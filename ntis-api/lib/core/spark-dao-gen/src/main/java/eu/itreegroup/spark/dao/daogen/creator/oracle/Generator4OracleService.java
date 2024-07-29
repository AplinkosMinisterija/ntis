package eu.itreegroup.spark.dao.daogen.creator.oracle;

import eu.itreegroup.spark.dao.daogen.common.Utils;
import eu.itreegroup.spark.dao.daogen.creator.GeneratorService;
import eu.itreegroup.spark.dao.daogen.struct.Table;
import eu.itreegroup.spark.dao.daogen.struct.TableColumn;
import eu.itreegroup.spark.dao.daogen.struct.TableIndex;

public class Generator4OracleService extends GeneratorService {

    public Generator4OracleService(Table table) {
        super(table);
    }

    @Override
    public String generateGenClasses(String suffix) {
        String answer = "package " + this.getPackageName() + "." + suffix.toLowerCase() + ";\r\n";
        answer = answer + "\r\n";

        answer = answer + "import java.sql.Connection;\r\n" + //
                "import java.sql.CallableStatement;\r\n" + //
                "import java.sql.PreparedStatement;\r\n" + //
                "import java.sql.ResultSet;\r\n" + //
                "import java.util.ArrayList;\r\n" + //
                "import java.util.List;\r\n" + //
                "import " + generatorDAO.getPackageName() + "." + generatorDAO.getClassName() + ";\r\n" + //
                "import eu.itreegroup.spark.dao.common.statementparams.StatementParam;\r\n" + //
                "import eu.itreegroup.spark.dao.common.statementparams.UpdateStatementPart;\r\n" + //
                "import eu.itreegroup.spark.dao.common.SprBaseDBServiceImpl;\r\n" + //
                "import eu.itreegroup.s2.client.util.S2ViolatedConstraint;\r\n" + //
                "import org.slf4j.Logger;\r\n" + //
                "import org.slf4j.LoggerFactory;\r\n" + //
                "import lt.jmsys.spark.bind.executor.plsql.errors.SparkBusinessException;\r\n" + //
                "import lt.jmsys.spark.bind.executor.plsql.errors.SparkMessageType;\r\n" + //
                "import eu.itreegroup.s2.client.util.S2Message;\r\n" + //
                "import eu.itreegroup.spark.dao.common.SprBaseDAO;\r\n";
        boolean dateExists = false;
        boolean doubleExists = false;
        for (int i = 0; i < table.getColumns().size(); i++) {
            TableColumn column = table.getColumns().get(i);
            if ("Date".equals(column.getJavaType())) {
                dateExists = true;
            }
            if ("Double".equalsIgnoreCase(column.getJavaType())) {
                doubleExists = true;
            }
        }
        if (dateExists || doubleExists) {
            answer = answer + "import eu.itreegroup.spark.dao.common.Utils;\r\n";
        }
        if (table.isAuditColumnExists()) {
            answer = answer + "import java.sql.Timestamp;\r\n";
        }
        if (table.getUkIndexes().size() > 0) {
            answer = answer + "import java.util.Arrays;\r\n";
        }
        answer = answer + "\r\n";
        answer = answer + "public class " + this.getClassName() + suffix + " extends SprBaseDBServiceImpl<" + generatorDAO.getClassName() + "> {\r\n";
        answer = answer + "  private static final Logger log = LoggerFactory.getLogger(" + this.getClassName() + suffix + ".class);\r\n";
        answer = answer + generateSelectStatement() + "\r\n";
        answer = answer + getClassConstructors4Gen(suffix) + "\r\n";
        answer = answer + generateSetDBDataToObject() + "\r\n";
        answer = answer + generateLoadMethod() + "\r\n";
        answer = answer + generateDeleteMethod() + "\r\n";
        answer = answer + generateConstraintValidateMethod() + "\r\n";
        answer = answer + generateInsertMethod() + "\r\n";
        answer = answer + generateUpdateMethod() + "\r\n";
        answer = answer + generateSaveMethod() + "\r\n";
        answer = answer + "}";
        return answer;
    }

