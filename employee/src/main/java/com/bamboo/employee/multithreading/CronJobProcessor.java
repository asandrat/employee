package com.bamboo.employee.multithreading;

import com.bamboo.employee.entity.Employee;
import com.bamboo.employee.entity.Vacation;
import com.bamboo.employee.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;


@Slf4j
@RequiredArgsConstructor
public class CronJobProcessor implements Callable<List<Integer>> {

    private final EmployeeService employeeService;

    private final Employee employee;

    @Override
    public List<Integer> call() {
        long start = System.currentTimeMillis();

        List<Integer> favoriteMonths = findFavouriteMonth(employee.getVacations());

        log.info(Thread.currentThread().getName() + " Employee with id "
                + employee.getUniqueId() + " has favorite month " + favoriteMonths);

        employeeService.saveFavoriteMonth(employee, favoriteMonths);

        log.info(Thread.currentThread().getName() + " executes task in " +
                (System.currentTimeMillis() - start));

        return favoriteMonths;
    }

    public List<Integer> findFavouriteMonth(List<Vacation> vacations) {
        Map<Integer, Integer> monthOccurrenceMap = new HashMap<>();

        for (Vacation vacation : vacations) {
            int monthFrom = vacation.getDateFrom().getMonthValue();
            int monthTo = vacation.getDateTo().getMonthValue();
            monthOccurrenceMap.put(monthFrom, monthOccurrenceMap.getOrDefault(
                    monthFrom, 0) + 2);
            if (monthFrom != monthTo) {
                monthOccurrenceMap.put(monthTo, monthOccurrenceMap.getOrDefault(
                        monthTo, 0) + 1);
                monthOccurrenceMap.put(monthFrom,
                        monthOccurrenceMap.get(monthFrom) - 1);
            }
        }

        return monthOccurrenceMap.values().stream()
                .max(Integer::compareTo)
                .map(maxValue -> monthOccurrenceMap.entrySet().stream()
                        .filter(entry -> maxValue.equals(entry.getValue()))
                        .map(Map.Entry::getKey)
                        .collect(Collectors.toList()))
                .orElse(Collections.emptyList());
    }
}
