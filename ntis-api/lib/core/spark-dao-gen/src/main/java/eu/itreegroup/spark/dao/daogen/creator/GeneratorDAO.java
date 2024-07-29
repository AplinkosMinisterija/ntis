package eu.itreegroup.spark.dao.daogen.creator;

import eu.itreegroup.spark.dao.daogen.struct.Table;
import eu.itreegroup.spark.dao.daogen.struct.TableColumn;

public class GeneratorDAO extends Generator {

    public GeneratorDAO(Table table) {
        super(table);
        setClassType(CLASS_TYPE_DAO);
    }

    @Override
    public String generateGenClasses(String suffix) {
        String answer = "package " + this.getPackageName() + "." + suffix.toLowerCase() + ";\r\n";
        answer = answer + "\r\n";

        answer = answer + "import eu.itreegroup.s2.client.util.S2Message;\r\n" + //
                "import eu.itreegroup.spark.dao.common.SprBaseDAO;\r\n" + //
                "import eu.itreegroup.spark.dao.common.SprBaseDBServiceImpl;\r\n" + //
                "import eu.itreegroup.spark.dao.common.Utils;\r\n" + //
                "import eu.itreegroup.spark.dao.common.statementparams.UpdateStatementPart;\r\n" + //
                "import lt.jmsys.spark.bind.executor.plsql.errors.SparkBusinessException;\r\n" + //
                "import lt.jmsys.spark.bind.executor.plsql.errors.SparkMessageType;\r\n" + //
                "import com.fasterxml.jackson.annotation.JsonIgnore;\r\n" + //
                "\r\n";

        boolean dateExists = false;
        for (int i = 0; i < table.getColumns().size(); i++) {
            TableColumn column = table.getColumns().get(i);
            if ("Date".equals(column.getJavaType())) {
                dateExists = true;
            }
        }
        if (dateExists) {
            answer = answer + "import java.util.Date;\r\n";
        }
        if (table.isAuditColumnExists()) {
            answer = answer + "import java.sql.Timestamp;\r\n";
        }

        answer = answer + "\r\n";
        answer = answer + "public class " + this.getClassName() + suffix + " extends SprBaseDAO{\r\n";
        answer = answer + getClassProperties();
        answer = answer + getClassConstructors4Gen(suffix) + "\r\n";
        answer = answer + getMethodInitial() + "\r\n";
        for (int i = 0; i < table.getColumns().size(); i++) {
            TableColumn column = table.getColumns().get(i);
            answer = answer + column.getSettersGetter() + "\r\n";
        }
        answer = answer + getCommonMethods(suffix) + "\r\n";
        answer = answer + getUpdateStatementConstructor() + "\r\n";
        answer = answer + generateToString() + "\r\n";
        answer = answer + "}";
        return answer;
    }

    @Override
    public String generateClasses(String suffix) {
        String answer = "package " + this.getPackageName() + ";\r\n";
        answer = answer + "\r\n";

        answer = answer + "import " + this.getPackageName() + "." + suffix.toLowerCase() + "." + this.getClassName() + suffix + ";\r\n";
        boolean dateExists = false;
        for (int i = 0; i < table.getColumns().size(); i++) {
            TableColumn column = table.getColumns().get(i);
            if ("Date".equals(column.getJavaType())) {
                dateExists = true;
            }
        }
        if (dateExists) {
            answer = answer + "import java.util.Date;\r\n";
        }
        answer = answer + "\r\n";
        answer = answer + "public class " + this.getClassName() + " extends " + this.getClassName() + suffix + " {\r\n";
        answer = answer + getClassConstructors() + "\r\n";
        answer = answer + "}";
        return answer;

    }

    public String getMethodInitial() {
        String answer = "   protected void markObjectAsInitial() {\r\n";
        for (int i = 0; i < table.getColumns().size(); i++) {
            TableColumn column = table.getColumns().get(i);
            answer = answer + "      this." + column.getJavaName() + "_changed = false;\r\n";
        }
        answer = answer + "      this.record_changed = false;\r\n";
        answer = answer + "   }";
        return answer;
    }

