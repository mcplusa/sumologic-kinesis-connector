package com.mcplusa.sumologic;

import java.io.Serializable;

public class KinesisMessageModel implements Serializable {
  private String data;
  private int id;
  
  public KinesisMessageModel(String data) {
    this.data = data;
    this.id = 1;
  }
  
  public String getData() {
    return data;
  }

  public void setData(String data) {
    this.data = data;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String toString() {
    return data;
  }
}