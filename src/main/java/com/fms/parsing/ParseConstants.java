package com.fms.parsing;

public class ParseConstants {

    public static final String THING_REGEX = "\\((\\d+),(\\d+(\\.\\d+)?),€(\\d+)\\)";

    public static final int MAX_THINGS_IN_A_LINE = 15;

    public static final String SPACE = " ";

    public static final String NO_CHARACTER = "";

    public static final String COLON = ":";

    public static final double MAX_WEIGHT = 100.00d;

    public static final double MAX_COST = 100.00d;

    private ParseConstants(){}
}
