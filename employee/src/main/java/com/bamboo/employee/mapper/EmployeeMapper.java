package com.bamboo.employee.mapper;


import com.bamboo.employee.entity.EmployeeEntity;
import com.bamboo.employee.model.Employee;
import com.bamboo.employee.model.dto.EmployeeDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface EmployeeMapper {

    EmployeeMapper INSTANCE = Mappers.getMapper(EmployeeMapper.class);

    EmployeeDTO employeeToDTO(Employee employee);

    Employee EmployeeDTOtoEmployee(EmployeeDTO employeeDTO);

    Employee entityToEmployee(EmployeeEntity employeeEntity);

    EmployeeEntity employeeToEntity(Employee employee);
}
