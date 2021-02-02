package com.gamersbay.admin;

public class NotificationModel {
    private String id;

    public NotificationModel() {
        //public no-arg constructor needed
    }

    public NotificationModel(String id){
        this.id = id;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
