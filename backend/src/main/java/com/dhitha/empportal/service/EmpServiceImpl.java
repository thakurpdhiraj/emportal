package com.dhitha.empportal.service;

import com.dhitha.empportal.model.EmpRequest;
import com.dhitha.empportal.model.EmpResponse;
import com.dhitha.empportal.model.Employee;
import com.dhitha.empportal.repository.EmpRepository;
import com.dhitha.empportal.repository.EmpSpecification;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmpServiceImpl implements EmpService {

  private final EmpRepository repository;

  @Override
  public EmpResponse getAllEmployee(EmpRequest empRequest) {
    PageRequest pageRequest = PageRequest
        .of(empRequest.getPageKey(), empRequest.getSize(), Sort.sort(Employee.class).by(Employee::getJoining).descending());
    EmpSpecification empSpecification = new EmpSpecification(empRequest);
    Slice<Employee> page = repository.findAll(empSpecification, pageRequest);
    EmpResponse empResponse = new EmpResponse();
    if (!Objects.isNull(page)) {
      empResponse.setEmployees(page.getContent());
      if(page.hasNext()){
        empResponse.setNextKey(empRequest.getPageKey() + 1L);
      }
      if(page.hasPrevious()){
        empResponse.setPreviousKey(empRequest.getPageKey() - 1L);
      }
    }
    return empResponse;
  }
}
