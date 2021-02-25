package com.bamboo.employee.mapstruct;

import com.bamboo.employee.entitiesDB.Employee;
import com.bamboo.employee.model.EmployeeDTO;
import org.mapstruct.Mapper;

import java.util.Collection;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {
    EmployeeDTO employeeEntityToEmployeeDTO(Employee employee);
    Employee employeeDTOToEmployeeEntity(EmployeeDTO employeeDTO);

    Collection<EmployeeDTO> map(Collection<Employee> employees);
}
