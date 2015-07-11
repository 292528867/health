package com.wonders.xlab.healthcloud.controller.docs;

import com.wonders.xlab.healthcloud.entity.docs.HealthDocs;
import com.wonders.xlab.healthcloud.repository.customer.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
       /* List<HealthDocsDto> healthDocsDtos = new ArrayList<>();
        for (HealthDocs doc : docses) {
            String[] diagnosisHistory = doc.getDiagnosisHistory().split(",", 4);
            List<DiagnosisDto> diagnosisDtos = new ArrayList<>();
            for (String history : diagnosisHistory) {
                String[] str = history.split(",");
                for (int i = 0; i < str.length; i ++) {

                }
                diagnosisDtos.add(new DiagnosisDto(str[0], str[1], str[2], str[3]));
            }
            String[] medicineHistory = doc.getMedicineHistory().split(",", 4);
            List<MedicationDto> medicationDtos = new ArrayList<>();
            for (String history : medicineHistory) {
                String[] str = history.split(",");

                medicationDtos.add(new MedicationDto(str[0], str[1], str[2]));
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

        return new ControllerResult<List<HealthDocsDto>>().setRet_code(0).setRet_values(healthDocsDtos).setMessage("成功");*/
        return docses;

    }

}
