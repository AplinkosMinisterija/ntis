package eu.itreegroup.spark.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class DBStatementConstructor {

    private static final Logger log = LoggerFactory.getLogger(DBStatementConstructor.class);

    public static String DB_TYPE_ORACLE = "ORACLE";

    public static String DB_TYPE_POSTGRESQL = "PostgreSQL";

    public static final int DATE_FORMAT_DAY_DB = 1;

    public static final int DATE_FORMAT_DAY_TIME_DB = 2;

    public static final int DATE_FORMAT_DAY_JAVA = 3;

    public static final int DATE_FORMAT_DAY_TIME_JAVA = 4;

    @Value("${app.db.type}")
    String dbType;

    public String getDbType() {
        return dbType;
    }

    public String getSysdateCommand() throws Exception {
        log.debug("Get sysdate funciont for db: " + dbType);
        if (DB_TYPE_ORACLE.equalsIgnoreCase(dbType)) {
            return " sysdate ";
        } else {
            if (DB_TYPE_POSTGRESQL.equalsIgnoreCase(dbType)) {
                return " now() ";
            }
        }
        throw new Exception("Sysdate function can't be identified for db type: '" + dbType + "'");
    }

    public String getTruncatedColumnCommand(String columnName) throws Exception {
        if (DB_TYPE_ORACLE.equalsIgnoreCase(dbType)) {
            return "TRUNC(" + columnName + ") ";
        } else {
            if (DB_TYPE_POSTGRESQL.equalsIgnoreCase(dbType)) {
                return "DATE_TRUNC('day', " + columnName + ")";
            }
        }
        throw new Exception("Failed to truncate column: " + columnName);
    }

    public String getTruncateSysdateCommand() throws Exception {
        log.debug("Get truncate sysdate function for db: " + dbType);
        if (DB_TYPE_ORACLE.equalsIgnoreCase(dbType)) {
            return " trunc(sysdate) ";
        } else {
            if (DB_TYPE_POSTGRESQL.equalsIgnoreCase(dbType)) {
                return " current_date ";
            }
        }
        throw new Exception("Sysdate function can't be identified for db type: '" + dbType + "'");
    }

    /**
     * Function will return sql statement part for pagination implementation.
     * @param skip_rows - rows count that should be skipped.
     * @param page_size - page size that should be returned to the client
     * @param pageIncreaser - page multiplier for record count identification
     * @return - statement part that limit data from db according to provided parameters.
     * @throws Exception
     */
    public String getPagingString(Double skip_rows, Double page_size, int pageIncreaser) throws Exception {
        log.debug("Get paging string for: " + dbType);
        if (DB_TYPE_ORACLE.equalsIgnoreCase(dbType)) {
            return " OFFSET " + skip_rows + " ROWS FETCH NEXT " + (page_size * pageIncreaser) + " ROWS ONLY ";
        } else {
            if (DB_TYPE_POSTGRESQL.equalsIgnoreCase(dbType)) {
                return " limit " + (page_size * pageIncreaser) + " offset " + skip_rows + " ";
            }
        }
        throw new Exception("Sysdate function can't be identified for db type: '" + dbType + "'");
    }

    public String getRecordLimitString(Double recordCount) throws Exception {
        if (recordCount == null) {
            return null;
        } else {
            return getPagingString(0d, recordCount, 1);
        }
    }

    public String getListAggFunctionName() throws Exception {
        log.debug("Get truncate sysdate function for db: " + dbType);
        if (DB_TYPE_ORACLE.equalsIgnoreCase(dbType)) {
            return " listagg ";
        } else {
            if (DB_TYPE_POSTGRESQL.equalsIgnoreCase(dbType)) {
                return " STRING_AGG  ";
            }
        }
        throw new Exception("List aggregation function can't be identified for db type: '" + dbType + "'");

    }

    /**
     * FUnction will construct string for select statement. Will concatenate provided strings (column names) to the one string.
     * @param colNames - column names that values should be concatenated in select statement.
     * @return - concatenated column, for separation will be used '|'
     */
    public String colNamesToConcatString(String... colNames) {
        if (DB_TYPE_POSTGRESQL.equalsIgnoreCase(dbType)) {
            for (int i = 0; i < colNames.length; i++) {
                colNames[i] = "COALESCE(" + colNames[i] + "::text,'')";
            }
        }
        return String.join("||'|'||", colNames);
    }

    /**
     * Function will construct sql statement part for period validation. Constructed part will validate if current date (sysdate) is between 
     * startDateColumn and endDateColumn. In case if endDateColumn value is null will be used current date. For validation will be used dates without time.
     * @param startDateColumn - period start date column name, that should be used for validation
     * @param endDateColumn - period end date column name, that should be used for validation.
     * @return statement fragment that validate if current date is between startDateColumn and endDateColumn
     */
    public String getPeriodValidationForCurrentDateStr(String startDateColumn, String endDateColumn) throws Exception {
        return getPeriodValidationForCurrentDateStr(startDateColumn, endDateColumn, true);
    }

    /**
     * Function will construct sql statement part for period validation. Constructed part will validate if current date (sysdate) is between 
     * startDateColumn and endDateColumn. In case if endDateColumn value is null will be used current date.
     * @param startDateColumn - period start date column name, that should be used for validation
     * @param endDateColumn - period end date column name, that should be used for validation.
     * @param truncateDate - for validation should be truncated or not dates.
     * @return statement fragment that validate if current date is between startDateColumn and endDateColumn
     * @throws Exception
     */
    public String getPeriodValidationForCurrentDateStr(String startDateColumn, String endDateColumn, boolean truncateDate) throws Exception {
        StringBuffer answer = new StringBuffer();
        if (DB_TYPE_ORACLE.equalsIgnoreCase(dbType)) {
            answer.append(truncateDate ? getTruncateSysdateCommand() : getSysdateCommand());
            answer.append(" between ");
            answer.append(startDateColumn);
            answer.append(" and nvl(");
            answer.append(endDateColumn);
            answer.append(", ");
            answer.append(truncateDate ? getTruncateSysdateCommand() : getSysdateCommand());
            answer.append(") ");
        } else {
            if (DB_TYPE_POSTGRESQL.equalsIgnoreCase(dbType)) {
                answer.append(truncateDate ? getTruncateSysdateCommand() : getSysdateCommand());
                answer.append(" between ");
                answer.append(startDateColumn);
                answer.append(" and COALESCE(");
                answer.append(endDateColumn);
                answer.append(", ");
                answer.append(truncateDate ? getTruncateSysdateCommand() : getSysdateCommand());
                answer.append(") ");
            }
        }
        return answer.toString();
    }

    /**
     * Function will check if current DB Statement manager works with oracle DB. In case if works with Oracle will return true 
     * otherwise return false.
     * @return in case if DB Statement manager works with Oracle DB answer will be true otherwise false.
     */
    public boolean useOracle() {
        return DBStatementManager.DB_TYPE_ORACLE.equalsIgnoreCase(this.getDbType());
    }

    /**
     * Function will check if current DB Statement manager works with postgre DB. In case if works with Postgre will return true 
     * otherwise return false.
     * @return in case if DB Statement manager works with Postgre DB answer will be true otherwise false.
     */
    public boolean usePostgre() {
        return DBStatementManager.DB_TYPE_POSTGRESQL.equalsIgnoreCase(this.getDbType());
    }
}
