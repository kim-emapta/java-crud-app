package com.api.crud.repository;

import com.api.crud.model.CrudModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CrudRepository extends JpaRepository<CrudModel, Long> {

}
