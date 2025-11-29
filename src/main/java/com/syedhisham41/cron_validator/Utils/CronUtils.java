package com.syedhisham41.cron_validator.Utils;

import java.util.Arrays;
import java.util.function.Function;

import com.syedhisham41.cron_validator.Constants.CronDictionary;
import com.syedhisham41.cron_validator.DTO.CronFields;
import com.syedhisham41.cron_validator.Exceptions.DangerousCronException;

public class CronUtils {

    public static String generateTextFromCronFields(CronFields cronFields, String cronExpr)
            throws IllegalArgumentException, IllegalAccessException {
        StringBuilder sb = new StringBuilder();

        String seconds = cronFields.getSeconds().trim();
        String minutes = cronFields.getMinutes().trim();
        String hours = cronFields.getHours().trim();
        String months = cronFields.getMonths().trim();
        String dom = cronFields.getDaysOfMonth().trim();
        String nwdays = cronFields.getNearestWeekdays().trim();
        String dow = cronFields.getDaysOfWeek().trim();
        String ldow = cronFields.getLastDayOfWeek().trim();

        String secondsCronExpr = cronExpr.split(" ")[0];
        String domCronExpr = cronExpr.split(" ")[3];
        String dowCronExpr = cronExpr.split(" ")[5];
        String monthsCronExpr = cronExpr.split(" ")[4];

        sb.append(humanizeSeconds(seconds, secondsCronExpr)).append(" ");
        sb.append(humanizeTime(hours, minutes)).append(" ");
        sb.append(humanizeDayOfMonth(dom, nwdays, domCronExpr)).append(" ");
        sb.append(humanizeDayOfWeek(dow, dowCronExpr, ldow)).append(" ");
        sb.append(humanizeMonths(months, monthsCronExpr));

        return sb.toString().trim();
    }

    private static String humanizeTime(String hours, String minutes) {

        // Format hour → 9 → "9 AM", 14 → "2 PM"
        Function<Integer, String> fmtHour = (h) -> {
            int hr12 = (h % 12 == 0) ? 12 : h % 12;
            String mer = (h < 12) ? "AM" : "PM";
            return hr12 + " " + mer;
        };

        // ------------------------------------------
        // 1. Handle "*"
        // ------------------------------------------
        if ("*".equals(hours) && "*".equals(minutes))
            return "every minute";

        if ("*".equals(hours)) {
            int step = detectStep(minutes);
            if (step > 0)
                return "every " + step + " minutes";

            return "at minutes " + minutes + " every hour";
        }

        // ------------------------------------------
        // 2. Detect hour range (e.g., 9–17)
        // ------------------------------------------
        int[] hourRange = detectRange(hours);

        // ------------------------------------------
        // 3. Detect minute step (e.g., 0,5,10,...)
        // ------------------------------------------
        int stepMin = detectStep(minutes);

        // ------------------------------------------
        // Case: stepped minutes + hour range
        // Result: → "every 5 minutes from 9 AM to 5 PM"
        // ------------------------------------------
        if (stepMin > 0 && hourRange != null) {
            return "every " + stepMin + " minutes from " +
                    fmtHour.apply(hourRange[0]) + " to " +
                    fmtHour.apply(hourRange[1]);
        }

        // ------------------------------------------
        // Case: hour range with fixed minute(s)
        // ------------------------------------------
        if (hourRange != null) {
            if (minutes.equals("0"))
                return "every hour from " +
                        fmtHour.apply(hourRange[0]) + " to " +
                        fmtHour.apply(hourRange[1]) +
                        " on the hour";

            return "at minute(s) " + minutes +
                    " from " + fmtHour.apply(hourRange[0]) +
                    " to " + fmtHour.apply(hourRange[1]);
        }

        // ------------------------------------------
        // Fallback: single hour or general case
        // ------------------------------------------
        String[] hourArr = hours.split(",");

        if (hourArr.length == 1) {
            int h24 = Integer.parseInt(hourArr[0]);
            String formatted = fmtHour.apply(h24);

            if (stepMin > 0)
                return "every " + stepMin + " minutes during " + formatted;

            if (minutes.equals("0"))
                return "every day at " + formatted.replace(" ", ":00 ");

            return "every day at " + formatted.replace(" ", ":" + minutes + " ");
        }

        return "at minutes " + minutes + " during hours " + hours;
    }

    // Helper: detect step interval in a field
    private static Integer detectStep(String field) {
        String[] arr = field.split(",");
        if (arr.length < 2)
            return -1;

        try {
            int first = Integer.parseInt(arr[0]);
            int second = Integer.parseInt(arr[1]);
            int diff = second - first;

            // Verify entire list follows the same step
            for (int i = 2; i < arr.length; i++) {
                int prev = Integer.parseInt(arr[i - 1]);
                int curr = Integer.parseInt(arr[i]);
                if ((curr - prev) != diff)
                    return -1;
            }
            return diff;
        } catch (Exception e) {
            return -1;
        }
    }

    // Helper: detect continuous range
    private static int[] detectRange(String field) {
        String[] arr = field.split(",");
        if (arr.length < 2)
            return null;

        int[] nums = Arrays.stream(arr).mapToInt(Integer::parseInt).toArray();
        Arrays.sort(nums);

        for (int i = 1; i < nums.length; i++) {
            if (nums[i] != nums[i - 1] + 1)
                return null; // not continuous
        }
        return new int[] { nums[0], nums[nums.length - 1] };
    }