    public String getClassProperties() {
        String answer = "";
        TableColumn identifierColumn = null;
        for (int i = 0; i < table.getColumns().size(); i++) {
            TableColumn column = table.getColumns().get(i);
            if (column.isIdentifier()) {
                identifierColumn = column;
            }
            answer = answer + "   private " + column.getJavaType() + " " + column.getJavaName() + ";\r\n";
        }
        if (identifierColumn != null) {
            answer = "   public static String IDENTIFIER=\"" + table.getTableName().toUpperCase() + "." + identifierColumn.getDbName().toUpperCase() + "\";\r\n"
                    + answer + "\r\n";

        } else {
            answer = "   public static String IDENTIFIER=\"" + table.getTableName().toUpperCase() + "\";\r\n" + answer + "\r\n";

        }
        for (int i = 0; i < table.getColumns().size(); i++) {
            TableColumn column = table.getColumns().get(i);
            answer = answer + "   protected boolean " + column.getJavaName() + "_changed;\r\n";
        }
        answer = answer + "   protected boolean record_changed;\r\n";
        answer = answer + "   protected boolean recordLoaded;\r\n";
        answer = answer + "   protected boolean forceUpdate;\r\n";
        return answer;
    }

    public String getClassConstructors4Gen(String suffix) {
        String answer = "   public " + this.getClassName() + suffix + "() {\r\n" + //
                "      markObjectAsInitial();\r\n" + //
                "      setRecordLoaded(false);\r\n" + //
                "      setForceUpdate(false);\r\n" + //
                "   }\r\n" + //
                "   public " + this.getClassName() + suffix + "(";
        for (int i = 0; i < table.getColumns().size(); i++) {
            TableColumn column = table.getColumns().get(i);
            if (i != 0) {
                answer = answer + ", ";
            }
            answer = answer + column.getJavaType() + " " + column.getJavaName();
        }
        answer = answer + ") {\r\n";
        for (int i = 0; i < table.getColumns().size(); i++) {
            TableColumn column = table.getColumns().get(i);
            answer = answer + "      this." + column.getJavaName() + " = " + column.getJavaName() + ";\r\n";
        }
        answer = answer + "   }\r\n";
        answer = answer + "   public void copyValues(" + this.getClassName() + suffix + " obj) {\r\n";
        for (int i = 0; i < table.getColumns().size(); i++) {
            TableColumn column = table.getColumns().get(i);
            if (!column.isAuditColumn()) {
                answer = answer + "      this.set" + column.getJavaNameInitCap() + "(obj.get" + column.getJavaNameInitCap() + "());\r\n";
            }
        }
        answer = answer + "   }";

        return answer;
    }

