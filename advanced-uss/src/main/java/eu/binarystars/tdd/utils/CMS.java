package eu.binarystars.tdd.utils;

import java.util.HashMap;
import java.util.Map;

class CMS {
    static Map<String, Map<String, String>> texts = new HashMap<>();
    static {
        var localizedTexts = new HashMap<String, String>();
        localizedTexts.put("one", "Eins");
        localizedTexts.put("two", "Zwei");
        texts.put("de", localizedTexts);

        localizedTexts.put("one", "One");
        localizedTexts.put("two", "Two");
        texts.put("en", localizedTexts);
    }

    static String getText(String locale, String key) {
        return texts.getOrDefault(locale, texts.get("en")).get(key);
    }

    static String DE(String key) {
        return getText("de", key);
    }

    static String EN(String key) {
        return getText("de", key);
    }

    CMS(String locale) {
        this.locale = locale;
    }

    private final String locale;

    String getText(String key) {
        return getText(locale, key);
    }

}
