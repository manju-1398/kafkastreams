package com.ale.ovng.infra.exercise.model.Class;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import java.util.LinkedHashMap;

//@ApplicationScoped
@JsonRootName("QoE")
public class QoE {

        public String macAddrr;
        public String timetoconnectDuration;
        public String timetoconnectStart;
        public String siteId;

        public QoE(){}

        public QoE(LinkedHashMap js)
        {

            this.timetoconnectDuration = js.get("timetoconnectDuration").toString();
            this.timetoconnectStart = js.get("timetoconnectStart").toString();
            this.macAddrr = js.get("macAddrr").toString();
        }

    @JsonCreator

    public QoE(@JsonProperty("macAddrr") String macAddrr, @JsonProperty("timetoconnectDuration") String timetoconnectDuration, @JsonProperty("timetoconnectStart") String timetoconnectStart, @JsonProperty("SiteId") String siteId) {
        this.macAddrr = macAddrr;
        this.timetoconnectDuration = timetoconnectDuration;
        this.timetoconnectStart = timetoconnectStart;
        this.siteId = siteId;
    }

    public String getSiteId() {
        return siteId;
    }

    public String getMacAddrr() {
        return macAddrr;
    }

    public void setMacAddrr(String macAddrr) {
        this.macAddrr = macAddrr;
    }

    public void setTimetoconnectDuration(String timetoconnectDuration) {
        this.timetoconnectDuration = timetoconnectDuration;
    }

    public void setTimetoconnectStart(String timetoconnectStart) {
        this.timetoconnectStart = timetoconnectStart;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }
    public String getTimetoconnectStart() {
        return timetoconnectStart;
    }

    public String getTimetoconnectDuration() {
        return timetoconnectDuration;
    }




}

