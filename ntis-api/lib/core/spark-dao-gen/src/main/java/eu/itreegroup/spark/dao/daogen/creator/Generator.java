package eu.itreegroup.spark.dao.daogen.creator;

import java.io.File;

import eu.itreegroup.spark.dao.daogen.struct.Table;

public abstract class Generator {

    public static int CLASS_TYPE_DAO = 1;

    public static int CLASS_TYPE_SERVICE = 2;

    public static int CLASS_TYPE_SERVICE_INTERFACE = 3;

    public static int CLASS_LEVEL_GEN = 1;

    public static int CLASS_LEVEL_CUST = 2;

    public Table table;

    public String homeDir;

    public String packageName;

    public String className;

    public String interfaceName;

    public int classType;

    public Generator(Table table) {
        this.table = table;
    }

    public String getClassName() {
        return this.className;
    }

    public String getClassName(int classLevel) {
        if (getClassType() == CLASS_TYPE_DAO) {
            return getClassName();
        } else {
            if (classLevel == CLASS_LEVEL_GEN) {
                return getClassName();
            } else {
                if (classLevel == CLASS_TYPE_SERVICE_INTERFACE) {
                    return getInterfaceName();
                } else {
                    if (getInterfaceName() == null) {
                        return getClassName();
                    } else {
                        return getInterfaceName() + "Impl";
                    }
                }
            }
        }
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getPackageName() {
        return this.packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public void setHomeDir(String homeDir) {
        this.homeDir = homeDir;
    }

    public String getHomeDir() {
        return this.homeDir;
    }

    public String getPathForPackage() {
        return packageName.replace('.', File.separatorChar);
    }

    public Table getTable() {
        return this.table;
    }

    public void setTable(Table table) {
        this.table = table;
    }

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    public int getClassType() {
        return classType;
    }

    public void setClassType(int classType) {
        this.classType = classType;
    }

    public abstract String generateGenClasses(String suffix);

    public abstract String generateClasses(String suffix);

    public abstract String generateInterface();
}
