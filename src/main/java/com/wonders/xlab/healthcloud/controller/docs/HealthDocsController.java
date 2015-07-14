package com.wonders.xlab.healthcloud.controller.docs;

import com.wonders.xlab.healthcloud.dto.docs.DiagnosisDto;
import com.wonders.xlab.healthcloud.dto.docs.HealthDocsDto;
import com.wonders.xlab.healthcloud.dto.docs.MedicationDto;
import com.wonders.xlab.healthcloud.dto.result.ControllerResult;
import com.wonders.xlab.healthcloud.entity.docs.HealthDocs;
import com.wonders.xlab.healthcloud.repository.customer.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mars on 15/7/9.
 */
@RestController
@RequestMapping("healthDocs")
public class HealthDocsController {

    @Autowired
    private UserRepository userRepository;

    @RequestMapping("listHealthDocs")
    public Object listHealthDocs() {

        List<HealthDocs> docses = userRepository.findHealthDocs();
        List<HealthDocsDto> healthDocsDtos = new ArrayList<>();
        for (HealthDocs doc : docses) {
            String[] diagnosisHistory = doc.getDiagnosisHistory().split(",", 4);
            List<DiagnosisDto> diagnosisDtos = new ArrayList<>();

            for (String history : diagnosisHistory) {
                String[] str = history.split(",");
                for (int i = 0; i < str.length; i ++) {
                    DiagnosisDto  dto = new DiagnosisDto();
                    if (i == 0) {
                        dto.setTime(str[0].trim());
                    }
                    if (i == 1) {
                        dto.setArea(str[1].trim());
                    }
                    if (i == 2) {
                        dto.setTreatmentMethod(str[2].trim());
                    }
                    if (i == 3) {
                        dto.setDisease(str[3].trim());
                    }
                    diagnosisDtos.add(dto);
                }

            }
            String[] medicineHistory = doc.getMedicineHistory().split(",", 3);
            List<MedicationDto> medicationDtos = new ArrayList<>();
            for (String history : medicineHistory) {
                String[] str = history.split(",");

                for (int i = 0; i < str.length; i++) {
                    MedicationDto dto = new MedicationDto();
                    if (i == 0) {
                        dto.setTime(str[0].trim());
                    }
                    if (i == 1) {
                        dto.setMedicineName(str[1].trim());
                    }
                    if (i == 2) {
                        dto.setTakeMethod(str[2].trim());
                    }
                    medicationDtos.add(dto);
                }
            }
            HealthDocsDto dto = new HealthDocsDto(
                    doc.getName(),
                    doc.getGender(),
                    doc.getCardNumber(),
                    diagnosisDtos,
                    medicationDtos
            );
            healthDocsDtos.add(dto);
        }

        return new ControllerResult<List<HealthDocsDto>>().setRet_code(0).setRet_values(healthDocsDtos).setMessage("成功");
//        return docses;

    }

}
