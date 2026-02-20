package com.example.comicbe.utils;

import com.example.comicbe.constant.AppConstant;
import org.springframework.util.StringUtils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.temporal.TemporalAdjusters;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * @author datdv
 */
public class DateTimeUtils {

    public static String currentLongTimeStr(Integer subStr) {
        String currentTimeString = String.valueOf(new Date().getTime());
        if (subStr != null) {
            return currentTimeString.substring(currentTimeString.length() - subStr);
        }
        return currentTimeString;
    }

    public static String formatDateTimeNow() {
        return getDateTimeNow(null);
    }

    public static String getDateTimeNow(String format) {
        if (format == null) {
            format = "yyyy/MM/dd HH:mm:ss";
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        Date now = new Date(System.currentTimeMillis());
        return simpleDateFormat.format(now);
    }

    /**
     * parseDate
     *
     * @param d
     * @param format
     * @return
     */
    public static Date parseDate(String d, String format) {
        if (!StringUtils.hasText(d)) {
            return null;
        } else {
            if (!StringUtils.hasText(format)) {
                format = "yyyy-MM-dd";
            }

            SimpleDateFormat sdf = new SimpleDateFormat(format);
            sdf.setTimeZone(TimeZone.getTimeZone(ZoneId.of(AppConstant.DATE.TIMEZONE_ICT)));
            sdf.setLenient(false);

            try {
                return sdf.parse(d);
            } catch (ParseException var4) {
                throw new AppException(ErrorCode.FORMAT_DATE_FAILED);
            }
        }
    }

    public static Date parseDateNotTimeZone(String d, String format) {
        if (!StringUtils.hasText(d)) {
            return null;
        } else {
            if (!StringUtils.hasText(format)) {
                format = "yyyy-MM-dd";
            }

            SimpleDateFormat sdf = new SimpleDateFormat(format);
            sdf.setLenient(false);

            try {
                return sdf.parse(d);
            } catch (ParseException var4) {
                throw new AppException(ErrorCode.FORMAT_DATE_FAILED);
            }
        }
    }

    /**
     * getDateTimeNow
     *
     * @return
     */
    public static Date getDateTimeNow() {
        return new Date(System.currentTimeMillis());
    }

    /**
     * getDateTimeNowDate
     *
     * @param format
     * @return
     */
    public static Date getDateTimeNowDate(String format) {
        return parseDate(getDateTimeNow(format), format);
    }

    /**
     * getLocalTimeNow
     *
     * @return LocalTime
     */
    public static LocalTime getLocalTimeNow() {
        return LocalTime.now();
    }

    /**
     * getStartOfDay
     *
     * @return
     */
    public static Date getStartOfDay() {
        Instant startTime = LocalDate.now(ZoneId.of(ZoneId.systemDefault().getId())).atStartOfDay(ZoneId.of(ZoneId.systemDefault().getId())).toInstant();
        return Date.from(startTime);
    }

    /**
     * formatDate : format date using String format
     *
     * @param date    : date for format
     * @param format: format String
     * @return: String {java.lang.String}
     */
    public static String formatDate(Date date, String format) {
        if (format == null) {
            format = "dd/MM/yyyy HH:mm:ss";
        }
        if (date == null) {
            return null;
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        return simpleDateFormat.format(date);

    }

    /**
     * formatDateTimeQuery : format String date use query in database
     *
     * @param date : string date input of function
     * @return String {java.lang.String}
     * @throws ParseException
     */
    public static String formatDateTimeQuery(String date) throws ParseException {
        String dateNew = date;
        if (date.trim().contains("-")) {
            dateNew = date.trim().replace("-", "/");
        }
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date date1 = new SimpleDateFormat("dd/MM/yyyy").parse(dateNew);
        return formatter.format(date1);
    }

    /**
     * convertStringRequestToTimesTamp : convert string date client request to Timestamp
     *
     * @param date       : string date client request
     * @param dateFormat : format of date (client request)
     * @return Timestamp {java.sql.Timestamp}
     */
    public static Timestamp convertStringRequestToTimesTamp(String date, String dateFormat) {
        try {
            if (!StringUtils.hasText(date)) {
                return null;
            } else {
                DateFormat formatter = new SimpleDateFormat(dateFormat);
                Timestamp result = null;
                if (date.contains("T")) {
                    java.sql.Date dateAfterFormat = (java.sql.Date) formatter.parse(date.trim().replaceAll("Z$", "+0000"));
                    result = new Timestamp(dateAfterFormat.getTime());
                } else {
                    Date dateAfterFormat = formatter.parse(date);
                    result = new Timestamp(dateAfterFormat.getTime());
                }
                return result;
            }
        } catch (Exception e) {
            throw new AppException(ErrorCode.FAILED);
        }
    }

    /**
     * compareAfterDateTimeNow : compare date after vs date now
     *
     * @param date : date for compare
     * @return Boolean
     */
    public static Boolean compareAfterDateTimeNow(Date date) {
        Date dateNow = new Date(System.currentTimeMillis());
        if (date.after(dateNow)) {
            return true;
        }
        return false;
    }

    /**
     * equalsDateTimeNow
     *
     * @param date
     * @return
     */
    public static Boolean equalsDateTimeNow(Date date) {
        Date dateNow = new Date(System.currentTimeMillis());
        if (date.equals(dateNow)) {
            return true;
        }
        return false;
    }

    /**
     * compareBeforeDateTimeNow : compare date before vs date now
     *
     * @param date : date for compare
     * @return Boolean
     */
    public static Boolean compareBeforeDateTimeNow(Date date) {
        Date dateNow = new Date(System.currentTimeMillis());
        if (date.before(dateNow)) {
            return true;
        }
        return false;
    }

    /**
     * minusDayAndDateTimeNow : minus date in database vs date now
     *
     * @param date : date in database
     * @return Long {java.lang.Long}
     */
    public static Long minusDayAndDateTimeNow(Date date) {
        Date dateNow = new Date(System.currentTimeMillis());
        Calendar calendarDateEnd = Calendar.getInstance();
        Calendar calendarDateNow = Calendar.getInstance();
        calendarDateEnd.setTime(date);
        calendarDateNow.setTime(dateNow);
        return (calendarDateEnd.getTime().getTime() - calendarDateNow.getTime().getTime()) / (24 * 3600 * 1000);
    }

    /**
     * convertDateToStringUseFormatDate
     *
     * @param date   : date for format
     * @param format : String format of date
     * @return String {java.lang.String}
     */
    public static String convertDateToStringUseFormatDate(Date date, String format) {
        if (format == null) {
            format = "dd-MM-yyyy";
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        return simpleDateFormat.format(date);
    }


    /**
     * getSecond
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public static int getSecond(Date startDate, Date endDate) {
        LocalDateTime ldtStart = LocalDateTime.ofInstant(startDate.toInstant(), ZoneId.systemDefault());
        LocalDateTime ldtEndDate = LocalDateTime.ofInstant(endDate.toInstant(), ZoneId.systemDefault());
        return (int) Duration.between(ldtStart, ldtEndDate).getSeconds() / 60 + 1;
    }

    /**
     * getDayWeek
     *
     * @param date: data to get day week
     * @return {int}
     */
    public static int getDayWeek(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.DAY_OF_WEEK);
    }

    public static LocalDateTime startOfDay(LocalDate date) {
        return date.atStartOfDay();
    }

    public static LocalDateTime endOfDay(LocalDate date) {
        return date.atTime(LocalTime.MAX);
    }

    public static LocalDateTime startOfMonth(LocalDate date) {
        return date.withDayOfMonth(1).atStartOfDay();
    }

    public static LocalDateTime endOfMonth(LocalDate date) {
        return date.withDayOfMonth(date.lengthOfMonth()).atTime(LocalTime.MAX);
    }

    public static LocalDateTime startOfYear(LocalDate date) {
        return date.withDayOfYear(1).atStartOfDay();
    }

    public static LocalDateTime endOfYear(LocalDate date) {
        return date.withDayOfYear(date.lengthOfYear()).atTime(LocalTime.MAX);
    }

    public static LocalDateTime startOfWeek(LocalDate date) {
        return date.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY))
                .atStartOfDay();
    }

    public static LocalDateTime endOfWeek(LocalDate date) {
        return date.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY))
                .atTime(LocalTime.MAX);
    }
}