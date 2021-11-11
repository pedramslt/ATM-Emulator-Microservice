package com.javainuse.enumeration;


public enum LoginType {

    NONE(0),
    PIN(1),
    FINGER_PRINT(2);

    private final Integer value;

    /**
     * @param value
     */
    LoginType(Integer value) {
        this.value = value;
    }

    /**
     * @param value
     * @return
     */
    public static LoginType fromValue(Integer value) {

        if (value == null) {
            return LoginType.NONE;
        }

        if (value == 1) {
            return LoginType.PIN;
        } else if (value == 2) {
            return LoginType.FINGER_PRINT;
        }
        return LoginType.NONE;
    }

    /**
     * @return
     */
    public Integer toValue() {
        return value;
    }

    public static LoginType fromType(String type) {
        if (type.equals(PIN.name())) {
            return PIN;
        } else if (type.equals(FINGER_PRINT.name())) {
            return FINGER_PRINT;
        } else {
            return NONE;
        }
    }

}
