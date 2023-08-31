package utilities;

import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Date;
import java.util.Locale;

public class DateTimeUtils {

    public static Instant convertDateTime(String dateTime){
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_INSTANT;
        TemporalAccessor temporalAccessor = dateTimeFormatter.parse(dateTime);
        Instant instant = Instant.from(temporalAccessor);
        return instant;
    }

    public static String getCurrentDate() {
        Date date = new Date();
        return date.toString().replace(":", "_").replace(" ", "_");
    }

    public static String getCurrentDateTime() {
        Date now = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return formatter.format(now);
    }

    public static String getCurrentDateTimeCustom(String separator_Character) {
        Date now = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String timeStamp = formatter.format(now).replace("/", separator_Character);
        timeStamp = timeStamp.replace(" ", separator_Character);
        timeStamp = timeStamp.replace(":", separator_Character);
        return timeStamp;
    }
}
