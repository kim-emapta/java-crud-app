package com.api.crud.services;

import com.api.crud.model.CrudModel;
import com.api.crud.repository.CrudRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DataFetchingService {
    private final CrudRepository crudRepository;

    @Autowired
    public DataFetchingService(CrudRepository crudRepository){
        this.crudRepository = crudRepository;
    }
    @Transactional
    public Optional<CrudModel> fetchEntry(Long id) {
        return crudRepository.findById(id);
    }
    @Transactional
    public List<CrudModel> fetchAllEntries() {
        return (List<CrudModel>) crudRepository.findAll();
    }
}
