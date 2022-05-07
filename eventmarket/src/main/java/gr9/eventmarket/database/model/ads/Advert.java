package gr9.eventmarket.database.model.ads;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

import gr9.eventmarket.database.model.User;

public class Advert {

    @Id
    private String adID;

    private String headline;

    private Location location;

    @DBRef
    private User seller;

    private LocalDateTime dateAndTime; // 2022-02-16 13:00:00

    private TypeOfEvent typeOfEvent;

    /**
     * todo: investigate whether specifying the currency is necessary, or if we
     * assume
     * it's NOK by default.
     */
    private double price;
    private boolean isBought = false;
    private boolean flagged;

    public Advert() {}

    public Advert(User seller, String headline, Location location, String dateAndTime, TypeOfEvent typeOfEvent, double price) {
        this(seller, headline, location,
                (LocalDateTime.ofEpochSecond(Long.parseLong(dateAndTime), 0, ZoneOffset.UTC)),
                typeOfEvent, price, false);
    }

    /**
     * Secondary constructor primarily to please MongoDB
     */
    public Advert(User seller, String headline, Location location, LocalDateTime dateAndTime, TypeOfEvent typeOfEvent, double price,
            boolean isBought) {
        if (!numberNonNegative(price)) {
            throw new IllegalArgumentException("Number cannot be negative");
        }
        infoNotNull(seller, headline, location, dateAndTime, typeOfEvent);
        this.dateAndTime = dateAndTime;
        this.headline = headline;
        this.seller = seller;
        this.location = location;
        this.typeOfEvent = typeOfEvent;
        this.price = price;
    }

    public User getSeller() {
        return this.seller;
    }

    public Location getLocation() {
        return this.location;
    }

    public TypeOfEvent getTypeOfEvent() {
        return typeOfEvent;
    }

    public String getHeadline() {
        return headline;
    }

    /**
     * Returns the timestamp of the underlying date-time object
     */
    public String getDateAndTime() {
        return String.valueOf(this.dateAndTime.atZone(ZoneOffset.UTC).toEpochSecond() * 1000);
    }

    // Note: DO NOT rename this to getUTC..., as this means the resulting JSON is utcdateTimeString
    public LocalDateTime getUtcDateTimeString() {
        return this.dateAndTime;
    }

    public double getPrice() {
        return this.price;
    }

    public boolean isBought() {
        return isBought;
    }

    public boolean isFlagged() {
        return this.flagged;
    }

    public void setAdvertAsFlagged() {
        this.flagged = true;
    }

    public void setPrice(double price) {
        if (!numberNonNegative(price)) {
            throw new IllegalArgumentException("Price cannot be negative");
        }
        this.price = price;
    }

    private boolean numberNonNegative(double number) {
        return number >= 0;
    }

    public void advertBought() {
        this.isBought = true;
    }

    private void infoNotNull(Object... objects) {
        for (Object o : objects) {
            if (o == null) {
                throw new IllegalArgumentException("Argument cannot be Null");
            }
            if (o instanceof String && ((String)o).isBlank()) {
                throw new IllegalArgumentException("Argument cannot be blank");
            }
        }
    }
}
