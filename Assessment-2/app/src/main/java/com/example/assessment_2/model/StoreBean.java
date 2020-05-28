package com.example.assessment_2.model;

public class StoreBean {
    private double latitude;
    private double longtitude;
    private String name;

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongtitude() {
        return longtitude;
    }

    public void setLongtitude(double longtitude) {
        this.longtitude = longtitude;
    }

}

class MotorBean {
    private String brand;
    private double horsePower;
    private double torque;
    private int weight;
    private int displacement;
    private int tankVolumn;

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public double getHorsePower() {
        return horsePower;
    }

    public void setHorsePower(double horsePower) {
        this.horsePower = horsePower;
    }

    public double getTorque() {
        return torque;
    }

    public void setTorque(double torque) {
        this.torque = torque;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getDisplacement() {
        return displacement;
    }

    public void setDisplacement(int displacement) {
        this.displacement = displacement;
    }

    public int getTankVolumn() {
        return tankVolumn;
    }

    public void setTankVolumn(int tankVolumn) {
        this.tankVolumn = tankVolumn;
    }
}
