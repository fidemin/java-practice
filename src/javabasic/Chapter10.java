package javabasic;

import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class Chapter10 {
    final static String[] DAY_OF_WEEK = {"", "Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};

    public static void main(String[] args){
        Calendar cal1 = Calendar.getInstance();
        printCalendar(cal1);

        Calendar cal2 = Calendar.getInstance();
        cal2.set(Calendar.MONTH, Calendar.JULY);
        printCalendar(cal2);

        long diff = (cal1.getTimeInMillis() - cal2.getTimeInMillis()) / 1000;
        System.out.println("cal1 - cal2 seconds: " + diff);

        Calendar cal3 = Calendar.getInstance();
        cal3.add(Calendar.MONTH, -1);
        printCalendar(cal3);

        DecimalFormat df = new DecimalFormat("#,###.##");
        System.out.println(df.format(1234567.891)); // 1,234,567.89

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SZ");
        Date d = new Date(cal1.getTimeInMillis());
        System.out.println(sdf.format(d));

        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = sdf2.parse("2022-08-22");
            System.out.println(sdf.format(date));
        } catch (ParseException e) {
           System.out.println("Wrong format");
        }

        MessageFormat mf1 = new MessageFormat("Name: {0}, Age: {1}");
        Object[] arguments = {"Yunhong", "22"};
        System.out.println(mf1.format(arguments));

        // LocalDate and LocalTime are immutable
        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.now();
        System.out.println(date.withYear(2021));
        System.out.println(date.plusMonths(1));
        System.out.println(time.truncatedTo(ChronoUnit.HOURS));

        LocalDate birthDate = LocalDate.of(1984,2, 17);
        LocalTime birthTime = LocalTime.of(23,2, 17);


        Instant now = Instant.now();
        System.out.println(now.toEpochMilli() + " " + now.getEpochSecond() + " " + now.getNano());

        // Date to instance
        Instant ist1 = d.toInstant();

        // LocalDateTime, ZonedDateTime
        LocalDateTime ldt = LocalDateTime.of(date, time);
        System.out.println(ldt);

        ZoneId zid = ZoneId.of("Asia/Seoul");
        ZonedDateTime zdt = ldt.atZone(zid);
        System.out.println(zdt);
        OffsetDateTime odt = zdt.toOffsetDateTime();
        System.out.println(odt);

        GregorianCalendar gCal = GregorianCalendar.from(zdt);
        printCalendar(gCal);

        ZoneId zid2 = ZoneId.of("America/New_York");
        System.out.println(ZonedDateTime.now().withZoneSameInstant(zid2));



    }

    static void printCalendar(Calendar cal) {
        System.out.printf("%d-%02d-%02dT%02d:%02d:%02d+%02d:00 %s\n",
                cal.get(Calendar.YEAR), cal.get(Calendar.MONTH)+1, cal.get(Calendar.DAY_OF_MONTH),
                cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), cal.get(Calendar.SECOND),
                cal.get(Calendar.ZONE_OFFSET) / (60 * 60 * 1000), DAY_OF_WEEK[cal.get(Calendar.DAY_OF_WEEK)]);
    }
}
