package com.ale.ovng.infra.exercise.model.Class;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Data;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.json.simple.JSONArray;

import java.io.Serializable;

@Data
@Schema(name="External Devices", description="POJO that represents the list of external devices.")
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
