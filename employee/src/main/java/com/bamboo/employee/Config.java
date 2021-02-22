package com.bamboo.employee;

import com.bamboo.employee.model.EmployeeDTO;
import com.bamboo.employee.service.employee.EmployeeService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

@Configuration
@EnableScheduling
public class Config {
    @Autowired
    EmployeeService employeeService;

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Scheduled(fixedDelay = 5000, initialDelay = 1000)
    public void scheduleFixedDelayTask() {
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        Collection<EmployeeDTO> firstNEmployees = employeeService.findFirstN(3);
        if (firstNEmployees == null || firstNEmployees.size() == 0) {
            return;
        }
        ArrayList<Integer> months = new ArrayList<>();

        for (EmployeeDTO employee : firstNEmployees) {
            Future<Integer> integerFuture =
                    executorService.submit(
                            new FavouriteMonth(employee, employeeService));
            try {
                months.add(integerFuture.get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

        Set<Integer> set = new HashSet<>();
        List<Integer> commonMonths =
                months.stream().filter(e -> !set.add(e)).collect(Collectors.toList());
        if (commonMonths.size() == 0) {
            System.out.println("No common months found.");
            return;
        }
        System.out.println("Common favourite months:");

        commonMonths.stream().distinct().collect(Collectors.toList()).forEach(System.out::println);

    }

}
