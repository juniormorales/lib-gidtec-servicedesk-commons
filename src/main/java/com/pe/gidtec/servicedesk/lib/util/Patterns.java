package com.pe.gidtec.servicedesk.lib.util;

public class Patterns {
    public static final String PASSWORD = "^(?=.*\\d)(?=.*[\\u0021-\\u002b\\u003c-\\u0040])(?=.*[A-Z])(?=.*[a-z])\\S{8,16}$";
    public static final String ALPHANUMERIC = "^\\w+$";
    public static final String ALPHANUMERIC_ONLY_MINUS = "^[a-z\\d]+$";
    public static final String NUMERIC = "^([0-9]*)$";
    public static final String LOCAL_DATE = "((18|19|20)[0-9]{2}[\\-.](0[13578]|1[02])[\\-.](0[1-9]|[12][0-9]|3[01]))|(18|19|20)[0-9]{2}[\\-.](0[469]|11)[\\-.](0[1-9]|[12][0-9]|30)|(18|19|20)[0-9]{2}[\\-.](02)[\\-.](0[1-9]|1[0-9]|2[0-8])|(((18|19|20)(04|08|[2468][048]|[13579][26]))|2000)[\\-.](02)[\\-.]29";
}
