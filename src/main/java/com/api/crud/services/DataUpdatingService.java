package com.api.crud.services;

import com.api.crud.model.CrudModel;
import com.api.crud.repository.CrudRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DataUpdatingService {

    private final CrudRepository crudRepository;

    @Autowired
    public DataUpdatingService(CrudRepository crudRepository){
        this.crudRepository = crudRepository;
    }

    @Transactional
    public boolean updateEntry(Long id, CrudModel updatedModel) {
        Optional<CrudModel> existingModelOptional = crudRepository.findById(id);

        if (existingModelOptional.isPresent()) {
            CrudModel existingModel = existingModelOptional.get();
            existingModel.setName(updatedModel.getName());
            existingModel.setEmail(updatedModel.getEmail());
            existingModel.setMobile(updatedModel.getMobile());
            crudRepository.save(existingModel);
            return true;
        } else {
            return false;
        }
    }

}
