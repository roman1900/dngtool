package au.com.redmars.ifd;

import java.util.HashMap;
import java.util.Map;

public enum CameraOffset {
    Canon(90000),
    SAMSUNG(110000),
    Apple(120000),
    PENTAX(150000),
    Olympus(160000),
    SONY(170000);
    private static final Map<Integer, CameraOffset> BY_VALUE = new HashMap<>();
    

    public final Integer value;

    static {
        for (CameraOffset e: values()) {
            BY_VALUE.put(e.value, e);
        }
    }
    private CameraOffset(Integer value) {
        this.value = value;
    }
    public static CameraOffset valueOfTag(int value) {
        return BY_VALUE.get(value);
    }
}
