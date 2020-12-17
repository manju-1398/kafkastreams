package com.ale.ovng.infra.exercise.service;

import com.ale.ovng.infra.exercise.connector.Consume;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class InsertDevice extends Consume.Consumer {

    public void insertDevice (){
        System.out.println("DB has been updated");
    }

}
