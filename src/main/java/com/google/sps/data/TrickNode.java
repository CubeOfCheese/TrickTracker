package com.google.sps.data;

// a class that holds data pertaining to a trick
public class TrickNode {
  public String skate_style;
  public String trick_name;
  public long date;
  public String link;
  public String notes;
  public String trick_media;

  public TrickNode(String trick_name, long date, String link, String notes, String skate_style, String trick_media) {
    this.trick_name = trick_name;
    this.date = date;
    this.link = link;
    this.notes = notes;
    this.skate_style = skate_style;
    this.trick_media = trick_media;
  }
}