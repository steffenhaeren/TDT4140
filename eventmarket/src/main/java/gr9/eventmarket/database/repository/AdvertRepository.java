package gr9.eventmarket.database.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import gr9.eventmarket.database.model.User;
import gr9.eventmarket.database.model.ads.Advert;

public interface AdvertRepository 
    extends MongoRepository<Advert, String>{

    List<Advert> getPostsBySeller(User u);
}
