package com.dhitha.empportal.service;

import com.dhitha.empportal.model.EmpRequest;
import com.dhitha.empportal.model.EmpResponse;

public interface EmpService {
  EmpResponse getAllEmployee(EmpRequest empRequest);
}
