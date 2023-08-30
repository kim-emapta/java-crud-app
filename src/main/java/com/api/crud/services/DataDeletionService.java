package com.api.crud.services;

import com.api.crud.repository.CrudRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DataDeletionService {

    private final CrudRepository crudRepository;

    @Autowired
    public DataDeletionService(CrudRepository crudRepository) {
        this.crudRepository = crudRepository;
    }

    public boolean deleteEntry(Long id) {
        if (crudRepository.existsById(id)) {
            crudRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
}