    @Override
    public String generateInsertMethod() {
        String answer = "   public " + generatorDAO.getClassName() + " insertRecord(Connection conn, " + generatorDAO.getClassName()
                + " daoObject) throws Exception{\r\n" + //
                "      daoObject.validateObject(Utils.OPERATION_INSERT, this);\r\n" + //
                "      this.validateConstraints(conn, daoObject, null);\r\n" + //
                "      String stmt = \"BEGIN INSERT INTO " + generatorDAO.getTable().getTableName() + " (";
        String paramString = "";
        TableColumn identifierColumn = null;
        int colCount = 0;
        for (int i = 0; i < this.getGeneratorDAO().table.getColumns().size(); i++) {
            TableColumn column = this.getGeneratorDAO().table.getColumns().get(i);
            if (column.isIdentifier()) {
                identifierColumn = column;
            }
            if (!table.isAuditColumnExists() || !column.isAuditColumn()) {
                if (!column.isIdentifier() || column.isUpdatable() || (column.getSequenceName() != null && !"".equals(column.getSequenceName()))) {
                    if (colCount != 0) {
                        answer = answer + ", ";
                        paramString = paramString + ", ";
                    }
                    colCount++;
                    answer = answer + column.getDbName().toUpperCase();
                    if (column.getSequenceName() != null && !"".equals(column.getSequenceName())) {
                        paramString = paramString + column.getSequenceName() + ".nextval";
                    } else {
                        paramString = paramString + "?";
                    }
                }
            }
        }
        if (table.isAuditColumnExists()) {
            answer = answer + ", rec_version, rec_create_timestamp, rec_timestamp, rec_userid";
            paramString = paramString + ", ?, ?, ?, ?";
        }
        answer = answer + ") values (" + paramString + ") RETURNING " + identifierColumn.getDbName().toUpperCase() + " INTO ?; END;\";\r\n";
        answer = answer + "      log.debug(\"insert stmt: \"+stmt);\r\n";
        answer = answer + "      try(CallableStatement cstmt = conn.prepareCall(stmt)){\r\n";
        int colNum = 1;
        for (int i = 0; i < this.getGeneratorDAO().table.getColumns().size(); i++) {
            TableColumn column = this.getGeneratorDAO().table.getColumns().get(i);
            if (!table.isAuditColumnExists() || !column.isAuditColumn()) {
                if (!column.isIdentifier() || column.isUpdatable()) {
                    answer = answer + "         cstmt."
                            + (("String".equalsIgnoreCase(column.getJavaType()))
                                    ? "setString(" + colNum + ", daoObject.get" + column.getJavaNameInitCap() + "());\r\n"
                                    : ("Date".equalsIgnoreCase(column.getJavaType()))
                                            ? "setObject(" + colNum + ",  Utils."
                                                    + ("TIMESTAMP".equalsIgnoreCase(column.getDbType()) ? "getSqlTimestamp" : "getSqlDate") + "(daoObject.get"
                                                    + column.getJavaNameInitCap() + "()));\r\n"
                                            : ("Double".equalsIgnoreCase(column.getJavaType()))
                                                    ? "setObject(" + colNum + ", daoObject.get" + column.getJavaNameInitCap() + "());\r\n"
                                                    : ("Integer".equalsIgnoreCase(column.getJavaType()))
                                                            ? "setObject(" + colNum + ", daoObject.get" + column.getJavaNameInitCap() + "());\r\n"
                                                            : "setString(" + colNum + ",daoObject.get" + column.getJavaNameInitCap() + "());\r\n");
                    colNum++;
                }
            }
        }
        if (table.isAuditColumnExists()) {
            answer = answer + "         //Record audit information (start)\r\n" + //
                    "         cstmt.setInt(" + (colNum++) + ", 1);\r\n" + //
                    "         cstmt.setObject(" + (colNum++) + ",  new Timestamp(System.currentTimeMillis()));\r\n" + //
                    "         cstmt.setObject(" + (colNum++) + ",  new Timestamp(System.currentTimeMillis()));\r\n" + //
                    "         String userName = getUserName();\r\n" + //
                    "         cstmt.setObject(" + (colNum++) + ", userName);\r\n" + //
                    "         //Record audit information (end)\r\n";
        }
        String sqlType = Utils.getMaping4insertstatement(identifierColumn);
        answer = answer + "         cstmt.registerOutParameter(" + colNum + ", java.sql.Types." + sqlType + ");\r\n" + //
                "         cstmt.execute();\r\n" + //
                "         daoObject.set" + identifierColumn.getJavaNameInitCap() + "(cstmt."
                + (("String".equalsIgnoreCase(identifierColumn.getJavaType())) ? "getString(" + colNum + "));\r\n"
                        : ("Date".equalsIgnoreCase(identifierColumn.getJavaType())) ? "getDate(" + colNum + "));\r\n"
                                : ("Double".equalsIgnoreCase(identifierColumn.getJavaType())) ? "getDouble(" + colNum + "));\r\n"
                                        : ("Integer".equalsIgnoreCase(identifierColumn.getJavaType())) ? "getIn(" + colNum + "));\r\n"
                                                : "getString(" + colNum + "));\r\n")
                + //
                "      }catch(Exception ex){\r\n" + //
                "         throw ex;\r\n" + //
                "      }\r\n" + //
                "      return daoObject;\r\n" + //
                "   }\r\n";
        return answer;
    }

