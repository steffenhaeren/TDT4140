package gr9.eventmarket.database.model;

import gr9.eventmarket.database.model.ads.Advert;
import gr9.eventmarket.database.model.ads.City;
import gr9.eventmarket.database.model.ads.Location;
import gr9.eventmarket.database.model.ads.TypeOfEvent;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

public class UserTest {

    @Test
    public void testConstructorForValidInput() {

        //Tests the constructor for valid input, asserts that a new profile is not set to admin,
        // then test to se that the profile is set to admin after the call of profile.setUserAdmin().
        User profile = new User("JohnDoe", "johndoe@gmail.com", "90001000", "Password123");
        assertEquals("JohnDoe", profile.getUsername());
        assertEquals("Password123", profile.getPassword());
        assertEquals("johndoe@gmail.com", profile.getEmail());
        assertEquals("90001000", profile.getPhoneNumber());
    }

    @Test
    public void testConstructor_password() {

        // Should throw an IllegalArgumentExceptions as password must contain at least 8 characters.
        assertThrows(IllegalArgumentException.class, () -> new User("JohnDoe", "johndoe@gmail.com", "90001000", "Abcdef1"));

        // Should throw an IllegalArgumentException as password must contain both lower and uppercase letters.
        assertThrows(IllegalArgumentException.class, () -> new User("JohnDoe", "johndoe@gmail.com", "90001000", "abcdefg1"));

        // Should throw an IllegalArgumentException as password must contain at least 1 digit.
        assertThrows(IllegalArgumentException.class, () -> new User("JohnDoe", "johndoe@gmail.com", "90001000", "Abcdefg"));

        // Should not throw an exception as the password matches the criteria (See profile.java class).
        assertDoesNotThrow(() ->new User("JohnDoe", "johndoe@gmail.com", "90001000", "Abcdefg1"));

    }

    @Test
    public void testConstructor_username() {
        // Should throw an IllegalArgumentException as the username is too short.
        assertThrows(IllegalArgumentException.class, () -> new User("Jo", "johndoe@gmail.com", "90001000", "Abcdefg1"));

        // Should throw an IllegalArgumentException as it contains special characters except dashes, points or underscores.
        assertThrows(IllegalArgumentException.class, () -> new User("JohnDoe!", "johndoe@gmail.com", "90001000", "Abcdefg1"));

    }

    @Test
    public void testConstructor_email() {
        // Should throw an IllegalArgumentException as the email does not contain a "@" in the middle.
        assertThrows(IllegalArgumentException.class, () -> new User("JohnDoe", "Abcdefg1", "john_doe.gmail.com", "90001000"));
    }

    @Test
    public void testContstructor_phoneNumber() {
        // Should throw an IllegalArgumentException as the phone number is too short.
        assertThrows(IllegalArgumentException.class,() -> new User("JohnDoe", "johndoe@gmail.com", "9000100", "Abcdefg1"));

        // Should throw an IllegalArgumentException as the phone number is too long.
        assertThrows(IllegalArgumentException.class,() -> new User("JohnDoe", "johndoe@gmail.com", "900010000", "Abcdefg1"));

        // Should throw an IllegalArgumentException as the phone number does not only contain digits.
        assertThrows(IllegalArgumentException.class,() -> new User("JohnDoe", "johndoe@gmail.com", "9000100a", "Abcdefg1"));
    }

    @Test
    public void testFlaggingFunctionality_Mod() {
        User mod = new User("JohnDoe", "johndoe@gmail.com", "90001000", "Password123");
        User user = new User("JohnDoe", "johndoe@gmail.com", "90001000", "Password123");
        Location location = new Location("Bergen", "Hulen");
        Advert advert = new Advert(user, "Billetter", location, "1645106329136", TypeOfEvent.CONCERT, 499);
        // Tests that the advert initially is set to not flagged
        assertFalse(advert.isFlagged());

        // Tries to flag the advert before the role as mod has been added, asserts that it did not get flagged
        mod.flagAdvert(advert);
        assertFalse(advert.isFlagged());
        mod.setRoles(new HashSet<>(Arrays.asList(new Role(ERole.ROLE_MOD))));
        mod.flagAdvert(advert);

        // Tests that the flagged field of advert is true after the mod role has been added and mod flagged the advert
        assertTrue(advert.isFlagged());
    }

    @Test
    public void testGivingModeratorRights() {
        User admin = new User("JohnDoe", "johndoe@gmail.com", "90001000", "Password123");
        User toBeMod = new User("JohnDoe", "johndoe@gmail.com", "90001000", "Password123");
        assertFalse(toBeMod.isMod());
        admin.setRoles(new HashSet<>(Arrays.asList(new Role(ERole.ROLE_MOD))));
        admin.giveModeratorRights(toBeMod);
        assertTrue(toBeMod.isMod());
    }
}
