package az.fintech.unitech.util;

import com.fasterxml.jackson.databind.ObjectMapper;

public class StringUtils  {

    private static final ObjectMapper objectMapper = new ObjectMapper();
    private String value;

    public StringUtils() {
    }

    public static String beautify(Object obj) {
        if (obj == null) return "";
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (Exception ex) {
            return obj.toString();
        }
    }

    public String getValue() {
        return this.value;
    }

}
