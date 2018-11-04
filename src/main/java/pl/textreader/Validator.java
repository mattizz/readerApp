package pl.textreader;

import org.w3c.dom.Node;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {

    private static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    public int findTypeOfField(String name) {

        String ifPhone = name.replaceAll("\\s+", "");
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(name);

        if (matcher.find()) { //if email
            return 1;
        } else if (ifPhone.length() == 9 && ifPhone.matches("^[0-9]*$")) { //if phone
            return 2;
        } else if (name.matches("^[a-zA-Z]+$")) { //if jabber
            return 3;
        } else { //different
            return 0;
        }
    }

    public static int setTypeOfContact(String type) {
        switch (type) {
            case "email":
                return 1;
            case "phone":
                return 2;
            case "jabber":
                return 3;
            default:
                return 0;
        }
    }

    public static String checkIfAgeExist(Node ageTag) {
        if (ageTag == null) {
            return "";
        } else {
            return ageTag.getTextContent();
        }
    }
}