    @Override
    public String generateDeleteMethod() {
        TableColumn identifierColumn = null;
        for (int i = 0; i < this.getGeneratorDAO().table.getColumns().size(); i++) {
            TableColumn column = this.getGeneratorDAO().table.getColumns().get(i);
            if (column.isIdentifier()) {
                identifierColumn = column;
            }
        }
        String answer = "   @Override\r\n" + //
                "   public void deleteRecord(Connection conn, " + identifierColumn.getJavaType() + " identifier) throws Exception{\r\n" + //
                "      manageForeignKeysOnDelete(conn, " + generatorDAO.getClassName() + ".IDENTIFIER, identifier);\r\n" + //
                "      String stmt = \"DELETE FROM " + generatorDAO.getTable().getTableName() + " WHERE " + identifierColumn.getDbName().toUpperCase()
                + "=?\";\r\n" + //
                "      log.debug(\"delete stmt: \"+stmt);\r\n" + //
                "      try(PreparedStatement pstmt = conn.prepareStatement(stmt)){\r\n" + //
                "         pstmt."
                + (("String".equalsIgnoreCase(identifierColumn.getJavaType())) ? "setString(1,identifier);\r\n"
                        : ("Date".equalsIgnoreCase(identifierColumn.getJavaType()))
                                ? "setObject(1, Utils." + ("TIMESTAMP".equalsIgnoreCase(identifierColumn.getDbType()) ? "getSqlTimestamp" : "getSqlDate")
                                        + "(identifier));\r\n"
                                : ("Double".equalsIgnoreCase(identifierColumn.getJavaType())) ? "setObject(1, identifier);\r\n"
                                        : ("Integer".equalsIgnoreCase(identifierColumn.getJavaType())) ? "setObject(1, identifier);\r\n"
                                                : "setObject(1, identifier);\r\n");
        answer = answer + "         pstmt.execute();\r\n" + //
                "      }catch(Exception ex){\r\n" + //
                "          throw ex;\r\n" + //
                "      }\r\n" + //
                "   }\r\n" + //
                "   public void deleteRecord(Connection conn, " + generatorDAO.getClassName() + " daoObject) throws Exception{\r\n" + //
                "      deleteRecord(conn, daoObject.get" + identifierColumn.getJavaNameInitCap() + "());\r\n" + //
                "   }\r\n";
        return answer;
    }

