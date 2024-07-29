package eu.itreegroup.s2.client.util;

import java.util.Arrays;

import lt.jmsys.spark.bind.executor.plsql.errors.SparkMessageType;
import lt.jmsys.spark.bind.executor.plsql.errors.SparkUserMessage;

public class S2Message implements SparkUserMessage {

    public static final String MESSAGE_SOURCE_DB = "db";

    public static final String MESSAGE_SOURCE_SERVER = "server";

    public static final String MESSAGE_SOURCE_CLIENT = "client";

    private static final long serialVersionUID = 1L;

    protected SparkMessageType type;

    protected String key;

    protected String text;

    protected String module;

    protected String item;

    protected String[] params;

    protected String source;

    public static S2Message toS2Message(SparkUserMessage sourceMsg) {
        if (sourceMsg == null) {
            return null;
        }
        String module = sourceMsg.getModule() != null ? sourceMsg.getModule().toLowerCase() : null;
        SparkMessageType type = SparkMessageType.getType(sourceMsg.getSeverity());
        String code = sourceMsg.getCode() != null ? sourceMsg.getCode() : "unknown";

        String params[] = null;
        if (sourceMsg.getParam5() != null) {
            params = new String[] { sourceMsg.getParam1(), sourceMsg.getParam2(), sourceMsg.getParam3(), sourceMsg.getParam4(), sourceMsg.getParam5() };
        } else if (sourceMsg.getParam4() != null) {
            params = new String[] { sourceMsg.getParam1(), sourceMsg.getParam2(), sourceMsg.getParam3(), sourceMsg.getParam4() };
        } else if (sourceMsg.getParam3() != null) {
            params = new String[] { sourceMsg.getParam1(), sourceMsg.getParam2(), sourceMsg.getParam3() };
        } else if (sourceMsg.getParam2() != null) {
            params = new String[] { sourceMsg.getParam1(), sourceMsg.getParam2() };
        } else if (sourceMsg.getParam1() != null) {
            params = new String[] { sourceMsg.getParam1() };
        }

        return new S2Message(module, sourceMsg.getItem(), S2Message.MESSAGE_SOURCE_DB, code, type, sourceMsg.getDefault_text(), params);
    }

    /**
     * Constructor for GWT serialization...
     * http://www.gwtproject.org/doc/latest/DevGuideServerCommunication.html#DevGuideSerializableTypes:
     * As of GWT 1.5, it must have a default (zero argument) constructor (with any access modifier) or no constructor at all.
     */
    protected S2Message() {
    }

    /**
     * Unlocalized message (should not be used if possible?). Change it to S2Message(key, type)
     * @param type
     * @param text
     */
    public S2Message(SparkMessageType type, String text) {
        this(null, null, null, null, type, text, (String[]) null);
    }

    public S2Message(String key, SparkMessageType type) {
        this(null, null, null, key, type, null, (String[]) null);
    }

    public S2Message(String key, SparkMessageType type, String... params) {
        this(null, null, null, key, type, null, params);
    }

    /**
     * Localized message (final text is retrieved from resources)
     * @param module - bloko pavadinimas, kuriame yra item (field'as). Neprivalomas.
     * @param item - lauko pavadinimas, kuriam yra skirtas pranešimas. Neprivalomas.
     * @param key - pranešimo kodas. Pagal jį ieškomas pranešimo tekstas.
     * @param type - pranešimo tipas.
     * @param params - pranešimo parametrai, kurie gali būti įkeliami į pranešimo tekstą.
     */
    public S2Message(String module, String item, String key, SparkMessageType type, String... params) {
        this(module, item, null, key, type, null, params);
    }

    /**
     * Localized message (final text is retrieved from resources)
     * @param module - bloko pavadinimas, kuriame yra item (field'as). Neprivalomas.
     * @param item - lauko pavadinimas, kuriam yra skirtas pranešimas. Neprivalomas.
     * @param source - šaltinis, iš kur gaunama klaida (DB, server, client).
     * @param key - pranešimo kodas. Pagal jį ieškomas pranešimo tekstas.
     * @param type - pranešimo tipas.
     * @param params - pranešimo parametrai, kurie gali būti įkeliami į pranešimo tekstą.
     */
    public S2Message(String module, String item, String source, String key, SparkMessageType type, String... params) {
        this(module, item, source, key, type, null, params);
    }

    /**
     * Konstruktorius
     * @param module - bloko pavadinimas, kuriame yra item (field'as). Neprivalomas.
     * @param item - lauko pavadinimas, kuriam yra skirtas pranešimas. Neprivalomas.
     * @param source - šaltinis, iš kur gaunama klaida (DB, server, client).
     * @param key - pranešimo kodas. Pagal jį ieškomas pranešimo tekstas.
     * @param text - pranešimo tekstas.
     * @param type - pranešimo tipas.
     * @param params - pranešimo parametrai, kurie gali būti įkeliami į pranešimo tekstą.
     */
    public S2Message(String module, String item, String source, String key, SparkMessageType type, String text, String... params) {
        this.module = module;
        this.item = item;
        this.source = source;
        // safe defaults to ensure that S2Exception is created even if unexpected values are passed
        // throw new IllegalArgumentException("Message.key is null or empty");
        this.type = type != null ? type : SparkMessageType.FATAL;
        if ((key == null || key.isEmpty()) && (text == null || text.isEmpty())) {
            this.key = "unknonwn";
            this.text = "Message key and text is null or empty";
        } else {
            this.key = key;
            this.text = text;
        }
        this.params = params;
    }

    public SparkMessageType getType() {
        return type;
    }

    public String getKey() {
        return key;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String getModule() {
        return module;
    }

    @Override
    public String getItem() {
        return item;
    }

    public String[] getParams() {
        return params;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Message [");
        if (type != null) {
            builder.append("type=").append(type).append(", ");
        }
        if (source != null) {
            builder.append("source=").append(source).append(", ");
        }
        if (module != null) {
            builder.append("module=").append(module).append(", ");
        }
        if (item != null) {
            builder.append("item=").append(item).append(", ");
        }
        if (key != null) {
            builder.append("key=").append(key).append(", ");
        }
        if (params != null) {
            builder.append("params=").append(Arrays.toString(params));
        }
        if (text != null) {
            builder.append("text=").append(text);
        }
        builder.append("]");
        return builder.toString();
    }

    @Override
    public String getSeverity() {
        return type.getSeverity();
    }

    @Override
    public String getCode() {
        return key;
    }

    @Override
    public String getDefault_text() {
        return text;
    }

    @Override
    public String getParam1() {
        return params != null && params.length > 0 ? params[0] : null;
    }

    @Override
    public String getParam2() {
        return params != null && params.length > 1 ? params[1] : null;
    }

    @Override
    public String getParam3() {
        return params != null && params.length > 2 ? params[2] : null;
    }

    @Override
    public String getParam4() {
        return params != null && params.length > 3 ? params[3] : null;
    }

    @Override
    public String getParam5() {
        return params != null && params.length > 4 ? params[4] : null;
    }
}
