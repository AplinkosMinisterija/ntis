package eu.itreegroup.spark.dao.common;

import java.text.Normalizer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

import org.apache.commons.lang3.time.DateUtils;

public class Utils {

    public static int OPERATION_UPDATE = 1;

    public static int OPERATION_INSERT = 2;

    public static java.sql.Date getSqlDate(java.util.Date utilDate) {
        if (utilDate != null) {
            return new java.sql.Date(utilDate.getTime());
        } else {
            return null;
        }
    }

    public static java.sql.Timestamp getSqlTimestamp(java.util.Date utilDate) {
        if (utilDate != null) {
            return new java.sql.Timestamp(utilDate.getTime());
        } else {
            return null;
        }
    }

    public static String removeWhitespacesFromEnd(String str) {
        if (str != null) {
            str = str.replaceAll("\\s+$", "");
        }
        return str;
    }

    public static Double getDouble(Object obj) {
        Double answer = null;
        if (obj != null) {
            if (obj instanceof java.math.BigDecimal) {
                answer = Double.valueOf(((java.math.BigDecimal) obj).doubleValue());
            } else {
                if (obj instanceof java.lang.Double) {
                    return (Double) obj;
                } else {
                    if (obj instanceof java.lang.Integer) {
                        return ((java.lang.Integer) obj).doubleValue();
                    } else {
                        if (obj instanceof java.lang.String) {
                            if ("".equals(obj)) {
                                return null;
                            } else {
                                return Double.valueOf((String) obj);
                            }
                        }
                    }
                }
            }
        }
        return answer;
    }

    /**
     * Function will return long value.
     * @return Long object.
     */
    public static Long getLong(Object obj) {
        Long answer = null;
        if (obj != null) {
            if (obj instanceof java.lang.Double) {
                return ((Double) obj).longValue();
            } else {
                if (obj instanceof java.math.BigDecimal) {
                    answer = Long.valueOf(((java.math.BigDecimal) obj).longValue());
                } else {
                    if (obj instanceof java.lang.Long) {
                        return (Long) obj;
                    } else {
                        if (obj instanceof java.lang.Integer) {
                            return ((java.lang.Integer) obj).longValue();
                        } else {
                            if (obj instanceof java.lang.String) {
                                if ("".equals(obj)) {
                                    return null;
                                } else {
                                    return Long.valueOf((String) obj);
                                }
                            }
                        }
                    }
                }
            }

        }
        return answer;
    }

    /**
     * Function will return current date object. Date will be without time. Function works as function getDate(Date date, String format) wrapper.  
     * @return current date without time.
     * @throws ParseException
     */
    public static Date getDate() throws ParseException {
        return getDate(new Date(), "yyyy-MM-dd");
    }

    /**
     * Function will return truncated date (date without time)
     * @param date - date object that should be truncated
     * @return
     * @throws ParseException
     */
    public static Date getDate(Date date) throws ParseException {
        return getDate(date, "yyyy-MM-dd");
    }

    /**
     * Function will truncate provided date according to the provided format. In case if date is null function will return null.
     * @param date - date that should be truncated
     * @param format - date format that should be used for truncation
     * @return - truncated date
     * @throws ParseException
     */
    public static Date getDate(Date date, String format) throws ParseException {
        if (date == null) {
            return null;
        } else {
            SimpleDateFormat formatter = new SimpleDateFormat(format);
            return DateUtils.truncate(formatter.parse(formatter.format(date)), Calendar.DATE);
        }
    }

    /**
     * Function will convert from string to date with provided format. In case if dateStr is null then function will return null.
     * @param dateStr string that should be converted to the date
     * @param format - date format.
     * @return Formated date.
     * @throws ParseException
     */
    public static Date getDateFromString(String dateStr, String format) throws ParseException {
        if (dateStr == null || "".equals(dateStr)) {
            return null;
        } else {
            SimpleDateFormat formatter = new SimpleDateFormat(format);
            return formatter.parse(dateStr);
        }
    }

    /**
     * Function will return date as string. For translation will be used provided date format. In case if provided date object equals to null then
     * function will return null.
     * @param date - object that should be converted to the string
     * @param format - date format mask
     * @return date converted to the string
     * 
     * @throws ParseException
     */
    public static String getStringFromDate(Date date, String format) throws ParseException {
        if (date == null) {
            return null;
        } else {
            SimpleDateFormat formatter = new SimpleDateFormat(format);
            return formatter.format(date);
        }
    }

    /**
     * Function will replace national characters to Latin. In provided source will be replaced all national characters to the 
     * Latin characters. In case if in text exists characters ąčęėįšųūžĄČĘĖĮŠŲŪŽ they will be replaced into aceeisuuzAXŽCEEISUUZ.
     * @param source which should be modified by removing national characters
     * @return modified string without nation characters.
     */
    public static String replaceNationalCharacters(String source) {
        source = Normalizer.normalize(source, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(source).replaceAll("");
    }

    /**
     * Function will construct sql statement part that will for provided parameter (or string) translate non Latin letters to the
     * Latin letters.
     * @param paramTxt - text or parameter that should be provided to translation function.
     * @return sql statement part that will translate provided parameter or text.
     */
    public static String getSqlForTranslation(String paramTxt) {
        return "spr_translate_latin(" + paramTxt + ",'N')";
    }
}
