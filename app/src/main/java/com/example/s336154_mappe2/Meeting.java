package com.example.s336154_mappe2;

public class Meeting {
    private long id;
    private String time;
    private String date;
    private String place;
    private String comment;
    private long contactId; // ID of the associated contact

    // Constructors
    public Meeting() {
    }


   public Meeting(String time, String date, String place, String comment, long contactId) {
        this.time = time;
        this.date = date;
        this.place = place;
        this.comment=comment;
        this.contactId = contactId;
    }



/*    public Meeting(String time, String date, String place, long contactId) {
        this.time = time;
        this.date = date;
        this.place = place;
        this.contactId = contactId;
    }

 */

    // Getters and Setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public long getContactId() {
        return contactId;
    }

    public void setContactId(long contactId) {
        this.contactId = contactId;
    }
}
