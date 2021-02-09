package com.bamboo.employee.mapstruct;

import com.bamboo.employee.entity.Employee;
import com.bamboo.employee.model.EmployeeDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {

    @Mapping(source = "uniqueId", target = "id")
    EmployeeDTO employeeToEmployeeDTO(Employee employee);

    List<EmployeeDTO> map(List<Employee> employees);
}
