package com.prusakova.ragweed.model;

import com.google.gson.annotations.SerializedName;

public class Medicine {
    @SerializedName("id_med")
    private int id_med;
    @SerializedName("med_name")
    private String med_name;
    @SerializedName("cost")
    private String cost;
    @SerializedName("active_substance")
    private String active_substance;
    @SerializedName("photo_med")
    private String photo_med;
    @SerializedName("instruction")
    private String instruction;


    public int getId_med(){
        return id_med;
    }
    public void setId_med(int id_med){
        this.id_med = id_med;
    }

    public String getMed_name(){return med_name;}
    public void setMed_name(String med_name){ this.med_name = med_name;}

    public String getCost(){ return cost;}
    public void setCost(String cost){this.cost = cost;}

    public String getActive_substance(){return active_substance;}
    public void setActive_substance(String active_substance){ this.active_substance = active_substance;}

    public String getPhoto_med(){ return photo_med;}
    public void setPhoto_med(String photo_med){ this.photo_med = photo_med;}

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }
}
