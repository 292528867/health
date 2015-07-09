package com.wonders.xlab.healthcloud.dto.docs;

import java.util.List;

/**
 * Created by mars on 15/7/9.
 */
public class HealthDocsDto {

    /** 名称 */
    private String name;

    /** 性别 */
    private String gender;

    /** 卡号 */
    private String cardNumber;

    private List<DiagnosisDto> diagnosisDtos;

    private List<MedicationDto> medicationDtos;

    public HealthDocsDto() {
    }

    public HealthDocsDto(String name, String gender, String cardNumber, List<DiagnosisDto> diagnosisDtos, List<MedicationDto> medicationDtos) {
        this.name = name;
        this.gender = gender;
        this.cardNumber = cardNumber;
        this.diagnosisDtos = diagnosisDtos;
        this.medicationDtos = medicationDtos;
    }

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

    public List<DiagnosisDto> getDiagnosisDtos() {
        return diagnosisDtos;
    }

    public void setDiagnosisDtos(List<DiagnosisDto> diagnosisDtos) {
        this.diagnosisDtos = diagnosisDtos;
    }

    public List<MedicationDto> getMedicationDtos() {
        return medicationDtos;
    }

    public void setMedicationDtos(List<MedicationDto> medicationDtos) {
        this.medicationDtos = medicationDtos;
    }
}
