package eu.itreegroup.s2.pay.viisp.model;

public class ViispPaymentInitResult {
	private String serviceUrl;
	private String postData;
	
	public String getServiceUrl() {
		return serviceUrl;
	}
	public void setServiceUrl(String serviceUrl) {
		this.serviceUrl = serviceUrl;
	}
	public String getPostData() {
		return postData;
	}
	public void setPostData(String postData) {
		this.postData = postData;
	}
}
