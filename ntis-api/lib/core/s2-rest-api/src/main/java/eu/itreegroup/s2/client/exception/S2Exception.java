package eu.itreegroup.s2.client.exception;

import java.util.Collection;

import lt.jmsys.spark.bind.executor.plsql.errors.SparkBusinessException;
import lt.jmsys.spark.bind.executor.plsql.errors.SparkUserMessage;

public class S2Exception extends SparkBusinessException {

    private static final long serialVersionUID = 1L;

    /**
     * Constructor for GWT serialization...
     * http://www.gwtproject.org/doc/latest/DevGuideServerCommunication.html#DevGuideSerializableTypes:
     * As of GWT 1.5, it must have a default (zero argument) constructor (with any access modifier) or no constructor at all.
     */
    protected S2Exception() {
        super();
    }

    public S2Exception(SparkUserMessage message) {
        super(message, null);
    }

    public S2Exception(Collection<SparkUserMessage> messages) {
        super(messages, null);
    }

    public S2Exception(SparkUserMessage message, String exceptionDetails) {
        super(message, exceptionDetails);
    }

    public S2Exception(Collection<SparkUserMessage> messageList, String exceptionDetails) {
        super(messageList, exceptionDetails);
    }

    @Override
    public String toString() {
        return "S2Exception [messages=" + getMessages() + "]";
    }
}
