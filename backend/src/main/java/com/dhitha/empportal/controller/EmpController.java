package com.dhitha.empportal.controller;

import com.dhitha.empportal.model.EmpRequest;
import com.dhitha.empportal.model.Employee;
import com.dhitha.empportal.service.EmpService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
public class EmpController {

  private final EmpService empService;

  @CrossOrigin(origins = "http://localhost:8080")
  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> getEmployees(EmpRequest empRequest){
    return ResponseEntity.ok(empService.getAllEmployee(empRequest));
  }
}
