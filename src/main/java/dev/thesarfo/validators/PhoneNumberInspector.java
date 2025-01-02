package dev.thesarfo.validators;

import dev.thesarfo.annotations.PhoneNumber;

import java.lang.reflect.Field;


import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.NumberParseException;

public class PhoneNumberValidator implements Validator<String> {

    @Override
    public boolean isValid(String value) {
        if (value == null || value.isEmpty()) return false;
        PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();
        try {
            phoneNumberUtil.parse(value, ""); // "" for generic region
            return true;
        } catch (NumberParseException e) {
            return false;
        }
    }
}
