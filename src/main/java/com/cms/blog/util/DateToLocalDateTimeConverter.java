package com.cms.blog.util;

import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.TimeZone;

public class DateToLocalDateTimeConverter
{
    static SimpleDateFormat normal = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    static SimpleDateFormat custom = new SimpleDateFormat("MMM-dd-yyyy HH:mm:ss a Z");
    static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM-dd-yyyy HH:mm:ss a Z");
    
    public LocalDateTime dateToLocalDateConverter(Date date)
    {
        return date.toInstant()
                   .atZone(ZoneId.systemDefault())
                   .toLocalDateTime();
    }
    
    public Date localDateTimeToDateConverter(LocalDateTime dateToConvert)
    {
        ZonedDateTime zdt = dateToConvert.atZone(ZoneId.systemDefault());
        return Date.from(zdt.toInstant());
    }
    
    public ZonedDateTime stringToDateConverter(String date)
    {
        LocalDateTime localDateTime = LocalDateTime.parse(date, formatter);
        localDateTime.atZone(TimeZone.getDefault().toZoneId());
        OffsetDateTime offsetDateTime = localDateTime.atOffset(ZoneOffset.UTC);
        return offsetDateTime.toZonedDateTime();
        // return offsetDateTime.atZoneSimilarLocal(TimeZone.getDefault().toZoneId());
        // return offsetDateTime.atZoneSameInstant(ZoneId.systemDefault());
    }
    
    public String zonedDateTimeToString(ZonedDateTime zdt)
    {
        return zdt.format(DateTimeFormatter.ofPattern("MMM-dd-yyyy HH:mm:ss a Z"));
    }
    
    public static void main(String[] args)
    {
        String sdate = "Sep-23-2020 10:49:10 AM +0100";
        ZonedDateTime zonedDateTime = new DateToLocalDateTimeConverter().stringToDateConverter(sdate);
        sdate = new DateToLocalDateTimeConverter().zonedDateTimeToString(zonedDateTime);
        System.out.println("sdate = " + sdate);
        
        /*LocalDateTime localDateTime = LocalDateTime.now(); //get current date time
        localDateTime.atZone(ZoneId.systemDefault());
        OffsetDateTime offsetDateTime = localDateTime.atOffset(ZoneOffset.UTC);
        ZonedDateTime zonedDateTime = offsetDateTime.atZoneSameInstant(ZoneId.systemDefault());
        System.out.println("Current Time " + localDateTime);
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM-dd-yyyy HH:mm:ss a Z");
        String formatDateTime = zonedDateTime.format(formatter);
        
        System.out.println("Formatted Time :" + formatDateTime);
        
        ZonedDateTime zdt = new DateToLocalDateTimeConverter().stringToDateConverter("Sep-19-2020 13:21:32 PM +0100");
    
        String format1 = zdt.format(DateTimeFormatter.ofPattern("MMM-dd-yyyy HH:mm:ss a Z"));
        System.out.println("format1 = " + format1);
        
        Date temp = new DateToLocalDateTimeConverter().localDateTimeToDateConverter(localDateTime);
        String format = custom.format(temp);
        System.out.println("formatted = " + format);*/
    }
}