    @Override
    public String generateLoadMethod() {
        String answer = "   @Override\r\n" + //
                "   public " + generatorDAO.getClassName() + " loadRecord(Connection conn, Object identifier) throws Exception{\r\n";
        TableColumn identifierColumn = null;
        for (int i = 0; i < this.getGeneratorDAO().table.getColumns().size(); i++) {
            TableColumn column = this.getGeneratorDAO().table.getColumns().get(i);
            if (column.isIdentifier()) {
                identifierColumn = column;
            }
        }
        answer = answer + "        String stmt = getTableRecordStatement() + \" WHERE " + identifierColumn.getDbName().toUpperCase() + " = ?\";\r\n" + //
                "      log.debug(\"load stmt: \"+stmt);\r\n" + //
                "      List<" + generatorDAO.getClassName() + "> data = null;\r\n" + //
                "      try(PreparedStatement pstmt = conn.prepareStatement(stmt)){\r\n";
        answer = answer + "         pstmt."
                + (("String".equalsIgnoreCase(identifierColumn.getJavaType())) ? "setString(1,(String)identifier);\r\n"
                        : ("Date".equalsIgnoreCase(identifierColumn.getJavaType()))
                                ? "setObject(1,Utils." + ("TIMESTAMP".equalsIgnoreCase(identifierColumn.getDbType()) ? "getSqlTimestamp" : "getSqlDate")
                                        + "(identifier));\r\n"
                                : ("Double".equalsIgnoreCase(identifierColumn.getJavaType())) ? "setObject(1,(Double)identifier);\r\n"
                                        : ("Integer".equalsIgnoreCase(identifierColumn.getJavaType())) ? "setObject(1,(Integer)identifier);\r\n"
                                                : "setString(1,(String)identifier);\r\n");
        answer = answer + "         try(ResultSet rs = pstmt.executeQuery()){\r\n" + //
                "               data = setDBDataToObject(rs);\r\n" + //

                "         }catch(Exception ex1){\r\n" + //
                "            throw ex1;\r\n" + //
                "         }\r\n" + //
                "      }catch(Exception ex){\r\n" + //
                "         throw ex;\r\n" + //
                "      }\r\n" + //
                "      if (data == null || data.isEmpty()) {\r\n" + //
                "         throw new Exception(\"Object with identifier \"+ identifier + \" not found\");\r\n" + //
                "      }\r\n" + //
                "      " + generatorDAO.getClassName() + " daoObject = data.get(0);\r\n" + //
                "      daoObject.setRecordLoaded(true);\r\n" + //
                "      return daoObject;\r\n" + //
                "   \r\n" + //
                "   }";
        return answer;
    }