    private static String humanizeSeconds(String seconds, String secondsCronExpr) {

        if (seconds.equals("*"))
            return "every second";

        if (seconds.matches("\\d+"))
            return "at " + seconds + " seconds past the minute";

        if (secondsCronExpr.contains("/")) {
            int step = detectStep(seconds);
            String parts[] = secondsCronExpr.split("/");
            String start = parts[0];

            if (step < 1)
                throw new DangerousCronException("Invalid seconds step");

            if (step < 5)
                throw new DangerousCronException("Cron triggers too frequently");

            if (start.equals("*") || start.equals("0"))
                return "every " + step + " seconds";
            else
                return "every " + step + " seconds" + " after " + start + " seconds";
        }

        if (secondsCronExpr.contains("-") && !secondsCronExpr.contains(",")) {
            int[] range = detectRange(seconds);
            return "between " + range[0] + "s and " + range[1] + "s past the minute";
        }

        if (seconds.contains(",")) {
            return "at seconds " + seconds.replace(",", ", ") + " past the minute";
        }

        return "at " + seconds + " seconds past the minute";
    }

    private static String humanizeMonths(String months, String monthsCronExpr) {

        if (months.equals("*"))
            return "every month";

        if (months.matches("\\d+"))
            return "in " + CronDictionary.MONTHS.get(months) + " month";

        if (monthsCronExpr.contains("/")) {
            int step = detectStep(months);
            String parts[] = monthsCronExpr.split("/");
            String start = parts[0];
            if (start.equals("1") || start.equals("*"))
                return "every " + step + " months";
            else
                return "every " + step + " months" + " starting from " + CronDictionary.MONTHS.get(start);
        }

        if (monthsCronExpr.contains("-") && !monthsCronExpr.contains(",")) {
            int[] range = detectRange(months);
            return "between " + CronDictionary.MONTHS.get(((Integer) range[0]).toString()) + " and "
                    + CronDictionary.MONTHS.get(((Integer) range[1]).toString()) + " months";
        }

        if (months.contains(",")) {
            // return "at months " + months.replace(",", ", ");
            months = Arrays.stream(months.split(",")).map((e) -> CronDictionary.MONTHS.get(e)).toList().toString();
            return "at months " + months;
        }

        return "at " + CronDictionary.MONTHS.get(months) + " month";
    }

    private static String humanizeDayOfMonth(String dom, String nwdays, String domCronExpr) {

        if (dom.equals("*"))
            return "every day";
        if (dom.equals("?"))
            return "";

        if (domCronExpr.equals("LW") && nwdays.equals("62"))
            return "on the last weekday of the month";

        if (domCronExpr.equals("L") && dom.equals("62"))
            return "on the last day of the month";

        if (domCronExpr.endsWith("W")) {
            String day = nwdays;
            return "on the nearest weekday to day " + day;
        }

        if (domCronExpr.contains("/")) {
            int step = detectStep(dom);
            String start = domCronExpr.split("/")[0];
            return "every " + step + " days starting on day " + start;
        }

        if (domCronExpr.contains("-") && !domCronExpr.contains(",")) {
            int[] r = detectRange(dom);
            return "between days " + r[0] + " and " + r[1];
        }

        if (dom.contains(",")) {
            String list = Arrays.stream(dom.split(","))
                    .map(s -> s + (s.equals("1") ? "st" : s.equals("2") ? "nd" : s.equals("3") ? "rd" : "th"))
                    .toList()
                    .toString();
            return "on days " + list;
        }

        return "on day " + dom;
    }

    private static String humanizeDayOfWeek(String dow, String dowCronExpr, String ldow) {

        if (dow.equals("*"))
            return "every day of the week";
        if (dow.equals("?"))
            return "";

        // LAST weekday of the month in week-form: "5L" = last Thursday
        if (dowCronExpr.endsWith("L") && "true".equals(ldow)) {
            String day = dow;
            return "on the last " + CronDictionary.DAYS_OF_THE_WEEK.get(day);
        }

        // Nth weekday of month: MON#2 → second Monday
        if (dowCronExpr.contains("#")) {
            String[] parts = dowCronExpr.split("#");
            String weekday = CronDictionary.DAYS_OF_THE_WEEK.get(parts[0]);
            String nth = switch (parts[1]) {
                case "1" -> "first";
                case "2" -> "second";
                case "3" -> "third";
                case "4" -> "fourth";
                case "5" -> "fifth";
                default -> parts[1] + "th";
            };

            return "on the " + nth + " " + weekday + " of the month";
        }

        // Step-based DOW (rare): "MON/2"
        if (dow.contains("/") && !dow.contains("#")) {
            int step = detectStep(dow);
            return "every " + step + " days of the week";
        }

        // Ranges: "MON-FRI"
        if (dowCronExpr.contains("-") && !dowCronExpr.contains(",")) {
            String[] r = dowCronExpr.split("-");
            String start = CronDictionary.DAYS_OF_THE_WEEK.get(r[0]);
            String end = CronDictionary.DAYS_OF_THE_WEEK.get(r[1]);
            return "from " + start + " to " + end;
        }

        // Lists: "MON,WED,FRI"
        if (dow.contains(",")) {
            String list = Arrays.stream(dow.split(","))
                    .map(e -> CronDictionary.DAYS_OF_THE_WEEK.get(e))
                    .toList()
                    .toString();
            return "on " + list;
        }

        // Normal single day
        return "on " + CronDictionary.DAYS_OF_THE_WEEK.get(dow);
    }

}
