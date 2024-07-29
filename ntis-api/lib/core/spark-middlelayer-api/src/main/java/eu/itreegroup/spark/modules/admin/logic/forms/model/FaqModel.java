package eu.itreegroup.spark.modules.admin.logic.forms.model;

import java.util.Date;
import java.util.List;

public class FaqModel {
	
	private Double facId;
	private String facGroup; 
	private String facType; 
	private String facLang; 
	private String facCode;
	private String facQuestion; 
	private String facAnswer; 
	private Date facDateFrom; 
	private Date facDateTo; 
	private List<SprFile> attachment;
	
	public FaqModel() {
		super();
	}
	
	public Double getFacId() {
		return facId;
	}
	public void setFacId(Double facId) {
		this.facId = facId;
	}
	public String getFacGroup() {
		return facGroup;
	}
	public void setFacGroup(String facGroup) {
		this.facGroup = facGroup;
	}
	public String getFacType() {
		return facType;
	}
	public void setFacType(String facType) {
		this.facType = facType;
	}
	public String getFacLang() {
		return facLang;
	}
	public void setFacLang(String facLang) {
		this.facLang = facLang;
	}
	public String getFacCode() {
		return facCode;
	}
	public void setFacCode(String facCode) {
		this.facCode = facCode;
	}
	public String getFacQuestion() {
		return facQuestion;
	}
	public void setFacQuestion(String facQuestion) {
		this.facQuestion = facQuestion;
	}
	public String getFacAnswer() {
		return facAnswer;
	}
	public void setFacAnswer(String facAnswer) {
		this.facAnswer = facAnswer;
	}
	public Date getFacDateFrom() {
		return facDateFrom;
	}
	public void setFacDateFrom(Date facDateFrom) {
		this.facDateFrom = facDateFrom;
	}
	public Date getFacDateTo() {
		return facDateTo;
	}
	public void setFacDateTo(Date facDateTo) {
		this.facDateTo = facDateTo;
	}
	public List<SprFile> getAttachment() {
		return attachment;
	}
	public void setAttachment(List<SprFile> attachment) {
		this.attachment = attachment;
	}

}
