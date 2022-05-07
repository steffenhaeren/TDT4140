package gr9.eventmarket.database.payload.request;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import gr9.eventmarket.database.model.ads.Location;
import gr9.eventmarket.database.model.ads.TypeOfEvent;

public class PostAdReq {

    private Location loc;
    private TypeOfEvent eventType;
    private String headline;
    private double price;

    private LocalDateTime dateAndTime;

    public PostAdReq() {}

    public PostAdReq(Location loc, String headline, TypeOfEvent eventType, double price, LocalDateTime dateAndTime) {
        this.loc = loc;
        this.headline = headline;
        this.eventType = eventType;
        this.price = price;
        this.dateAndTime = dateAndTime;
    }

    public Location getLoc() {
        return loc;
    }

    public String getHeadline() {
        return headline;
    }

    public TypeOfEvent getEventType() {
        return eventType;
    }

    public double getPrice() {
        return price;
    }

    public LocalDateTime getDateAndTime() {
        return dateAndTime;
    }

    public void setDateAndTime(String raw) {
        this.dateAndTime = LocalDateTime.ofEpochSecond(Long.parseLong(raw), 0, ZoneOffset.UTC);
    }

}
