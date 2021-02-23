package com.bamboo.employee;

import com.bamboo.employee.model.EmployeeDTO;
import com.bamboo.employee.service.employee.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class ScheduledJobExecutor {
    @Autowired
    EmployeeService employeeService;

    @Value("${limit.number.of.employees}")
    private String limitNumberOfEmployees;

    private static final Logger logger = LoggerFactory.getLogger(ScheduledJobExecutor.class);

    //@Scheduled(cron = "${cron.expression}")
    @Scheduled(fixedDelay = 5000, initialDelay = 1000)
    public void scheduleCronJob() {
        ExecutorService executorService =
                Executors.newFixedThreadPool(Integer.parseInt(limitNumberOfEmployees));
        Collection<EmployeeDTO> firstNEmployees =
                employeeService.findFirstNRegisteredEmployees(Integer.parseInt(limitNumberOfEmployees));

        if (firstNEmployees == null || firstNEmployees.size() == 0) {
            return;
        }
        ArrayList<Integer> months = new ArrayList<>();

        for (EmployeeDTO employee : firstNEmployees) {
            Future<Integer> integerFuture = executorService.submit(
                    new EmployeeFavouriteMonthCalculator(employee, employeeService));
            try {
                months.add(integerFuture.get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }

        }

        Set<Integer> set = new HashSet<>();
        List<Integer> commonMonths =
                months.stream().filter(e -> !set.add(e)).collect(Collectors.toList());
        if (commonMonths.size() == 0) {
            logger.info("\nNo common months found.");
            return;
        }
        logger.info("\nCommon favourite months: ");

        commonMonths.stream().distinct().collect(Collectors.toList()).forEach(System.out::println);
    }

}
