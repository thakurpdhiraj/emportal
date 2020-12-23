package com.dhitha.empportal.model;

import java.io.Serializable;
import java.util.List;
import lombok.Data;

@Data
public class EmpResponse implements Serializable {
  private static final long serialVersionUID = 1L;

  private Long nextKey;
  private Long previousKey;
  private List<Employee> employees;
}
