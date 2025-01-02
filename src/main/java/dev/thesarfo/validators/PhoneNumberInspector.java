package dev.thesarfo.validators;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;

public class PhoneNumberInspector implements Inspector<String> {

    @Override
    public boolean isValid(String value) {
        if (value == null || value.isEmpty()) return false;
        PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();
        try {
            phoneNumberUtil.parse(value, "");
            return true;
        } catch (NumberParseException e) {
            return false;
        }
    }
}
