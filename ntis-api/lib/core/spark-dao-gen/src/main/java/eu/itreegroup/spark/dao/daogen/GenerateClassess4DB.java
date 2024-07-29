package eu.itreegroup.spark.dao.daogen;

import java.io.File;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.HashMap;

import eu.itreegroup.spark.dao.daogen.creator.Generator;
import eu.itreegroup.spark.dao.daogen.creator.GeneratorDAO;
import eu.itreegroup.spark.dao.daogen.creator.GeneratorService;
import eu.itreegroup.spark.dao.daogen.creator.oracle.Generator4OracleDAO;
import eu.itreegroup.spark.dao.daogen.creator.oracle.Generator4OracleService;
import eu.itreegroup.spark.dao.daogen.struct.Table;

public class GenerateClassess4DB {

    public static final String DB_TYPE_ORACLE = "ORACLE";

    public static final String DB_TYPE_POSTGRE = "POSTGRE";

    public static final String DB_TYPE_MSSQL = "MSSQL";

    public static final int GENERATE_ALL = 1;

    public static final int GENERATE_DAO = 2;

    public static final int GENERATE_SERVICE = 3;

    // Gen config
    public static final String GEN_PATH = System.getProperty("user.dir") + "\\src\\main\\java";

    public static final String GEN_DB_TYPE = DB_TYPE_ORACLE;

    public static final String GEN_DB_ADDRESS = "";

    public static final String GEN_DB_USERNAME = "";

    public static final String GEN_DB_PASSWORD = "";

    // <end> config

    String homePath;

    String jdbcDriver;

    String dbConnection;

    String dbUser;

    String dbPassword;

    String dbType;

    int generationMode;

    public GenerateClassess4DB(String homePath, String dbType, String dbConnection, String dbUser, String dbPassword) {
        this(homePath, dbType, dbConnection, dbUser, dbPassword, GENERATE_ALL);
    }

    public GenerateClassess4DB(String homePath, String dbType, String dbConnection, String dbUser, String dbPassword, int generationMode) {
        this.homePath = homePath;
        setDbType(dbType);
        try {
            if (DB_TYPE_ORACLE.equalsIgnoreCase(getDbType())) {
                Class.forName("oracle.jdbc.driver.OracleDriver");
            } else if (DB_TYPE_POSTGRE.equalsIgnoreCase(getDbType())) {
                Class.forName("org.postgresql.Driver");
            } else if (DB_TYPE_MSSQL.equalsIgnoreCase(getDbType())) {
                Class.forName("oracle.jdbc.driver.OracleDriver");
            }
            setDbConnection(dbConnection);
            setDbUser(dbUser);
            setDbPassword(dbPassword);
            setGenerationMode(generationMode);
        } catch (Exception ex) {
            System.err.println("Failed to initiate DB connection");
            ex.printStackTrace();
        }
    }

    public String getHomePath() {
        return homePath;
    }

    public void setHomePath(String homePath) {
        this.homePath = homePath;
    }

    public String getJdbcDriver() {
        return jdbcDriver;
    }

    public void setJdbcDriver(String jdbcDriver) {
        this.jdbcDriver = jdbcDriver;
    }

    public String getDbConnection() {
        return dbConnection;
    }

    public void setDbConnection(String dbConnection) {
        this.dbConnection = dbConnection;
    }

    public String getDbUser() {
        return dbUser;
    }

    public void setDbUser(String dbUser) {
        this.dbUser = dbUser;
    }

    public String getDbPassword() {
        return dbPassword;
    }

    public void setDbPassword(String dbPassword) {
        this.dbPassword = dbPassword;
    }

    public String getDbType() {
        return dbType;
    }

    public void setDbType(String dbType) {
        this.dbType = dbType;
    }

    public int getGenerationMode() {
        return generationMode;
    }

    public void setGenerationMode(int generationMode) {
        this.generationMode = generationMode;
    }

