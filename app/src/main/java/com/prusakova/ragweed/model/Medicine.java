package com.prusakova.ragweed.model;

public class Medicine {
    private int id_med;
    private String med_name;
    private double cost;
    private String active_substance;
    private byte[] photo_med;

    public Medicine(int id_med, String med_name, double cost, String  active_substance, byte[] photo_med){
        this.id_med = id_med;
        this.med_name = med_name;
        this.cost = cost;
        this.active_substance = active_substance;
        this.photo_med = photo_med;
    }

    public int getId_med(){
        return id_med;
    }
    public void setId_med(int id_med){
        this.id_med = id_med;
    }

    public String getMed_name(){return med_name;}
    public void setMed_name(String med_name){ this.med_name = med_name;}

    public double getCost(){ return cost;}
    public void setCost(double cost){this.cost = cost;}

    public String getActive_substance(){return active_substance;}
    public void setActive_substance(String active_substance){ this.active_substance = active_substance;}

    public byte[] getPhoto_med(){ return photo_med;}
    public void setPhoto_med(byte[] photo_med){ this.photo_med = photo_med;}
}
