package com.bamboo.employee.repository;

import com.bamboo.employee.entity.Employee;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
class EmployeeRepositoryImplTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    ServletWebServerFactory servletWebServerFactory;

    @Test
    void findAllEmployeesTest() {
        List<Employee> employees = employeeRepository.findAll();
        assertThat(employees).isNotNull();
    }
}
