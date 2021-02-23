package com.bamboo.employee.multithreading;

import com.bamboo.employee.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class CronJobExecutor {

    private final EmployeeService employeeService;
    @Value("${number.of.employees.to.fetch}")
    private int numberOfEmployeesToFetchPerExecution;

    @Scheduled(cron = "${cron.expression}")
    public void cronJob() {
        employeeService.calculateFavoriteMonthsForEmployees(
                numberOfEmployeesToFetchPerExecution
        );
    }
}
