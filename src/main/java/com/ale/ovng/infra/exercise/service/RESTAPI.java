package com.ale.ovng.infra.exercise.Service;

import com.ale.ovng.infra.exercise.connector.Consume;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped

public class RESTAPI extends Consume.DbInsert {
    public  String siteId;
    public  String speriod;
    public  String eperiod;
    public double[] RESTAPI(String siteId, String speriod,String  eperiod){
        this.eperiod=eperiod;
        this.speriod=speriod;
        this.siteId=siteId;
        Consume.DbInsert.notFound();
      return  Consume.DbInsert.findDev(siteId, speriod, eperiod);


    }




}
