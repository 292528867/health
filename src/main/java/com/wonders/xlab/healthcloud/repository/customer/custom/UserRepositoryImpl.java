package com.wonders.xlab.healthcloud.repository.customer.custom;

import com.wonders.xlab.healthcloud.entity.docs.HealthDocs;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mars on 15/7/9.
 */
public class UserRepositoryImpl implements UserRepositoryCustom{

    @PersistenceContext
    private EntityManager entityManager;

    public List<HealthDocs> findHealthDocs() {

        StringBuilder builder = new StringBuilder();
        builder.append("SELECT name, gender, cardNumber, diagnosisHistory, medicineHistory FROM health_cloud.health_docs");

        Query query = entityManager.createNativeQuery(builder.toString());
        List<HealthDocs> healths = new ArrayList<>();
        List<Object[]> list = query.getResultList();
        for (Object[] objects : list) {
            HealthDocs docs = new HealthDocs();
            docs.setName(objects[0].toString());
            docs.setGender(objects[1].toString());
            docs.setCardNumber(objects[2].toString());
            docs.setDiagnosisHistory(objects[3].toString());
            docs.setMedicineHistory(objects[4].toString());
            healths.add(docs);
        }
        return healths;

    }
}
