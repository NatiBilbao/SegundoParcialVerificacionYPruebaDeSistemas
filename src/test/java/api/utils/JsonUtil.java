package api.utils;

import org.json.JSONObject;

import java.util.Iterator;

public class JsonUtil {
    public static boolean areEqualJson(String expected, String actual) {
        boolean areEqual = true;
        JSONObject actualJSON = new JSONObject(actual);
        JSONObject expectedJSON = new JSONObject(expected);

        Iterator<?> keys = expectedJSON.keys();
        while(keys.hasNext()) {
            String key = (String) keys.next();
            if (actualJSON.has(key)) {
                String expectedValue = String.valueOf(expectedJSON.get(key));
                String actualValue = String.valueOf(actualJSON.get(key));
                if (expectedValue.contains("IGNORE") || actualValue.contains("IGNORE")) {
                    System.out.println("INFO> Ignorando la comparación del key [" + key + "]");
                } else if (!expectedValue.equals(actualValue)) {
                    areEqual = false;
                    System.out.println("ERROR> El key [" + key + "] tiene como expected: [" + expectedValue + "] vs actual: [" + actualValue + "]");
                }
            } else {
                areEqual = false;
                System.out.println("ERROR> El actualResult no tiene: ["+key+"]");
            }
        }

        return areEqual;
    }
}
