package utilities;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Locale;

public class DateTimeUtils {
    public static LocalDate currentDate = LocalDate.now();
    public static LocalTime currentTime = LocalTime.now();
    public static ZoneId zoneId = ZoneId.of("America/Chicago");
    public static ZonedDateTime zonedDateTime;

    public static Instant convertDateTime(String dateTime, String format){
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_INSTANT;
        TemporalAccessor temporalAccessor = dateTimeFormatter.parse(dateTime);
        Instant instant = Instant.from(temporalAccessor);
        return instant;
    }
}
