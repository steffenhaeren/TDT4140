package gr9.eventmarket.database.payload.response;

import java.util.ArrayList;
import java.util.List;

import gr9.eventmarket.database.model.ads.Advert;

public class PostListResponse {

    private List<Advert> ads;

    public PostListResponse() {}

    public PostListResponse(List<Advert> ads) {
        this.ads = ads;
    }

    public List<Advert> getAds() {
        return ads;
    }
}