    public void generateFiles(Generator generator, String suffix) throws Exception {
        File file = new File(generator.getHomeDir() + File.separatorChar + generator.getPathForPackage() + File.separatorChar + "gen");
        file.mkdirs();
        File fileGen = new File(generator.getHomeDir() + File.separatorChar + generator.getPathForPackage() + File.separatorChar + "gen" + File.separatorChar
                + generator.getClassName(Generator.CLASS_LEVEL_GEN) + "Gen.java");
        if (fileGen.exists()) {
            fileGen.delete();
        }
        if (fileGen.createNewFile()) {
            System.out.println("File created: " + fileGen.getName());
        } else {
            System.out.println("File already exists.");
        }
        FileWriter writer = new FileWriter(fileGen);
        writer.write(generator.generateGenClasses(suffix));
        writer.close();

        File fileDAO = new File(generator.getHomeDir() + File.separatorChar + generator.getPathForPackage() + File.separatorChar
                + generator.getClassName(Generator.CLASS_TYPE_SERVICE) + ".java");
        if (fileDAO.createNewFile()) {
            System.out.println("File created: " + fileDAO.getAbsolutePath());
            writer = new FileWriter(fileDAO);
            writer.write(generator.generateClasses(suffix));
            writer.close();
        }
    }

    public void generateServiceInterfaceFiles(Generator generator, String suffix) throws Exception {
        File file = new File(generator.getHomeDir() + File.separatorChar + generator.getPathForPackage() + File.separatorChar + "gen");
        file.mkdirs();
        File fileServiceInterface = new File(generator.getHomeDir() + File.separatorChar + generator.getPathForPackage() + File.separatorChar
                + generator.getClassName(Generator.CLASS_TYPE_SERVICE_INTERFACE) + ".java");
        if (fileServiceInterface.createNewFile()) {
            System.out.println("File created: " + fileServiceInterface.getAbsolutePath());
            FileWriter writer = new FileWriter(fileServiceInterface);
            writer.write(generator.generateInterface());
            writer.close();
        }
    }

    public void generateStuff(String tableName, String identifierColumn, String sequenceName, String identifierUpdatable, String daoPackageName,
            String daoClassName, String servicePackageName, String serviceClassName, String suffix) {
        generateStuff(tableName, identifierColumn, sequenceName, identifierUpdatable, daoPackageName, daoClassName, servicePackageName, serviceClassName,
                suffix, null);
    }

