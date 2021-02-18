package com.bamboo.employee.multithreading;

import com.bamboo.employee.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class CronJobExecutor {

    private  final EmployeeService employeeService;

    @Scheduled(cron = "${cron.expression}")
    public void cronJob() {
        employeeService.calculateFavoriteMonthsForEmployees();
    }
}
