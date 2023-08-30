package com.api.crud.services;

import com.api.crud.model.CrudModel;
import com.api.crud.repository.CrudRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DataCreationService {

    private final CrudRepository crudRepository;

    @Autowired
    public DataCreationService(CrudRepository crudRepository){
        this.crudRepository = crudRepository;
    }

    @Transactional
    public Long createEntry(String name, String email, String mobile) {

        CrudModel entity = new CrudModel();
        Long savedId = null;

        try {
            entity.setName(name);
            entity.setEmail(email);
            entity.setMobile(mobile);

            CrudModel savedEntity = crudRepository.save(entity);
            savedId = savedEntity.getId();

        } catch (Exception e) {
            System.out.println(e);
        }

        return savedId;
    }

}
