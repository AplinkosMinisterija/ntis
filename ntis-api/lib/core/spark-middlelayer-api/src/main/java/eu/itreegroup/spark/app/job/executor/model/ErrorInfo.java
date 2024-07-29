package eu.itreegroup.spark.app.job.executor.model;

import org.json.JSONObject;

/**
 * Class used for error message presentation
 * 
 *
 */
public class ErrorInfo {

    /**
     * Host name, where error was raised
     */
    private String host;

    /**
     * Error message, that describes reason of error
     */
    private String errorMessage;

    /**
     * Error trace
     */
    private String errorTrace;

    /**
     * Getter for host
     * @return
     */
    public String getHost() {
        return host;
    }

    /**
     * Setter for host
     * @param host
     */
    public void setHost(String host) {
        this.host = host;
    }

    /**
     * Getter for errorMessage
     * @return
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * Setter for errorMessage
     * @param errorMessage
     */
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    /**
     * Getter for errorTrace.
     * @return
     */
    public String getErrorTrace() {
        return errorTrace;
    }

    /**
     * Setter for errorTrace parameter
     * @param errorTrace
     */
    public void setErrorTrace(String errorTrace) {
        this.errorTrace = errorTrace;
    }

    /**
     * Function will construct json presentation of current object. JSON will be constructed no bigger then provided jsonMaxSize.
     * @param jsonMaxSize count in symbols what json length should be constructed.
     * @return json presentation of current object
     */
    public String toJson(int jsonMaxSize) {
        String asnwer = "{\"host\":" + JSONObject.quote(host) + ", \"errorMessage\":" + JSONObject.quote(errorMessage) + ", \"errorTrace\":";
        String traceJson = JSONObject.quote(errorTrace);
        if (jsonMaxSize < (asnwer.length() + traceJson.length() + 10)) {
            return asnwer + traceJson.substring(0, (jsonMaxSize - asnwer.length() - 10)) + "...\"}";
        } else {
            return asnwer + errorTrace + "}";
        }
    }

}
