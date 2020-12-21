package com.dhitha.empportal.repository;

import com.dhitha.empportal.model.EmpRequest;
import com.dhitha.empportal.model.Employee;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

@RequiredArgsConstructor
public class EmpSpecification implements Specification<Employee> {

  private final EmpRequest request;

  @Override
  public Predicate toPredicate(Root<Employee> root, CriteriaQuery<?> criteriaQuery,
      CriteriaBuilder criteriaBuilder) {
    System.out.println("EmpRequest: "+ request);
    List<Predicate> predicates = new ArrayList<>();
    if(!Objects.isNull(request.getName())){
      predicates.add(criteriaBuilder.like(root.get("name"), request.getName()));
    }
    if(!Objects.isNull(request.getEmail())){
      predicates.add(criteriaBuilder.like(root.get("email"), request.getEmail()));
    }
    if(!Objects.isNull(request.getDepartment())){
      predicates.add(criteriaBuilder.like(root.get("department"), request.getDepartment()));
    }
    if(!Objects.isNull(request.getFromDate())){
      if(!Objects.isNull(request.getToDate())){
        predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("joining"), request.getFromDate()));
        predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("joining"), request.getToDate()));
      }else{
        predicates.add(criteriaBuilder.equal(root.get("joining"), request.getFromDate()));
      }
    }
    return criteriaQuery.where(predicates.toArray(new Predicate[0])).getRestriction();
  }
}
