package api.afrilangue.helper;

import org.apache.commons.lang3.RandomStringUtils;



public interface StringGenerator {

    static String generateAlphabetic(int length){
        return RandomStringUtils.randomAlphabetic(length).toUpperCase();
    }

    static String generateNumeric(int length){
        return RandomStringUtils.randomNumeric(length);
    }

    static String generateAlphaNumeric(int length){
        return RandomStringUtils.randomAlphanumeric(length).toUpperCase();
    }

}
