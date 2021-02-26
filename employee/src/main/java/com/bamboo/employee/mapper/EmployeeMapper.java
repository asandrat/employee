package com.bamboo.employee.mapper;


import com.bamboo.employee.entity.EmployeeEntity;
import com.bamboo.employee.model.Employee;
import com.bamboo.employee.model.dto.EmployeeDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {


    EmployeeDTO employeeToDTO(Employee employee);

    Employee EmployeeDTOtoEmployee(EmployeeDTO employeeDTO);

    @Mapping(target = "vacations", ignore = true)
    Employee entityToEmployee(EmployeeEntity employeeEntity);

    EmployeeEntity employeeToEntity(Employee employee);
}
