package com.dhitha.empportal.model;

import java.time.LocalDate;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

@Data
public class EmpRequest {

  private String name;
  private String department;
  private String email;
  @DateTimeFormat(iso = ISO.DATE)
  private LocalDate toDate;
  @DateTimeFormat(iso = ISO.DATE)
  private LocalDate fromDate;
  private int pageKey;
  private int size;
}
