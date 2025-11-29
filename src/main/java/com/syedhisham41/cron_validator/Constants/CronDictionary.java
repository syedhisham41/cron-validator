package com.syedhisham41.cron_validator.Constants;

import java.util.Map;

public class CronDictionary {

    public static final Map<String, String> MONTHS = Map.ofEntries(Map.entry("1", "January"),
            Map.entry("2", "February"), Map.entry("3", "March"), Map.entry("4", "April"), Map.entry("5", "May"),
            Map.entry("6", "June"), Map.entry("7", "July"), Map.entry("8", "August"), Map.entry("9", "September"),
            Map.entry("10", "October"), Map.entry("11", "November"), Map.entry("12", "December"),
            Map.entry("JAN", "January"), Map.entry("FEB", "February"), Map.entry("MAR", "March"),
            Map.entry("APR", "April"), Map.entry("MAY", "May"), Map.entry("JUN", "June"), Map.entry("JUL", "July"),
            Map.entry("AUG", "August"), Map.entry("SEP", "September"), Map.entry("OCT", "October"),
            Map.entry("NOV", "November"), Map.entry("DEC", "December"));

    public static final Map<String, String> DAYS_OF_THE_WEEK = Map.ofEntries(Map.entry("2", "Monday"),
            Map.entry("3", "Tuesday"), Map.entry("4", "Wednesday"), Map.entry("5", "Thursday"),
            Map.entry("6", "Friday"), Map.entry("7", "Saturday"), Map.entry("1", "Sunday"), Map.entry("MON", "Monday"),
            Map.entry("TUE", "Tuesday"), Map.entry("WED", "Wednesday"), Map.entry("THU", "Thursday"),
            Map.entry("FRI", "Friday"), Map.entry("SAT", "Saturday"), Map.entry("SUN", "Sunday"));

    public static final Map<String, String> SPECIAL_SYMBOLS = Map.ofEntries(Map.entry("*", "every"),
            Map.entry("?", "no specific value"), Map.entry("L", "last day"), Map.entry("W", "nearest weekday"),
            Map.entry("#", "nth day of week"));

}
