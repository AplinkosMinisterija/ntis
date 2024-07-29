package eu.itreegroup.spark.dao.daogen.common;

import eu.itreegroup.spark.dao.daogen.struct.TableColumn;

public class Utils {

    public static final String JAVA_OBJECT_TYPE_STRING = "String";

    public static final String JAVA_OBJECT_TYPE_DATE = "Date";

    public static final String JAVA_OBJECT_TYPE_DOUBLE = "Double";

    public static String getJavaType(String dbType) {
        String answer = null;
        try {
            switch (dbType.toUpperCase()) {
                case "VARCHAR2":
                    answer = JAVA_OBJECT_TYPE_STRING;
                    break;
                case "VARCHAR":
                    answer = JAVA_OBJECT_TYPE_STRING;
                    break;
                case "DATE":
                    answer = JAVA_OBJECT_TYPE_DATE;
                    break;
                case "DATETIME2":
                    answer = JAVA_OBJECT_TYPE_DATE;
                    break;
                case "NUMBER":
                    answer = JAVA_OBJECT_TYPE_DOUBLE;
                    break;
                case "TEXT":
                    answer = JAVA_OBJECT_TYPE_STRING;
                    break;
                case "SERIAL":
                    answer = JAVA_OBJECT_TYPE_DOUBLE;
                    break;
                case "INT2":
                    answer = JAVA_OBJECT_TYPE_DOUBLE;
                    break;
                case "INT4":
                    answer = JAVA_OBJECT_TYPE_DOUBLE;
                    break;
                case "INT8":
                    answer = JAVA_OBJECT_TYPE_DOUBLE;
                    break;
                case "INT":
                    answer = JAVA_OBJECT_TYPE_DOUBLE;
                    break;
                case "BIGINT":
                    answer = JAVA_OBJECT_TYPE_DOUBLE;
                    break;
                case "FLOAT8":
                    answer = JAVA_OBJECT_TYPE_DOUBLE;
                    break;
                case "TIMESTAMP":
                    answer = JAVA_OBJECT_TYPE_DATE;
                    break;
                case "TIMESTAMPTZ":
                    answer = JAVA_OBJECT_TYPE_DATE;
                    break;
                case "BPCHAR":
                    answer = JAVA_OBJECT_TYPE_STRING;
                    break;
                case "CLOB":
                    answer = JAVA_OBJECT_TYPE_STRING;
                    break;
                case "NUMERIC":
                    answer = JAVA_OBJECT_TYPE_DOUBLE;
                    break;
                case "GEOMETRY":
                    answer = JAVA_OBJECT_TYPE_STRING;
                    break;
                case "JSON":
                    answer = JAVA_OBJECT_TYPE_STRING;
                    break;

            }

            if (answer == null) {
                throw new Exception("Mapping error for java type. DB type is:" + dbType);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return answer;
    }

    public static String getMaping4insertstatement(TableColumn column) {
        String sqlType = null;
        try {
            if ("NUMBER".equalsIgnoreCase(column.getDbType()) || //
                    "SERIAL".equalsIgnoreCase(column.getDbType()) || //
                    "BIGINT".equalsIgnoreCase(column.getDbType()) || //
                    "INT".equalsIgnoreCase(column.getDbType()) || //
                    "INT4".equalsIgnoreCase(column.getDbType()) || //
                    "INT8".equalsIgnoreCase(column.getDbType()) || //
                    "FLOAT8".equalsIgnoreCase(column.getDbType())) {
                sqlType = "NUMERIC";
            } else {
                if ("VARCHAR".equalsIgnoreCase(column.getDbType()) || //
                        "VARCHAR2".equalsIgnoreCase(column.getDbType())) {
                    sqlType = "VARCHAR";
                } else {
                    if ("CHAR".equalsIgnoreCase(column.getDbType()) || //
                            "BPCHAR".equalsIgnoreCase(column.getDbType())) {
                        sqlType = "CHAR";
                    } else {
                        if ("TEXT".equalsIgnoreCase(column.getDbType()) || //
                                "CLOB".equalsIgnoreCase(column.getDbType())) {
                            sqlType = "VARCHAR";
                        }
                    }
                }
            }
            if (sqlType == null) {
                sqlType = "";
                throw new Exception("Mapping error for DB maping type. DB type is:" + column.getDbType());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return sqlType;
    }
}
