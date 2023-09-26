package com.example.digital_health_team.Model;

public class Patient{


    private long id = 0;


    private String patientname ="";


    private int patientfhirid = 0;


    private String ocrcontent ="";

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPatientname() {
        return patientname;
    }

    public void setPatientname(String patientname) {
        this.patientname = patientname;
    }

    public int getPatientfhirid() {
        return patientfhirid;
    }

    public void setPatientfhirid(int patientfhirid) {
        this.patientfhirid = patientfhirid;
    }

    public String getOcrcontent() {
        return ocrcontent;
    }

    public void setOcrcontent(String ocrcontent) {
        this.ocrcontent = ocrcontent;
    }
}
