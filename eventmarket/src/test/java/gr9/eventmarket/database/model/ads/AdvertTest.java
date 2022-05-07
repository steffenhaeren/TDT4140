package gr9.eventmarket.database.model.ads;

import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalTime;
import org.junit.jupiter.api.Test;

import gr9.eventmarket.database.model.User;


public class AdvertTest {

    private final String timeStamp = "1645106329";

    private User profile = new User("JohnDoe", "johndoe@gmail.com", "90001000", "Password123");
    private Location location = new Location("Oslo", "Spektrum");
    private String headline = "Spooderman ticket";
    private LocalTime time = LocalTime.of(1, 1);
    private TypeOfEvent typeOfEvent = TypeOfEvent.CONCERT;
    private double price = 100.0;

    @Test
    void testConsturctorForValidInput() {
        //Tests the constructor for valid input
        Advert advert = new Advert(profile, headline, location, timeStamp, typeOfEvent, price);
        assertEquals(profile, advert.getSeller());
        assertEquals(location, advert.getLocation());
        assertEquals(timeStamp + "000", advert.getDateAndTime());
        assertEquals(typeOfEvent, advert.getTypeOfEvent());
        assertEquals(price, advert.getPrice());
        assertFalse(advert.isBought());
    }

    @Test
    void testAdvertBought() {
        //Tests that an advert can be bought.
        Advert advert = new Advert(profile, headline, location, timeStamp, typeOfEvent, price);
        advert.advertBought();
        assertTrue(advert.isBought());
    }

    @Test 
    void testConstructorForNullArguments() {
        // Should throw an IllegalArgumentException as neither arguments can be null
        assertThrows(IllegalArgumentException.class, () -> new Advert(null, headline, location, timeStamp, typeOfEvent, price));
        assertThrows(IllegalArgumentException.class, () -> new Advert(profile, null, location, timeStamp, typeOfEvent, price));
        assertThrows(IllegalArgumentException.class, () -> new Advert(profile, headline, null, timeStamp, typeOfEvent, price));
        assertThrows(IllegalArgumentException.class, () -> new Advert(profile, headline, location, null, typeOfEvent, price));
        assertThrows(IllegalArgumentException.class, () -> new Advert(profile, headline, location, timeStamp, null, price));
    }

    @Test
    void testConstructorForNegativePrice() {
        // Should throw an IllegalArgumentException as the price is negative.
        assertThrows(IllegalArgumentException.class, () -> new Advert(null, headline, location, timeStamp, typeOfEvent, -1.0));

        // Should not throw as price is non-negative
        assertDoesNotThrow(() -> new Advert(profile, headline, location, timeStamp, typeOfEvent, 0.0));
        assertDoesNotThrow(() -> new Advert(profile, headline, location, timeStamp, typeOfEvent, 1.0));
    }


}
