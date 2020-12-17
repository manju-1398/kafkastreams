package com.ale.ovng.infra.exercise.model.Class;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Data;
import org.json.simple.JSONArray;

import java.io.Serializable;

@Data
//@ApplicationScoped
@JsonRootName("devices")
public class Device implements Serializable {


    JSONArray Devices;

    public org.json.simple.JSONArray getDevices() {
        return Devices;
    }
    @JsonCreator
    public void setDevices(JSONArray devices) {
        Devices = devices;
    }

}
