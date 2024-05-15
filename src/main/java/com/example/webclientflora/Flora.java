package com.example.webclientflora;

public class Flora {
    public int id;
    public String name;
    public String sciName;

    public Flora(String name, String sciName) {
        this.name = name;
        this.sciName = sciName;
    }

    @Override
    public String toString() {
        return "Flora{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", scientificName='" + sciName + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSciName() {
        return sciName;
    }

    public void setSciName(String sciName) {
        this.sciName = sciName;
    }
}
