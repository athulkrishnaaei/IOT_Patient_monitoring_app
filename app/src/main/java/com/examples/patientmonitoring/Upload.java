package com.examples.patientmonitoring;

import android.content.Intent;

public class Upload {
    private String name;
    private String gender;
    private String age;
    private String cami;



    public Upload(){

    }

    public String getName() {

        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getGender()
    {
        return gender;
    }

    public void setGender(String gender)
    {
        this.gender = gender;
    }

    public String getAge()
    {
        return age;
    }

    public void setAge(String age)
    {
        this.age = age;
    }
    public String getCami() {
        return cami;
    }

    public void setCami(String cami) {
        this.cami = cami;
    }


}
