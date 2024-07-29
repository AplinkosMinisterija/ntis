package lt.project.ntis.enums;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;

/**
 * Klasė skirta apibrėžti galimas GRAPH_DATA_PERIOD klasifikatoriaus reikšmes
 */
public enum NtisGraphDataPeriod {

    GRP_DAT_PRD_LAST_7_DAYS("GRP_DAT_PRD_LAST_7_DAYS"),

    GRP_DAT_PRD_THIS_WEEK("GRP_DAT_PRD_THIS_WEEK"),

    GRP_DAT_PRD_LAST_WEEK("GRP_DAT_PRD_LAST_WEEK"),

    GRP_DAT_PRD_THIS_MONTH("GRP_DAT_PRD_THIS_MONTH"),

    GRP_DAT_PRD_LAST_MONTH("GRP_DAT_PRD_LAST_MONTH"),

    GRP_DAT_PRD_THIS_YEAR("GRP_DAT_PRD_THIS_YEAR");

    String code;

    NtisGraphDataPeriod(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    /**
     * Metodas pasirinkto periodo pradžios ir pabaigos datas
     * @param period - pasirinkto periodo klasifikatorius
     * @return ArrayList<String> 
     * @throws Exception
     */
    public static final ArrayList<String> checkPeriod(String period) throws Exception {
        ArrayList<String> dates = new ArrayList<String>();
        LocalDate firstDay = LocalDate.now();
        LocalDate lastDay = LocalDate.now();
        if (period.equalsIgnoreCase(GRP_DAT_PRD_LAST_7_DAYS.getCode())) {
            lastDay = LocalDate.now();
            firstDay = lastDay.plusDays(-6);
        } else if (period.equalsIgnoreCase(GRP_DAT_PRD_THIS_WEEK.getCode())) {
            firstDay = LocalDate.now().with(DayOfWeek.MONDAY);
            lastDay = LocalDate.now().with(DayOfWeek.SUNDAY);
        } else if ((period.equalsIgnoreCase(GRP_DAT_PRD_LAST_WEEK.getCode()))) {
            firstDay = LocalDate.now().with(DayOfWeek.MONDAY).plusDays(-7);
            lastDay = LocalDate.now().with(DayOfWeek.SUNDAY).plusDays(-7);
        } else if (period.equalsIgnoreCase(GRP_DAT_PRD_THIS_MONTH.getCode())) {
            firstDay = LocalDate.now().withDayOfMonth(1);
            lastDay = firstDay.withDayOfMonth(firstDay.getMonth().length(firstDay.isLeapYear()));
        } else if (period.equalsIgnoreCase(GRP_DAT_PRD_LAST_MONTH.getCode())) {
            firstDay = LocalDate.now().minusMonths(1).with(TemporalAdjusters.firstDayOfMonth());
            lastDay = firstDay.withDayOfMonth(firstDay.getMonth().length(firstDay.isLeapYear()));
        } else if (period.equalsIgnoreCase(GRP_DAT_PRD_THIS_YEAR.getCode())) {
            firstDay = LocalDate.now().with(TemporalAdjusters.firstDayOfYear());
            lastDay = LocalDate.now().with(TemporalAdjusters.lastDayOfYear());
        }
        dates.add(firstDay.toString());
        dates.add(lastDay.toString());
        return dates;
    }

}
