package com.wonders.xlab.healthcloud.entity.docs;

/**
 * Created by mars on 15/7/9.
 */
public class HealthDocs {

    private String name;

    private String gender;

    private String cardNumber;

    private String diagnosisHistory;

    private String medicineHistory;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getDiagnosisHistory() {
        return diagnosisHistory;
    }

    public void setDiagnosisHistory(String diagnosisHistory) {
        this.diagnosisHistory = diagnosisHistory;
    }

    public String getMedicineHistory() {
        return medicineHistory;
    }

    public void setMedicineHistory(String medicineHistory) {
        this.medicineHistory = medicineHistory;
    }
}
