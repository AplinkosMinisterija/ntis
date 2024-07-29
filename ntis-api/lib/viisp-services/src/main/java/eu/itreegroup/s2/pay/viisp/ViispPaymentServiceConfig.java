package eu.itreegroup.s2.pay.viisp;

import eu.itreegroup.s2.CertConfig;

public interface ViispPaymentServiceConfig extends CertConfig {
	 String getContractId();
	 
	 String getSenderId();
	 
	 String getPostUrl();
	 
	 String getReturnUrlPost();
	 
	 String getReturnUrlServer();
}
