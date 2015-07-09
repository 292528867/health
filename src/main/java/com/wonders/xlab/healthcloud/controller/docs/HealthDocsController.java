package com.wonders.xlab.healthcloud.controller.docs;

import com.wonders.xlab.healthcloud.dto.docs.DiagnosisDto;
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
        for (HealthDocs doc : docses) {
            String[] diagnosisHistory = doc.getDiagnosisHistory().split(",", 4);
            List<DiagnosisDto> diagnosis = new ArrayList<>();
            for (String history : diagnosisHistory) {
                String[] str = history.split(",");

                diagnosis.add(new DiagnosisDto(str[0], str[1], str[2], str[4]));
            }


        }

        return null;

    }

}