    public String getCommonMethods(String suffix) {
        String answer = "    @JsonIgnore\r\n" + //
                "    public boolean isRecordChanged() {\r\n" + //
                "        return record_changed;\r\n" + //
                "     }\r\n";
        TableColumn identifierColumn = null;
        for (int i = 0; i < table.getColumns().size(); i++) {
            TableColumn column = table.getColumns().get(i);
            if (column.isIdentifier()) {
                identifierColumn = column;
            }
        }
        if (identifierColumn != null) {
            answer = answer + "    @JsonIgnore\r\n" + //
                    "    public void setRecordIdentifier(Object recIdentifier) {\r\n" + //
                    "        if (recIdentifier != null && !EMPTY_STRING.equals(recIdentifier)  && !\"null\".equalsIgnoreCase(recIdentifier.toString())){\r\n" + //
                    "            this." + identifierColumn.getJavaName() + " = (" + identifierColumn.getJavaType() + ")recIdentifier;\r\n" + //
                    "        }\r\n" + //
                    "    }\r\n" + //
                    "    @JsonIgnore\r\n" + //
                    "    public Object getRecordIdentifier() {\r\n" + //
                    "        return this." + identifierColumn.getJavaName() + ";\r\n" + //
                    "    }\r\n" + //
                    "    @JsonIgnore\r\n" + //
                    "    public void setRecordLoaded(boolean recordLoaded){\r\n" + //
                    "        this.recordLoaded = recordLoaded;\r\n" + //
                    "    }\r\n" + //
                    "    @JsonIgnore\r\n" + //
                    "    public boolean isRecordLoaded(){\r\n" + //
                    "        return this.recordLoaded;\r\n" + //
                    "    }\r\n" + //
                    "    @JsonIgnore\r\n" + //
                    "    public void setForceUpdate(boolean forceUpdate){\r\n" + //
                    "        this.forceUpdate = forceUpdate;\r\n" + //
                    "    }\r\n" + //
                    "    @JsonIgnore\r\n" + //
                    "    public boolean isForceUpdate(){\r\n" + //
                    "        return this.forceUpdate;\r\n" + //
                    "    }\r\n";
        }
        answer = answer + "    public void validateObject(int operation, SprBaseDBServiceImpl<?> baseService) throws SparkBusinessException{\r\n";
        for (int i = 0; i < table.getColumns().size(); i++) {
            TableColumn column = table.getColumns().get(i);
            String fragment_null = "";
            String fragment_length = "";
            if (!column.isIdentifier() || column.isUpdatable()) {
                if ("String".equalsIgnoreCase(column.getJavaType())) {
                    fragment_length = column.getJavaName() + "!= null && " + column.getJavaName() + ".length()>" + column.getDbPrecision();
                    fragment_null = column.getJavaName() + "== null || EMPTY_STRING.equals(" + column.getJavaName() + ")";
                } else {
                    if ("Double".equalsIgnoreCase(column.getJavaType())) {
                        fragment_length = column.getJavaName() + "!= null && ";
                        if (column.getDbScale() != 0) {
                            fragment_length = fragment_length + column.getJavaName() + ".toString().length()>" + column.getDbPrecision();
                        } else {
                            fragment_length = fragment_length + "(\"\"+" + column.getJavaName() + ".intValue()).length()>" + column.getDbPrecision();
                        }
                        fragment_null = column.getJavaName() + "== null";
                    } else {
                        if ("Integer".equalsIgnoreCase(column.getJavaType())) {
                            fragment_length = column.getJavaName() + "!= null && (\"\"+" + column.getJavaName() + ".intValue()).length()>"
                                    + column.getDbPrecision();
                            fragment_null = column.getJavaName() + "== null";
                        } else {
                            if ("Date".equalsIgnoreCase(column.getJavaType())) {
                                fragment_length = null;
                                fragment_null = column.getJavaName() + "== null";
                            } else {
                                if ("Timestamp".equalsIgnoreCase(column.getJavaType())) {
                                    fragment_length = null;
                                    fragment_null = column.getJavaName() + "== null";
                                }
                            }
                        }
                    }
                }
                if (!column.isDbIsNullable()) {
                    answer = answer + "      if (" + column.getJavaName() + "_changed || operation == Utils.OPERATION_INSERT) {\r\n" + //
                            "         if (" + fragment_null + ") {\r\n" + //
                            "            throw new SparkBusinessException(new S2Message(\"" + table.getTableName() + "\", \"" + column.getDbName().toUpperCase()
                            + "\", DB_ERROR_MANDATORY_PATH, SparkMessageType.ERROR));\r\n" + //
                            "         }";
                    if (column.getDbPrecision() != 0 && fragment_length != null) {
                        answer = answer + "else{\r\n" + //
                                "            if (" + fragment_length + ") {\r\n" + //
                                "               throw new SparkBusinessException(new S2Message(\"" + table.getTableName() + "\", \""
                                + column.getDbName().toUpperCase() + "\", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,\"" + column.getDbPrecision()
                                + "\"));\r\n" + //
                                "            }\r\n" + //
                                "         }\r\n" + //
                                "      }\r\n";
                    } else {
                        answer = answer + "\r\n" + //
                                "      }\r\n";
                    }
                } else {
                    if (column.getDbPrecision() != 0 && fragment_length != null) {
                        answer = answer + "      if (" + column.getJavaName() + "_changed || operation == Utils.OPERATION_INSERT) {\r\n" + //
                                "         if (" + fragment_length + ") {\r\n" + //
                                "            throw new SparkBusinessException(new S2Message(\"" + table.getTableName() + "\", \""
                                + column.getDbName().toUpperCase() + "\", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,\"" + column.getDbPrecision()
                                + "\"));\r\n" + //
                                "         }\r\n" + //
                                "      }\r\n";
                    }

                }
            }
        }
        answer = answer + "   }\r\n";
        return answer;
    }

