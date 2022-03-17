package au.com.redmars;

import java.util.HashMap;
import java.util.Map;

public enum FieldType {
    BYTE(1),
    ASCII(2),
    SHORT(3),
    LONG(4),
    RATIONAL(5),
    SBYTE(6),
    UNDEFINED(7),
    SSHORT(8),
    SLONG(9),
    SRATIONAL(10),
    FLOAT(11),
    DOUBLE(12);
    
    private static final Map<Integer, FieldType> BY_VALUE = new HashMap<>();
    

    public final Integer value;

    static {
        for (FieldType e: values()) {
            BY_VALUE.put(e.value, e);
        }
    }
    private FieldType(Integer value) {
        this.value = value;
    }
    public static FieldType valueOfTag(int value) {
        return BY_VALUE.get(value);
    }
}
