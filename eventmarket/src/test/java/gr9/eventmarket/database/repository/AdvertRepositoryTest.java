package gr9.eventmarket.database.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.TestPropertySource;

import gr9.eventmarket.database.model.User;
import gr9.eventmarket.database.model.ads.Advert;
import gr9.eventmarket.testutil.Faker;

@DataMongoTest
@TestPropertySource(properties = {"spring.mongodb.embedded.version=3.4.0", "spring.data.mongodb.port=42069"})
public class AdvertRepositoryTest {
    @Autowired
    private AdvertRepository adRepo;
    @Autowired
    private UserRepository userRepo;

    @Autowired
    private RoleRepository roles;

    /**
     * Makes sure to leave the database clean before and after tests.
     */
    @AfterEach
    @BeforeEach
    public void clearRepo() {
        adRepo.deleteAll();
        userRepo.deleteAll();
    }

    @Test
    public void testPagination() {
        List<User> users = Faker.generateUsers(3);
        List<Advert> ads = Faker.generateAds(users, 1);

        userRepo.insert(users);
        adRepo.insert(ads);

        {
            for (var user : users) {
                assertEquals(1, adRepo.getPostsBySeller(user).size());
            }
        }
        Pageable p1r = PageRequest.of(0, 2),
                 p2r = PageRequest.of(1, 2);

        var p1 = adRepo.findAll(p1r);
        var p2 = adRepo.findAll(p2r);

        assertEquals(2, p1.getTotalPages()); // make sure there's two pages
        assertEquals(3, p1.getTotalElements()); // and that there's 3 elements; this indirectly verfies that the insertion worked
        assertEquals(p1.getTotalPages(), p2.getTotalPages());
        assertEquals(p1.getTotalElements(), p2.getTotalElements());

        assertEquals(2, p1.getContent().size());
        assertEquals(1, p2.getContent().size());

        // Make sure p2's item isn't in p1
        assertFalse(p1.getContent().contains(p2.getContent().get(0)));
    }

}