    /*
    * 
    */
    public String getClassConstructors() {
        String answer = "   public " + this.getClassName() + "() {\r\n" + //
                "      super();\r\n" + //
                "   }\r\n" + //
                "   public " + this.getClassName() + "(";
        for (int i = 0; i < table.getColumns().size(); i++) {
            TableColumn column = table.getColumns().get(i);
            if (i != 0) {
                answer = answer + ", ";
            }
            answer = answer + column.getJavaType() + " " + column.getJavaName();
        }
        answer = answer + ") {\r\n";
        answer = answer + "      super(";
        for (int i = 0; i < table.getColumns().size(); i++) {
            TableColumn column = table.getColumns().get(i);
            if (i != 0) {
                answer = answer + ", ";
            }
            answer = answer + column.getJavaName();
        }
        answer = answer + ");\r\n";
        answer = answer + "   }";
        return answer;
    }

    public String getUpdateStatementConstructor() {
        String answer = "   public UpdateStatementPart constructUpdatePart(String userName) {\r\n" + //
                "      UpdateStatementPart updateStatementPart = new UpdateStatementPart();\r\n" + //
                "      String answer = \"UPDATE " + table.getTableName() + " SET \";\r\n" + //
                "      boolean changedExists = false;";
        TableColumn identifier = null;
        for (int i = 0; i < table.getColumns().size(); i++) {
            TableColumn column = table.getColumns().get(i);
            if (column.isIdentifier()) {
                identifier = column;
            } else {
                if (!table.isAuditColumnExists() || !column.isAuditColumn()) {
                    answer = answer + "      if (" + column.getJavaName() + "_changed) {\r\n" + //
                            "         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+\"" + column.getDbName().toUpperCase() + " = ? \";\r\n" + //
                            "         changedExists = true;\r\n";
                    if ("TIMESTAMP".equalsIgnoreCase(column.getDbType())) {
                        answer = answer + "         updateStatementPart.addStatementTimestampParam(" + column.getJavaName() + ");\r\n" + //
                                "      }\r\n";
                    } else {
                        answer = answer + "         updateStatementPart.addStatementParam(" + column.getJavaName() + ");\r\n" + //
                                "      }\r\n";
                    }
                }
            }
        }
        if (table.isAuditColumnExists()) {
            answer = answer + "      answer = answer +  (changedExists? COMMA : EMPTY_STRING)+\"rec_version = rec_version+1 \";\r\n" + //
                    "      changedExists = true;\r\n" + //
                    "      answer = answer +  (changedExists? COMMA : EMPTY_STRING)+\"rec_timestamp = ? \";\r\n" + //
                    "      changedExists = true;\r\n" + //
                    "      updateStatementPart.addStatementParam(new Timestamp(System.currentTimeMillis()));\r\n" + //
                    "      changedExists = true;\r\n" + //
                    "      answer = answer +  (changedExists?  COMMA : EMPTY_STRING)+\"rec_userid = ? \";\r\n" + //
                    "      updateStatementPart.addStatementParam(userName);\r\n";
        }
        answer = answer + "      answer = answer +\" WHERE  " + identifier.getDbName().toUpperCase() + " = ? \";\r\n" + //
                "      updateStatementPart.addStatementParam(" + identifier.getJavaName() + ");\r\n" + //
                "      updateStatementPart.setStatementPart(answer);\r\n" + //
                "      return updateStatementPart;\r\n" + //
                "   }\r\r";
        return answer;
    }

    public String generateToString() {
        String answer = "   public String toString() {\r\n" + //
                "      return \"{\\\"" + this.getClassName() + "\\\":{";
        for (int i = 0; i < table.getColumns().size(); i++) {
            TableColumn column = table.getColumns().get(i);
            if (i > 0) {
                answer = answer + ", ";
            }
            answer = answer + "\\\"" + column.getJavaName() + "\\\": \\\"\"+get" + column.getJavaNameInitCap() + "()+\"\\\"";
        }
        answer = answer + "}}\";\r\n" + //
                "   }\r\n";
        return answer;
    }

    @Override
    public String generateInterface() {
        String answer = "package eu.itreegroup.spark.modules.admin.service;\r\n";
        answer = answer + "\r\n";
        answer = answer + "import eu.itreegroup.spark.dao.common.SprBaseDBService;\r\n";
        answer = answer + "import " + this.getPackageName() + '.' + this.getClassName() + ";\r\n";
        answer = answer + "\r\n";
        answer = answer + "public interface " + this.getInterfaceName() + " extends SprBaseDBService<" + this.getClassName() + "> {\r\n";
        answer = answer + "\r\n}";
        return answer;
    }

}