    @Override
    public String generateConstraintValidateMethod() {
        TableColumn identifierColumn = null;
        for (int i = 0; i < this.getGeneratorDAO().table.getColumns().size(); i++) {
            TableColumn column = this.getGeneratorDAO().table.getColumns().get(i);
            if (column.isIdentifier()) {
                identifierColumn = column;
            }
        }
        String answer = "   @Override\r\n" + //
                "   public void validateConstraints(Connection conn, " + generatorDAO.getClassName()
                + " daoObject, String constraintName) throws Exception {\r\n" //
                + "       List<S2ViolatedConstraint> violatedConstraints = getViolatedConstraints(conn, daoObject, constraintName);\r\n" //
                + "       if (violatedConstraints.size()>0){\r\n" //
                + "           List<S2Message> messages = new ArrayList<S2Message>();\r\n" //
                + "           for (int i=0; i<violatedConstraints.size(); i++){\r\n" //
                + "               String[] columns = violatedConstraints.get(i).getFields().toArray(new String[violatedConstraints.get(i).getFields().size()]);\r\n" //
                + "               messages.add(new S2Message(\"" + generatorDAO.getTable().getTableName()
                + "\",  violatedConstraints.get(i).getName(), SprBaseDAO.DB_ERROR_UK_CONSTRAINT, SparkMessageType.ERROR, columns));\r\n" //
                + "           }\r\n" //
                + "           throw new SparkBusinessException(messages.toArray(new S2Message[messages.size()]));\r\n" //
                + "       }\r\n" //
                + "   }\r\n";

        answer = answer + "    @Override\r\n" + //
                "    public List<S2ViolatedConstraint> getViolatedConstraints(Connection conn, " + generatorDAO.getClassName()
                + " daoObject, String constraintName) throws Exception {\r\n" //
                + "        List<S2ViolatedConstraint> answer = new ArrayList<S2ViolatedConstraint>();\r\n";
        for (int i = 0; i < generatorDAO.getTable().getUkIndexes().size(); i++) {
            TableIndex index = generatorDAO.getTable().getUkIndexes().get(i);
            answer = answer + "        if (constraintName == null || \"" + index.getName().toUpperCase() + "\".equalsIgnoreCase(constraintName)) {\r\n" + //
                    "            if (";
            for (int j = 0; j < index.getColumns().size(); j++) {
                answer = answer + "daoObject.get" + index.getColumns().get(j).getJavaNameInitCap() + "() != null";
                if (j + 1 < index.getColumns().size()) {
                    answer = answer + " || //\r\n                    ";
                } else {
                    answer = answer + ") {\r\n";
                }
            }
            answer = answer + "                String[] constraintColumns = { ";
            for (int j = 0; j < index.getColumns().size(); j++) {
                answer = answer + "\"" + index.getColumns().get(j).getDbName().toLowerCase() + "\"";
                if (j + 1 < index.getColumns().size()) {
                    answer = answer + ", ";
                } else {
                    answer = answer + " };\r\n";
                }
            }
            answer = answer + "                String stmt = \"select 1 from " + generatorDAO.getTable().getTableName() + " where \" //\r\n";
            for (int j = 0; j < index.getColumns().size(); j++) {
                answer = answer + "                        + constructStatementPart(\"" + index.getColumns().get(j).getDbName() + "\", daoObject.get"
                        + index.getColumns().get(j).getJavaNameInitCap() + "()) + \" and \" //\r\n";
            }
            answer = answer + "                        + (daoObject.get" + identifierColumn.getJavaNameInitCap() + "() != null ? \""
                    + identifierColumn.getDbName().toUpperCase() + " != ?\" : \" 1=1\");\r\n" + //
                    "                try (PreparedStatement pstmt = conn.prepareStatement(stmt)) {\r\n" //
                    + "                    int idx = 0;\r\n";
            for (int j = 0; j < index.getColumns().size(); j++) {
                TableColumn column = index.getColumns().get(j);
                answer = answer + "                    idx = setValueToStatement(pstmt, idx, daoObject.get" + column.getJavaNameInitCap() + "());\r\n";
            }

            answer = answer + "                    if (daoObject.get" + identifierColumn.getJavaNameInitCap() + "() != null) {\r\n" //
                    + "                        pstmt."
                    + (("String".equalsIgnoreCase(identifierColumn.getJavaType()))
                            ? "setString(++idx, daoObject.get" + identifierColumn.getJavaNameInitCap() + "());\r\n"
                            : ("Date".equalsIgnoreCase(identifierColumn.getJavaType()))
                                    ? "setObject(++idx, daoObject.get" + identifierColumn.getJavaNameInitCap() + "());\r\n"
                                    : ("Double".equalsIgnoreCase(identifierColumn.getJavaType()))
                                            ? "setObject(++idx, daoObject.get" + identifierColumn.getJavaNameInitCap() + "());\r\n"
                                            : ("Integer".equalsIgnoreCase(identifierColumn.getJavaType()))
                                                    ? "setObject(++idx, daoObject.get" + identifierColumn.getJavaNameInitCap() + "());\r\n"
                                                    : "setObject(++idx, identifier);\r\n")
                    + "                    }\r\n";

            answer = answer + "                    try (ResultSet rs = pstmt.executeQuery()) {\r\n " + //
                    "                       if (rs.next()) {\r\n" + //
                    "                            answer.add(new S2ViolatedConstraint(\"" + index.getName().toUpperCase()
                    + "\", new ArrayList<String>(Arrays.asList(constraintColumns))));\r\n" + //
                    "                        }\r\n" + //
                    "                    }\r\n" + //
                    "                }\r\n" + //
                    "            }\r\n" + //
                    "        }\r\n";

        }
        answer = answer + "        return answer;\r\n" //
                + "    }\r\n";
        return answer;
    }
}
