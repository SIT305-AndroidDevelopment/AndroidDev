package com.example.assessment_2.model;

import java.io.Serializable;

public class MotorItemDto implements Serializable, Comparable<MotorItemDto> {
  public String id;
  public String name;
  public int horsePower;
  public int torque;
  public int weight;
  public int displacement;
  public int tankVolume;
  public String picRes;
  public Integer price;
  public int brandId;
  public boolean collected;

  public Integer getPrice() {
    return price;
  }

  public void setPrice(Integer price) {
    this.price = price;
  }

  public int compareTo(MotorItemDto user) {
    return this.price - user.getPrice();
  }
}
