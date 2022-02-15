package com.example.timetable.util;

import org.slf4j.LoggerFactory;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class Translator {
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(Translator.class);
    private static final Locale locale = new Locale("en", "EN");

    public static String localize(LocalizedMessage localizedMessage) {
        String message = localizedMessage.getMessageCode();
        try {
            ResourceBundle exampleBundle = ResourceBundle.getBundle(localizedMessage.getBundle(), locale);
            message = exampleBundle.getString(localizedMessage.getMessageCode());
        } catch (MissingResourceException e) {
            log.warn("Resource bundle or message is missing for " + localizedMessage.toString());
        }
        return message;
    }
}
