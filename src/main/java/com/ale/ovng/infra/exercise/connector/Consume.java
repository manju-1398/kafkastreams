package com.ale.ovng.infra.exercise.connector;

import com.ale.ovng.infra.exercise.service.streamingService;
import com.ale.ovng.infra.exercise.model.Class.QoE;
import io.smallrye.reactive.messaging.annotations.Blocking;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Message;
import org.eclipse.microprofile.reactive.messaging.Outgoing;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.core.Response;
import java.sql.*;
import java.util.concurrent.CompletionStage;


public class Consume {

public static class Consumer {

    @Incoming("internal_device_events")
    @Outgoing("data-stream")

    @Blocking
    @Transactional
    public streamingService process(streamingService l) {


        return (l);
    }

    @Inject
    io.vertx.mutiny.pgclient.PgPool client;


    @ConfigProperty(name = "myapp.schema.create", defaultValue = "true")
    boolean schemaCreate;

    @PostConstruct
    void config() {
        if (schemaCreate) {

        }
    }

    @Incoming("internal_device_events")
    public CompletionStage<Void> consumeMessage(Message<QoE> message) {

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        QoE msg = message.getPayload();

        long started = Long.parseLong(msg.getTimetoconnectStart());
        long duration = Long.parseLong(msg.getTimetoconnectDuration());

        client.query("insert into qoe_device_timetoconnect values('" + timestamp + "','" + msg.getMacAddrr() + "','" + msg.getSiteId() + "','" + started + "','" + duration + "')").execute()

                .await().indefinitely();

        client.begin();

        return message.ack();

    }

}
    public static class DbInsert{


        public static double[] findDev(String siteId, String speriod, String eperiod) {
            double[] arr = new double[2000];
            double[] result = new double[3];
            int count = 0;
            Connection con = null;
            Statement stmt = null;
            long sp = Long.parseLong(speriod);
            long ep = Long.parseLong(eperiod);
            try {
                Class.forName("org.postgresql.Driver");
                con = DriverManager
                        .getConnection("jdbc:postgresql://localhost:5432/ovng_training",
                                "postgres", "password");
                stmt = con.createStatement();

                String query = "SELECT * FROM qoe_device_timetoconnect;";
                ResultSet rs = stmt.executeQuery(query);
                while (rs.next()) {
//Retrieve by column name
                    String sid = rs.getString("siteId");
                    long ttcd = rs.getLong("duration");
                    long ttcs = rs.getLong("started");
                    if (sid.equals(siteId) && ttcs >= sp && ttcs <= ep) {
                        arr[count] = ttcd;
                        count++;
                    }
                }
                if (count == 0) {
                    return notFound();
                } else {
                    double sum = 0, avg;
                    for (int var = 0; var < count; var++) {
                        sum = arr[var] + sum;
                    }
                    avg = sum / count;
                    result[0] = count;
                    result[1] = avg;
                    result[2] = Response.Status.OK.getStatusCode();

                    return result;
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.err.println(e.getClass().getName() + ": " + e.getMessage());
                System.exit(0);
            }
            return result;

        }

        public static double[] notFound() {
            double[] status = new double[2];
            status[0] = Response.Status.NOT_FOUND.getStatusCode();
            return status;
        }


    }
}