package com.ale.ovng.infra.exercise.model.Class;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Data
@Schema(name="MacMapping", description="POJO that represents the mac mapping details.")
public class MacMapping
{

    public String macAddrr;
    public String id;
    public String siteId;


    public MacMapping() {

    }


    public String getSiteId() {
        return siteId;
    }



    @JsonCreator
    public MacMapping(@JsonProperty("siteId") String siteId, @JsonProperty("macAddrr") String macAddrr, @JsonProperty("Id") String Id) {
        this.siteId=siteId;
        this.id=Id;
        this.macAddrr=macAddrr;



    }
}