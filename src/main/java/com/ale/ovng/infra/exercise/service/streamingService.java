package com.ale.ovng.infra.exercise.service;


import com.ale.ovng.infra.exercise.model.Class.QoE;
import com.ale.ovng.infra.exercise.model.Serializer.JsonDeserializer;
import com.ale.ovng.infra.exercise.model.Serializer.JsonSerializer;
import com.ale.ovng.infra.exercise.model.Class.Device;
import com.ale.ovng.infra.exercise.model.Class.MacMapping;
import io.quarkus.kafka.client.serialization.JsonbSerde;
import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.errors.LogAndContinueExceptionHandler;
import org.apache.kafka.streams.kstream.*;
import org.json.simple.JSONArray;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Properties;



@ApplicationScoped
public class streamingService {

    private KafkaStreams streams;

    public void onStart(@Observes StartupEvent event) {
      //  streamingService w = new streamingService();

        Properties config = new Properties();
        config.put(StreamsConfig.APPLICATION_ID_CONFIG, "user-event-enricher-app");
        config.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        config.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        config.put("default.deserialization.exception.handler", LogAndContinueExceptionHandler.class);

        final Serde<String> STRING_SERDE = Serdes.String();

        JsonbSerde<QoE> QOE_INFO = new JsonbSerde<>(QoE.class);
        JsonSerializer<Device> deviceJsonSerializer = new JsonSerializer<>();
        JsonDeserializer<Device> deviceJsonDeserializer = new JsonDeserializer<>(
                Device.class);
        Serde<Device> DEVICE_INFO=Serdes.serdeFrom(deviceJsonSerializer,
                deviceJsonDeserializer);

        JsonSerializer<MacMapping> macJsonSerializer = new JsonSerializer<>();
        JsonDeserializer<MacMapping> macJsonDeserializer = new JsonDeserializer<>(
                MacMapping.class);
        Serde<MacMapping> USER_INFO=Serdes.serdeFrom(macJsonSerializer,
                macJsonDeserializer);

        StreamsBuilder builder = new StreamsBuilder();

       final KStream<String, Device> deviceKStream= builder.stream("ext_wireless_events", Consumed.with(STRING_SERDE, DEVICE_INFO));
        deviceKStream.mapValues(value -> {
           Device d = value;
           System.out.println(d.getDevices());
           return value;
       });
        KTable<String, MacMapping> userKTable = builder.table("public_macaddress_mapping", Consumed.with(STRING_SERDE, USER_INFO));


        KStream<String, QoE[]> qoe_devices = deviceKStream.join(userKTable,
                (ext_st,mac_st) ->{
            Device dev_obj = ext_st;
            MacMapping macMapping_obj = mac_st;

            System.out.println("mac_addr\t"+macMapping_obj.macAddrr);

            QoE[] qoe_obj= getQoes(macMapping_obj,dev_obj);

            return qoe_obj;

                });


        KStream<String,QoE>qoe_dev=qoe_devices.flatMapValues(( values)-> Arrays.asList(values))
                .selectKey(new KeyValueMapper<String, QoE, String>() {
                    @Override
                    public String apply(String key, QoE value) {
                        return value.getMacAddrr();
                    }
                });
        qoe_dev.to("internal_device_events",
                Produced.with(Serdes.String(),QOE_INFO));

        streams = new KafkaStreams(builder.build(), config);
        streams.cleanUp(); // only do this in dev - not in prod
        streams.start();

    }

    void onStop(@Observes ShutdownEvent event) {
        streams.close();
    }

    private static  QoE[] getQoes(MacMapping macMapping, Device dev) {
        JSONArray arr=dev.getDevices();
        int len=arr.size();

        QoE[] qoe_arr = new QoE[len];
        for(int i=0;i<len;i++)
        {
            LinkedHashMap jout = (LinkedHashMap) arr.get(i);
            QoE q= new QoE(jout);

            q.setSiteId(macMapping.getSiteId());
            qoe_arr[i]=q;
        }

        return qoe_arr;
    }




}









