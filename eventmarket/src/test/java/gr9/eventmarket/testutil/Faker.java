package gr9.eventmarket.testutil;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import gr9.eventmarket.database.model.User;
import gr9.eventmarket.database.model.ads.*;

/**
 * Helper class for generating fake data for unit tests.
 *
 * Should ONLY be used where the actual data is irrelevant, and the management of the aforementioned data is in question.
 */
public class Faker {
    private static final Random inst = new Random();

    private static List<String> generateRandomStringsRaw(int count, int length, Supplier<?> generator) {

        Set<String> strings = new HashSet<>();
        do {
            StringBuilder s = new StringBuilder();
            for (int i = 0; i < length; ++i) {
                s.append(generator.get());
            }
            strings.add(s.toString());
        } while (strings.size() < count);
        return strings.stream().collect(Collectors.toList());
    }

    private static List<String> generateRandomEmails(int count) {
        return generateRandomStrings(count, 15).stream()
            .map((s) -> s + "@example.com")
            .collect(Collectors.toList());
    }

    private static List<String> generateRandomStrings(int count, int length) {
        return generateRandomStringsRaw(count, length, () -> 'A' + inst.nextInt(27));
    }

    private static List<String> generateRandomPhoneNumbers(int count, int length) {
        return generateRandomStringsRaw(count, length, () -> inst.nextInt(10));
    }

    // NOTE: does not sort out users 
    public static List<User> generateUsers(int count) {
        var emails = generateRandomEmails(count);
        var usernames = generateRandomStrings(count, 14);
        var phoneNumbers = generateRandomPhoneNumbers(count, 8);

        List<User> list = new ArrayList<>();
        for (int i = 0; i < count; ++i) {
            list.add(

                new User(usernames.get(i),
                    emails.get(i),
                    phoneNumbers.get(i),
                    "Abcd!1234"
                )
            );
            
        }

        return list;
    }

    public static List<Advert> generateAds(List<User> users, int postsPerUser) {
        List<Advert> ads = new ArrayList<>();
        for (var user : users) {
            for (int i = 0; i < postsPerUser; ++i) {
                Advert ad = new Advert(user, "Party",
                    new Location("Oslo", "Your mom's house"),
                    String.valueOf(System.currentTimeMillis() / 1000),
                    TypeOfEvent.CONCERT,
                    69
                );
                ads.add(ad);
            }
        }
        return ads;
    }

}
