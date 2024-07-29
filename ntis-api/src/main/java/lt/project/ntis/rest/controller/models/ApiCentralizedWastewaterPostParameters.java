package lt.project.ntis.rest.controller.models;

import org.springframework.format.annotation.DateTimeFormat;

import io.swagger.v3.oas.annotations.Parameter;

public class ApiCentralizedWastewaterPostParameters {

	private @Parameter(description = "Unikalus nekilnojamojo turto kadastre esantis statinio numeris.") //
	String realEstateId;

	private @Parameter(description = "Kodas atitinkantis rinkinio \"Gyvenamos ir negyvenamosioms patalpoms suteikti adresai (visa LR teritorija)\" duomenis. \r\n " //
	        + "\r\n https://www.registrucentras.lt/p/1187", example = "157559157") //
	String aobCode;

	private @Parameter(description = "Kodas atitinkantis rinkinio \"Gyvenamos ir negyvenamosioms patalpoms suteikti adresai (visa LR teritorija)\" duomenis. \r\n "//
	        + "\r\n https://www.registrucentras.lt/p/1187") //
	String patCode;

	private @Parameter(description = "Kodas atitinkantis rinkinio \"Savivaldybės\" duomenis. \r\n "//
	        + "\r\n https://www.registrucentras.lt/p/1187", example = "45") //
	String municipalityCode;

	private @Parameter(description = "Pavadinimas atitinkantis rinkinio \"Savivaldybės\" duomenis. \r\n" //
	        + "\r\n https://www.registrucentras.lt/p/1187", example = " ") //
	String municipalityName;

	private @Parameter(description = "Kodas atitinkantis rinkinio \"Seniūnijos\" duomenis. \r\n "//
	        + "\r\n https://www.registrucentras.lt/p/1187", example = "4520") //
	String eldershipCode;

	private @Parameter(description = "Pavadinimas atitinkantis rinkinio \"Seniūnijos\" duomenis. \r\n " //
	        + " \r\n https://www.registrucentras.lt/p/1187", example = " ") //
	String eldershipName;

	private @Parameter(description = "Kodas atitinkantis rinkinio \"Gyvenamosios vietovės\" duomenis. \r\n " //
	        + "\r\n https://www.registrucentras.lt/p/1187", example = "13490") //
	String residenceCode;

	private @Parameter(description = "Pavadinimas atitinkantis rinkinio \"Gyvenamosios vietovės\" duomenis. \r\n " //
	        + "\r\n https://www.registrucentras.lt/p/1187", example = " ") //
	String residenceName;

	private @Parameter(description = "Kodas atitinkantis rinkinio \"Gatvės\" duomenis. \r\n" //
	        + "\r\n https://www.registrucentras.lt/p/1187", example = "1307544") //
	String streetCode;

	private @Parameter(description = "Pavadinimas atitinkantis rinkinio \"Gatvės\" duomenis. \r\n " //
	        + "\r\n https://www.registrucentras.lt/p/1187" , example = " ") //
	String streetName;

	private @Parameter(description = "Pastato numeris pagal Adresų registrą. Tekstinis laukas susidedantis iš skaitmenų ir raidės.", example = "33") //
	String buildingNumber;

	private @Parameter(description = "Pastato korpuso numeris pagal Adresų registrą. Skaitinis laukas. Turi būti pateiktas, jei objektas turi korpusą.") //
	String buildingUnit;

	private @Parameter(description = "Buto numeris pagal Adresų registrą. Skaitinis laukas. Turi būti pateiktas, jei objektas turi buto numerį.") //
	String flatNumber;

	private @Parameter(description = "Vandentvarkos įmonės identifikatorius (juridinio asmens kodas pagal Juridinių asmenų registrą).") //
	String orgCode;

	private @Parameter(description = "Nuotekų šalinimo būdas (galimos reikšmės: centralizuotas, vietinis)") //
	String wastewaterRemovalType;

	private @Parameter(description = "Prijungimo prie centralizuoto nuotekų tvarkymo data. Pagal prijungimo būdą nurodoma data, kada objektas prijungtas prie nurodyto nuotekų šalinimo būdo. Jei nėra žinoma tiksli data, pateikiama anksčiausia žinoma data. YYYY-MM-DD formatu pateikiama data. PVZ.: 2023-02-01 (2023 metų vasario 1d.)") //
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) //
	String connectionDate;

	private @Parameter(description = "Atjungimo nuo centralizuoto nuotekų tvarkymo data. Pagal prijungimo būdą nurodoma data, kada objektas atjungtas nuo nurodyto nuotekų šalinimo būdo. Jei nėra žinoma tiksli data, pateikiama anksčiausia žinoma data. YYYY-MM-DD formatu pateikiama data. PVZ.: 2023-02-01 (2023 metų vasario 1d.)") //
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) //
	String disconnectionDate;

	public String getRealEstateId() {
		return realEstateId;
	}

	public void setRealEstateId(String realEstateId) {
		this.realEstateId = realEstateId;
	}

	public String getAobCode() {
		return aobCode;
	}

	public void setAobCode(String aobCode) {
		this.aobCode = aobCode;
	}

	public String getPatCode() {
		return patCode;
	}

	public void setPatCode(String patCode) {
		this.patCode = patCode;
	}

	public String getMunicipalityCode() {
		return municipalityCode;
	}

	public void setMunicipalityCode(String municipalityCode) {
		this.municipalityCode = municipalityCode;
	}

	public String getMunicipalityName() {
		return municipalityName;
	}

	public void setMunicipalityName(String municipalityName) {
		this.municipalityName = municipalityName;
	}

	public String getEldershipCode() {
		return eldershipCode;
	}

	public void setEldershipCode(String eldershipCode) {
		this.eldershipCode = eldershipCode;
	}

	public String getEldershipName() {
		return eldershipName;
	}

	public void setEldershipName(String eldershipName) {
		this.eldershipName = eldershipName;
	}

	public String getResidenceCode() {
		return residenceCode;
	}

	public void setResidenceCode(String residenceCode) {
		this.residenceCode = residenceCode;
	}

	public String getResidenceName() {
		return residenceName;
	}

	public void setResidenceName(String residenceName) {
		this.residenceName = residenceName;
	}

	public String getStreetCode() {
		return streetCode;
	}

	public void setStreetCode(String streetCode) {
		this.streetCode = streetCode;
	}

	public String getStreetName() {
		return streetName;
	}

	public void setStreetName(String streetName) {
		this.streetName = streetName;
	}

	public String getBuildingNumber() {
		return buildingNumber;
	}

	public void setBuildingNumber(String buildingNumber) {
		this.buildingNumber = buildingNumber;
	}

	public String getBuildingUnit() {
		return buildingUnit;
	}

	public void setBuildingUnit(String buildingUnit) {
		this.buildingUnit = buildingUnit;
	}

	public String getFlatNumber() {
		return flatNumber;
	}

	public void setFlatNumber(String flatNumber) {
		this.flatNumber = flatNumber;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getWastewaterRemovalType() {
		return wastewaterRemovalType;
	}

	public void setWastewaterRemovalType(String wastewaterRemovalType) {
		this.wastewaterRemovalType = wastewaterRemovalType;
	}

	public String getConnectionDate() {
		return connectionDate;
	}

	public void setConnectionDate(String connectionDate) {
		this.connectionDate = connectionDate;
	}

	public String getDisconnectionDate() {
		return disconnectionDate;
	}

	public void setDisconnectionDate(String disconnectionDate) {
		this.disconnectionDate = disconnectionDate;
	}

}