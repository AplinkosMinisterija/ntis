package eu.itreegroup.spark.app.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Used for menu hierarchy implementation 
 * 
 *
 */
public class MenuStructure implements Serializable {

    private static final long serialVersionUID = 635273008429909526L;

    public static String MENU_STRUCTURE_CATEGORY = "CATEGORY";

    public static String MENU_STRUCTURE_LINK = "LINK";

    private String type;

    private String level;

    private String link;

    private String name;

    private String iconName;

    private List<MenuStructure> rows;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        if (type != null && MENU_STRUCTURE_CATEGORY.equals(type)) {
            rows = new ArrayList<MenuStructure>();
        } else {
            rows = null;
        }
        this.type = type;
    }

    public String getLevel() {
        return this.level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIconName() {
        return this.iconName;
    }

    public void setIconName(String iconName) {
        this.iconName = iconName;
    }

    public List<MenuStructure> getRows() {
        return this.rows;
    }

    public void setRow(List<MenuStructure> rows) {
        this.rows = rows;
    }

    @Override
    public String toString() {
        return "Type: " + type + " " + name + (rows != null ? rows : "");
    }

}
