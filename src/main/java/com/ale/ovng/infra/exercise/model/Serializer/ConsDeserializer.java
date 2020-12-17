package com.ale.ovng.infra.exercise.model.Serializer;


import com.ale.ovng.infra.exercise.model.Class.QoE;
import io.quarkus.kafka.client.serialization.JsonbDeserializer;

public class ConsDeserializer extends JsonbDeserializer<QoE> {
        public ConsDeserializer(){
            // pass the class to the parent.
            super(QoE.class);
        }
    }
