package lt.project.ntis.models;

import eu.itreegroup.spark.annotations.TypeScriptOptional;

public class NtisServiceSearchResultService {

    private Integer id;

    private String name;

    private String type;

    private String description;
    
    private String phone;
    
    private String email;

    private Integer priceFrom;

    private Integer priceTo;

    private Integer completionInDays;

    private Boolean contractAvailable;

    @TypeScriptOptional
    private Integer completionInDaysTo;

    public NtisServiceSearchResultService() {
        super();
    }

    public NtisServiceSearchResultService(Integer id, String name, String type, String description, String phone, String email, Integer priceFrom, Integer priceTo,
            Integer completionInDays, Boolean contractAvailable, Integer completionInDaysTo) {
        super();
        this.id = id;
        this.name = name;
        this.type = type;
        this.description = description;
        this.phone = phone;
        this.email = email;
        this.priceFrom = priceFrom;
        this.priceTo = priceTo;
        this.completionInDays = completionInDays;
        this.contractAvailable = contractAvailable;
        this.completionInDaysTo = completionInDaysTo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPriceFrom() {
        return priceFrom;
    }

    public void setPriceFrom(Integer priceFrom) {
        this.priceFrom = priceFrom;
    }

    public Integer getPriceTo() {
        return priceTo;
    }

    public void setPriceTo(Integer priceTo) {
        this.priceTo = priceTo;
    }

    public Integer getCompletionInDays() {
        return completionInDays;
    }

    public void setCompletionInDays(Integer completionInDays) {
        this.completionInDays = completionInDays;
    }

    public Boolean getContractAvailable() {
        return contractAvailable;
    }

    public void setContractAvailable(Boolean contractAvailable) {
        this.contractAvailable = contractAvailable;
    }

    public Integer getCompletionInDaysTo() {
        return completionInDaysTo;
    }

    public void setCompletionInDaysTo(Integer completionInDaysTo) {
        this.completionInDaysTo = completionInDaysTo;
    }

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

}
