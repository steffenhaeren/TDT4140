package gr9.eventmarket.database.model.ads;

public class Location {

    private String city;
    private String venue;

    public Location() {}
    public Location(String city, String venue) {
        if (!infoNotNull(city, venue)) {
            throw new IllegalArgumentException("City or venue cannot be null");
        }
        this.city = city;
        this.venue = venue;
    }

    public String getCity() {
        return this.city;
    }

    public String getVenue() {
        return this.venue;
    }

    private boolean infoNotNull (String city, String venue) {
        return (city != null) && (venue != null);
    }

}
