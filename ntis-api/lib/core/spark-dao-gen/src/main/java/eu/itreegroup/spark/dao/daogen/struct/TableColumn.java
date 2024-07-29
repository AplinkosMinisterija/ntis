package eu.itreegroup.spark.dao.daogen.struct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import eu.itreegroup.spark.dao.daogen.common.Utils;

public class TableColumn {

    Logger logger = LoggerFactory.getLogger(TableColumn.class);

    String dbName;

    String dbType;

    int dbPrecision;

    int dbScale;

    boolean identifier = false;

    boolean updatable = false;

    boolean dbIsNullable;

    boolean auditColumn = false;

    boolean flexField = false;

    String sequenceName;

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public String getDbType() {
        return dbType;
    }

    public void setDbType(String dbType) {
        this.dbType = dbType;
    }

    public int getDbPrecision() {
        return dbPrecision;
    }

    public void setDbPrecision(int dbPrecision) {
        this.dbPrecision = dbPrecision;
    }

    public boolean isDbIsNullable() {
        return dbIsNullable;
    }

    public void setDbIsNullable(boolean dbIsNullable) {
        this.dbIsNullable = dbIsNullable;
    }

    public String getJavaName() {
        return dbName.toLowerCase();
    }

    public String getJavaNameInitCap() {
        return getJavaName().substring(0, 1).toUpperCase() + getJavaName().substring(1);
    }

    public String getJavaType() {
        String answer = Utils.getJavaType(getDbType());
        return answer;
    }

    public void setIdentifier(boolean identifier) {
        this.identifier = identifier;
    }

    public boolean isIdentifier() {
        return this.identifier;
    }

    public void setUpdateble(boolean updatable) {
        this.updatable = updatable;
    }

    public boolean isUpdatable() {
        return this.updatable;
    }

    public String getSequenceName() {
        return this.sequenceName;
    }

    public void setSequenceName(String sequenceName) {
        this.sequenceName = sequenceName;
    }

    public boolean isAuditColumn() {
        return auditColumn;
    }

    public void setAuditColumn(boolean auditColumn) {
        this.auditColumn = auditColumn;
    }

    public void setUpdatable(boolean updatable) {
        this.updatable = updatable;
    }

    public void setFlexField(boolean flexfield) {
        this.flexField = flexfield;
    }

    public boolean isFlexField() {
        return this.flexField;
    }

    public int getDbScale() {
        return dbScale;
    }

    public void setDbScale(int dbScale) {
        this.dbScale = dbScale;
    }

    private String getComparitionString(String param1, String param2) {
        if (Utils.JAVA_OBJECT_TYPE_DATE.equals(getJavaType())) {
            return "(" + param1 + " != null && (" + param2 + " == null ||" + param1 + ".compareTo(" + param2 + ") != 0 ))";
        } else
            return "(" + param1 + " != null && !" + param1 + ".equals(" + param2 + ")) ";
    }

    public String getSettersGetter() {
        String anotation = "";
        if (this.isFlexField()) {
            anotation = "   @JsonIgnore\r\n";
        }
        return anotation + "   public void set" + getJavaNameInitCap() + "(" + getJavaType() + " " + getJavaName() + ") {\r\n" + //
                "      if (this.isForceUpdate() || \r\n" + //
                "               " + getComparitionString("this." + getJavaName(), getJavaName()) + " ||\r\n" + //
                "               " + getComparitionString(getJavaName(), "this." + getJavaName()) + "){\r\n" + //
                "         this." + getJavaName() + "_changed = true; \r\n" + //
                "         this.record_changed = true;\r\n" + //
                "         this." + getJavaName() + " = " + getJavaName() + ";\r\n" + //
                "      }\r\n" + //
                "   }\r\n" + //
                anotation + //
                "   public " + getJavaType() + " get" + getJavaNameInitCap() + "() {\r\n" + //
                "      return this." + getJavaName() + ";\r\n" + //
                "   }";
    }

    @Override
    public String toString() {
        return "dbName: " + getDbName() + " dbType: " + getDbType() + (this.isIdentifier() ? " [IDENTIFIER]" : "");
    }
}
