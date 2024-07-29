package eu.itreegroup.s2.auth.viisp.model;

import java.io.Serializable;

@SuppressWarnings("serial")
public class ViispAuthResult implements Serializable {
    
    private String viispUrlWithTicket;

    
    public String getViispUrlWithTicket() {
        return viispUrlWithTicket;
    }

    public void setViispUrlWithTicket(String viispUrlWithTicket) {
        this.viispUrlWithTicket = viispUrlWithTicket;
    }

    @Override
    public String toString() {
        return "ViispAuthResult [viispUrlWithTicket=" + viispUrlWithTicket + "]";
    }

}
