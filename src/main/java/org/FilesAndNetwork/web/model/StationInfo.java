package org.FilesAndNetwork.web.model;

public class StationInfo {
    private String name;
    private String date;


    public StationInfo(String name, String date) {
        this.name = name;
        this.date = date;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "StationInfo{" +
                "name='" + name + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
