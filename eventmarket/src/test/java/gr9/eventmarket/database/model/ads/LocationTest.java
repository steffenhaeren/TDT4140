package gr9.eventmarket.database.model.ads;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LocationTest {

    @Test
    public void testConsturctorForValidInput() {
        //Tests the constructor for valid input
        Location location = new Location("Oslo", "Spektrum");
        assertEquals("Oslo", location.getCity());
        assertEquals("Spektrum", location.getVenue());
    }

    @Test
    public void testConstructorForNullArguments() {
        // Should throw an IllegalArgumentException as neither arguments can be null
        assertThrows(IllegalArgumentException.class, () -> new Location(null, null));
        assertThrows(IllegalArgumentException.class, () -> new Location("Oslo", null));
        assertThrows(IllegalArgumentException.class, () -> new Location(null, "Spektrum"));
    }
}
