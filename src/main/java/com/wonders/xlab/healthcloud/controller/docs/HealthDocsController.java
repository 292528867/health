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

            String[] diagnosisHistory = doc.getDiagnosisHistory().trim().split(",");

            int length = diagnosisHistory.length / 4;
            List<DiagnosisDto> diagnosisDtos = new ArrayList<>();
            for (int i = 0; i < length; i++) {
                DiagnosisDto dto = new DiagnosisDto();
                dto.setTime(diagnosisHistory[4 * i]);
                dto.setArea(diagnosisHistory[4 * i + 1]);
                dto.setTreatmentMethod(diagnosisHistory[4 * i +2]);
                dto.setDisease(diagnosisHistory[4 * i + 3]);
                diagnosisDtos.add(dto);
            }

            String[] medicineHistory = doc.getMedicineHistory().trim().split(",");
            List<MedicationDto> medicationDtos = new ArrayList<>();
            length = medicineHistory.length / 3;
            for (int i = 0; i < length; i++) {
                MedicationDto dto = new MedicationDto();
                dto.setTime(medicineHistory[3 * i]);
                dto.setMedicineName(medicineHistory[3 * i + 1]);
                dto.setTakeMethod(medicineHistory[3 * i + 2]);
                medicationDtos.add(dto);
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
