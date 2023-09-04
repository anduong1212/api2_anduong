package utilities;

import com.github.javafaker.Faker;

public class FakerUtils {
    public static int generateRandomIssueID(String latestIssueID){
        Faker faker = new Faker();
        return faker.number().numberBetween(Integer.parseInt(latestIssueID), (int) Math.ceil(9.9999999999999 * Math.pow(10, latestIssueID.length())));
    }
}
