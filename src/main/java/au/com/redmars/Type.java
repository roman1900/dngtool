package au.com.redmars;

import java.util.HashMap;
import java.util.Map;

public enum Type {
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
    
    private static final Map<Integer, Type> BY_VALUE = new HashMap<>();
    

    public final Integer value;

    static {
        for (Type e: values()) {
            BY_VALUE.put(e.value, e);
        }
    }
    private Type(Integer value) {
        this.value = value;
    }
    public static Type valueOfTag(int value) {
        return BY_VALUE.get(value);
    }
}
