package lk.disaster.entity;

import java.time.LocalDateTime;


public class DisasterReport {

    private int id;
    private String fullName;
    private String contactNumber;
    private String emailAddress;
    private String nationalIdNumber;
    private String streetAddress;
    private String gramaNiladhariDivision;
    private String district;
    private String province;
    private String typeOfDisaster;
    private LocalDateTime dateOfIncident;
    private String impactDescription;
    private int numberOfAffectedIndividuals;
    private String urgencyLevel;
    private String status;

    public DisasterReport() {
    }

    public DisasterReport(int id, String fullName, String contactNumber, String emailAddress, String nationalIdNumber, String streetAddress, String gramaNiladhariDivision, String district, String province, String typeOfDisaster, LocalDateTime dateOfIncident, String impactDescription, int numberOfAffectedIndividuals, String urgencyLevel, String status) {
        this.id = id;
        this.fullName = fullName;
        this.contactNumber = contactNumber;
        this.emailAddress = emailAddress;
        this.nationalIdNumber = nationalIdNumber;
        this.streetAddress = streetAddress;
        this.gramaNiladhariDivision = gramaNiladhariDivision;
        this.district = district;
        this.province = province;
        this.typeOfDisaster = typeOfDisaster;
        this.dateOfIncident = dateOfIncident;
        this.impactDescription = impactDescription;
        this.numberOfAffectedIndividuals = numberOfAffectedIndividuals;
        this.urgencyLevel = urgencyLevel;
        this.status = status;
    }

    public DisasterReport(String fullName, String contactNumber, String emailAddress, String nationalIdNumber, String streetAddress, String gramaNiladhariDivision, String district, String province, String typeOfDisaster, LocalDateTime dateOfIncident, String impactDescription, int numberOfAffectedIndividuals, String urgencyLevel) {
        this.fullName = fullName;
        this.contactNumber = contactNumber;
        this.emailAddress = emailAddress;
        this.nationalIdNumber = nationalIdNumber;
        this.streetAddress = streetAddress;
        this.gramaNiladhariDivision = gramaNiladhariDivision;
        this.district = district;
        this.province = province;
        this.typeOfDisaster = typeOfDisaster;
        this.dateOfIncident = dateOfIncident;
        this.impactDescription = impactDescription;
        this.numberOfAffectedIndividuals = numberOfAffectedIndividuals;
        this.urgencyLevel = urgencyLevel;
        this.status = "pending";
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getNationalIdNumber() {
        return nationalIdNumber;
    }

    public void setNationalIdNumber(String nationalIdNumber) {
        this.nationalIdNumber = nationalIdNumber;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getGramaNiladhariDivision() {
        return gramaNiladhariDivision;
    }

    public void setGramaNiladhariDivision(String gramaNiladhariDivision) {
        this.gramaNiladhariDivision = gramaNiladhariDivision;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getTypeOfDisaster() {
        return typeOfDisaster;
    }

    public void setTypeOfDisaster(String typeOfDisaster) {
        this.typeOfDisaster = typeOfDisaster;
    }

    public LocalDateTime getDateOfIncident() {
        return dateOfIncident;
    }

    public void setDateOfIncident(LocalDateTime dateOfIncident) {
        this.dateOfIncident = dateOfIncident;
    }

    public String getImpactDescription() {
        return impactDescription;
    }

    public void setImpactDescription(String impactDescription) {
        this.impactDescription = impactDescription;
    }

    public int getNumberOfAffectedIndividuals() {
        return numberOfAffectedIndividuals;
    }

    public void setNumberOfAffectedIndividuals(int numberOfAffectedIndividuals) {
        this.numberOfAffectedIndividuals = numberOfAffectedIndividuals;
    }

    public String getUrgencyLevel() {
        return urgencyLevel;
    }

    public void setUrgencyLevel(String urgencyLevel) {
        this.urgencyLevel = urgencyLevel;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "DisasterReport{" +
                "id=" + id +
                ", fullName='" + fullName + '\'' +
                ", contactNumber='" + contactNumber + '\'' +
                ", emailAddress='" + emailAddress + '\'' +
                ", nationalIdNumber='" + nationalIdNumber + '\'' +
                ", streetAddress='" + streetAddress + '\'' +
                ", gramaNiladhariDivision='" + gramaNiladhariDivision + '\'' +
                ", district='" + district + '\'' +
                ", province='" + province + '\'' +
                ", typeOfDisaster='" + typeOfDisaster + '\'' +
                ", dateOfIncident=" + dateOfIncident +
                ", impactDescription='" + impactDescription + '\'' +
                ", numberOfAffectedIndividuals=" + numberOfAffectedIndividuals +
                ", urgencyLevel='" + urgencyLevel + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}