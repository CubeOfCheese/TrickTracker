package com.google.sps.data;

// a class that holds data pertaining to a trick
public class TrickNode {
  public String trick_name;
  public long date;
  public String link;
  public String notes;

  public TrickNode(String trick_name, long date, String link, String notes) {
    this.trick_name = trick_name;
    this.date = date;
    this.link = link;
    this.notes = notes;
  }
}