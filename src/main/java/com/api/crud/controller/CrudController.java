package com.api.crud.controller;

import com.api.crud.model.CrudModel;
import com.api.crud.repository.CrudRepository;
import com.api.crud.services.DataCreationService;
import com.api.crud.services.DataDeletionService;
import com.api.crud.services.DataFetchingService;
import com.api.crud.services.DataUpdatingService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api")
public class CrudController {

    private final CrudRepository crudRepository;
    private final ObjectMapper objectMapper;

    @Autowired
    public CrudController(CrudRepository crudRepository,
                          ObjectMapper objectMapper){
        this.crudRepository = crudRepository;
        this.objectMapper = objectMapper;
    }

    @GetMapping("/fetch")
    public ResponseEntity<?> handleGetRequest(@RequestParam(name = "id", required = false) Long id) {
        DataFetchingService dataFetchingService = new DataFetchingService(crudRepository);
        if (id != null) {
            Optional<CrudModel> optionalCrudModel = dataFetchingService.fetchEntry(id);
            if (optionalCrudModel.isPresent()) {
                CrudModel crudModel = optionalCrudModel.get();
                return ResponseEntity.ok(crudModel);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Record not found");
            }
        } else {
            List<CrudModel> crudModels = dataFetchingService.fetchAllEntries();
            return ResponseEntity.ok(crudModels);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<String> handlePostRequest(@RequestBody String requestBody) throws JsonProcessingException {
        try {
            DataCreationService dataCreationService = new  DataCreationService(crudRepository);
            JsonNode jsonNode = objectMapper.readTree(requestBody);
            String name = jsonNode.get("name").asText();
            String email = jsonNode.get("email").asText();
            String mobile = jsonNode.get("mobile").asText();

            Long savedId = dataCreationService.createEntry(name, email, mobile);

            return ResponseEntity.ok("Entry created with ID: " + savedId);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @PutMapping("/update")
    public ResponseEntity<String> handlePutRequest(@RequestParam(name = "id") Long id,
                                                   @RequestBody CrudModel requestBody) {
        try {
            DataUpdatingService dataUpdatingService = new DataUpdatingService(crudRepository);
            boolean updateSuccess = dataUpdatingService.updateEntry(id, requestBody);

            if (updateSuccess) {
                return ResponseEntity.ok("Record updated successfully.");
            } else {
                return ResponseEntity.notFound().build(); // Record not found
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error updating record: " + e.getMessage());
        }

    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> handleDeleteRequest(@RequestParam(name = "id") Long id) {
        DataDeletionService dataDeletionService = new DataDeletionService(crudRepository);

        boolean deleteSuccess = dataDeletionService.deleteEntry(id);

        if (deleteSuccess) {
            return ResponseEntity.ok("Record deleted successfully.");
        } else {
            return ResponseEntity.notFound().build(); // Record not found
        }
    }
}
