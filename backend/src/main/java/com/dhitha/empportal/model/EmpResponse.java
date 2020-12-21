package com.dhitha.empportal.model;

import java.util.List;
import lombok.Data;

@Data
public class EmpResponse {

  private Long nextKey;
  private Long previousKey;
  private List<Employee> employees;
}