    private HashMap<String, String> getTableIndexesInfo(Connection conn, String tableName) {
        HashMap<String, String> answer = new HashMap<String, String>();
        String stmt = null;
        if (DB_TYPE_ORACLE.equalsIgnoreCase(getDbType())) {
            stmt = "select constraint_name nm, constraint_type tp \r\n" //
                    + "from all_constraints \r\n" //
                    + "where upper(table_name)=upper(?) \r\n" //
                    + "  and index_name is not null";
        } else {
            stmt = "SELECT con.conname nm, upper(contype) tp\r\n" //
                    + "       FROM pg_catalog.pg_constraint con\r\n" //
                    + "            INNER JOIN pg_catalog.pg_class rel\r\n" //
                    + "                       ON rel.oid = con.conrelid\r\n" //
                    + "            INNER JOIN pg_catalog.pg_namespace nsp\r\n" //
                    + "                       ON nsp.oid = connamespace\r\n" //
                    + "       WHERE upper(rel.relname) =upper(?)";
        }
        try (PreparedStatement pstm = conn.prepareStatement(stmt)) {
            pstm.setString(1, tableName);
            try (ResultSet rez = pstm.executeQuery()) {
                while (rez.next()) {
                    answer.put(rez.getString("nm"), rez.getString("tp"));
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return answer;
    }

    /**
    This method generates table services and DAO  on the provided table name.
    @param tableName the name of the table to generate
    @param identifierColumn the name of the column used as an identifier
    @param sequenceName the name of the sequence to use for generating identifiers
    @param identifierUpdatable a boolean indicating whether the identifier column is updatable or not
    @param daoPackageName the package name to use for the generated DAO class
    @param servicePackageName the package name to use for the generated service class
    @param interfaceName the name of the interface to implement in the generated DAO class
    */
    public void generateStuffByTableName(String tableName, String identifierColumn, String sequenceName, String daoPackageName, String servicePackageName,
            boolean genInterface) {
        generateStuff(tableName, identifierColumn, sequenceName, "N", daoPackageName, tranforName(tableName) + "DAO", servicePackageName,
                tranforName(tableName) + "DBService", "Gen", genInterface ? tranforName(tableName) + "DBService" : null);
    }

    public void generateStuff(String tableName, String identifierColumn, String sequenceName, String identifierUpdatable, String daoPackageName,
            String daoClassName, String servicePackageName, String serviceClassName, String suffix, String interfaceName) {
        try {
            Connection conn = DriverManager.getConnection(this.getDbConnection(), this.getDbUser(), this.getDbPassword());
            System.out.println("Start DAO generation for table args[0]: " + tableName);
            System.out.println("Ideintification record is args[1]: " + identifierColumn);
            PreparedStatement ps = conn.prepareStatement("Select * from " + tableName);
            ResultSetMetaData metaData = ps.getMetaData();
            System.out.println("In table find collumns count: " + metaData.getColumnCount());
            Table table = new Table();
            table.setAuditColumns(",REC_VERSION,REC_CREATE_TIMESTAMP,REC_TIMESTAMP,REC_USERID,");
            table.setFlexFieldsList(",N01,N02,N03,N04,N05,C01,C02,C03,C04,C05,D01,D02,D03,D04,D05,");
            table.setTableName(tableName);
            if (metaData.getTableName(1) != null && !metaData.getTableName(1).isEmpty()) {
                table.setTableNameInDB(metaData.getTableName(1));
            } else {
                table.setTableNameInDB(tableName);
            }
            table.loadData(metaData, identifierColumn, sequenceName, identifierUpdatable);
            table.loadUKIndexes(conn.getMetaData(), getTableIndexesInfo(conn, tableName));
            System.out.println(table);
            System.out.println("isAuditColumnExists: " + (table.isAuditColumnExists() ? "true" : "false"));
            ps.close();

            GeneratorDAO generateDAO;
            if (DB_TYPE_ORACLE.equalsIgnoreCase(getDbType())) {
                generateDAO = new Generator4OracleDAO(table);
            } else {
                generateDAO = new GeneratorDAO(table);
            }
            generateDAO.setClassName(daoClassName);
            generateDAO.setPackageName(daoPackageName);
            generateDAO.setInterfaceName(interfaceName);
            generateDAO.setHomeDir(homePath);
            if (getGenerationMode() == 1 || getGenerationMode() == 2) {
                generateFiles(generateDAO, suffix);
            }
            System.out.println("DAO classes generated");

            GeneratorService generatorService;
            if (DB_TYPE_ORACLE.equalsIgnoreCase(getDbType())) {
                generatorService = new Generator4OracleService(table);
            } else {
                generatorService = new GeneratorService(table);
            }
            generatorService.setClassName(serviceClassName);
            generatorService.setPackageName(servicePackageName);
            generatorService.setInterfaceName(interfaceName);
            generatorService.setHomeDir(homePath);
            generatorService.setGeneratorDAO(generateDAO);
            if (getGenerationMode() == 1 || getGenerationMode() == 3) {
                generateFiles(generatorService, suffix);
            } else {
                if (getGenerationMode() == 2)
                    if (generatorService.getInterfaceName() != null) {
                        generateServiceInterfaceFiles(generatorService, suffix);
                    }
            }
            conn.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static String tranforName(String name) {
        String newName = "";
        int letterOfWord = 0;
        for (char ch : name.toCharArray()) {
            if (ch == '_') {
                letterOfWord = 0;
            } else {
                if (letterOfWord == 0) {
                    newName = newName + Character.toUpperCase(ch);
                } else {
                    newName = newName + Character.toLowerCase(ch);
                }
                letterOfWord = letterOfWord + 1;
            }

        }
        return newName;
    }

}
