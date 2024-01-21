package com.notesapp.backend.utils.validation.password;

import com.notesapp.backend.utils.validation.password.interfaces.PasswordValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.regex.Pattern;

/**
 * Password validator implementation. <br>
 * Fields: <br>
 * min - minimum password length (default: 10) <br>
 * max - maximum password length (default: 50) <br>
 * minEntropy - minimum password entropy (default: 3.3) <br>
 * pattern - password pattern (default: ^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*()\-_+={\[}\]:;"'\\|<,>.?/]).*$
 * This pattern checks if there is at least 1 lowercase letter, 1 uppercase letter, 1 number and 1 special character in the password
 * <br>
 */
@Component
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
public class PasswordValidatorImpl implements PasswordValidator {

    private int min = 10;
    private int max = 50;
    private double minEntropy = 3.3;
    private Pattern pattern = Pattern.compile("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*()\\-_+={\\[}\\]:;\"'\\\\|<,>.?/]).*$");

    @Override
    public final boolean isValid(String value) {
        if(value == null) {
            log.warn("Null password");
            return false;
        }

        int length = value.length();

        if(length < this.min || length > this.max) {
            log.warn("Wrong password length");
            return false;
        }

        if(!this.pattern.matcher(value).matches()) {
            log.warn("Password does not match pattern");
            return false;
        }

        if(calculateEntropy(value) < this.minEntropy) {
            log.warn("Password entropy is too low");
            return false;
        }

        return true;
    }

    private double calculateEntropy(String text) {
        HashMap<Character, Integer> dictionary = new HashMap<>(text.length());

        for(char c : text.toCharArray()) {
            dictionary.put(c, dictionary.getOrDefault(c, 0) + 1);
        }

        double entropy = 0.0;

        for(Integer value: dictionary.values()) {
            double probability = (double) value / (double) text.length();
            entropy -= probability * Math.log(probability) / Math.log(2.0);
        }

        return entropy;

    }


}
