package eu.itreegroup.s2.client.exception;

import java.util.Arrays;
import java.util.Collection;

import lt.jmsys.spark.bind.executor.plsql.errors.SparkBusinessException;
import lt.jmsys.spark.bind.executor.plsql.errors.SparkHasMessages;
import lt.jmsys.spark.bind.executor.plsql.errors.SparkUserMessage;

/**
 * Klasė wrapina SparkBusinessException. Naudojama tais atvejais, kai nėra galimybės išmesti SparkBusinessException.
 * Šis exception yra unchecked, nes paveldi iš RuntimeException.
 * 
 *
 */
public class SparkRuntimeException extends RuntimeException implements SparkHasMessages {
    
    private static final long serialVersionUID = 1L;
    
    protected SparkBusinessException sparkBusinessException;

    protected SparkRuntimeException() {
        super();
    }

    public SparkRuntimeException(SparkUserMessage message) {
        this(message, null);
    }

    public SparkRuntimeException(SparkUserMessage[] messages) {
        this(messages == null ? null : Arrays.asList(messages), null);
    }

    public SparkRuntimeException(Collection<SparkUserMessage> messages) {
        this(messages, null);
    }

    public SparkRuntimeException(SparkUserMessage message, String exceptionDetails) {
        super(exceptionDetails);
        sparkBusinessException = new SparkBusinessException(message, exceptionDetails);
    }

    public SparkRuntimeException(Collection<SparkUserMessage> messageList, String exceptionDetails) {
        super(exceptionDetails);
        sparkBusinessException = new SparkBusinessException(messageList, exceptionDetails);
    }

    @Override
    public Collection<SparkUserMessage> getMessages() {
        return sparkBusinessException.getMessages();
    }

    public void addMessage(SparkUserMessage message) {
        sparkBusinessException.addMessage(message);
    }

    @Override
    public String getLocalizedMessage() {
        return sparkBusinessException.getLocalizedMessage();

    }

    @Override
    public String toString() {
        return "SparkRuntimeException [messages=" + sparkBusinessException.getMessages() + "]";
    }

}
