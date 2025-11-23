package api.afrilangue.helper;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.text.Normalizer;
import java.util.UUID;

/**
 * @author Ibrahima Diallo <ibrahima.diallo2@supinfo.com>
 */
public class Utils {
    private Utils() {
        throw new UnsupportedOperationException();
    }

    public static String currentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }
        return ( (UserDetails) authentication.getPrincipal()).getUsername();
    }

    public static String uuid() {
        return UUID.randomUUID().toString();
    }
    public static String convertName(String name) {
        String[] arrayName = name.split(" ");
        StringBuffer sb = new StringBuffer();

        for (int i = 0; i < arrayName.length; i++) {
            sb.append(Character.toUpperCase(arrayName[i].charAt(0)))
                    .append(arrayName[i].substring(1)).append(" ");
        }
        return sb.toString().trim();
    }
    public static boolean isNumeric(String strNum) {
        return strNum.matches("-?\\d+(\\.\\d+)?");
    }
    public static String normalizeText(String text) {
        String normalized = Normalizer.normalize(text, Normalizer.Form.NFD);
        normalized = normalized.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");

        normalized = normalized.replaceAll("[\\p{Punct}\\s]+", "").toLowerCase();

        return normalized;
    }

    public static String removeParenthesesAndContent(String input) {
        if (input == null) {
            return "";
        }
        return input.replaceAll("\\(.*?\\)", "").trim();
    }

}
