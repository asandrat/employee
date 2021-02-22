package com.bamboo.employee;

import com.bamboo.employee.model.Employee;
import com.bamboo.employee.service.employee.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

@Component
public class ScheduledTasks {

    private Timestamp timestamp = new Timestamp(0);

    @Value("${maxNumberOfEmployeesPerJob}")
    private final int maxNumberOfEmployeesPerJob;

    private static final Logger log =
            LoggerFactory.getLogger(ScheduledTasks.class);

    private final EmployeeService service;

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat(
            "dd-MM-yyyy HH:mm:ss");

    public ScheduledTasks(
            @Value("${maxNumberOfEmployeesPerJob}") final int maxNumberOfEmployeesPerJob,
            final EmployeeService service) {
        this.maxNumberOfEmployeesPerJob = maxNumberOfEmployeesPerJob;
        this.service = service;
    }

    @Scheduled(cron = "${cronForScheduledTask}")
    public void vacationStatistics() {
        // READ EMPLOYEES
        List<Employee> employees =
                service.findFirstNEmployeesByTimestamp(
                        maxNumberOfEmployeesPerJob,
                        timestamp);

        // MAKE THREAD POOL
        ExecutorService executorService =
                Executors.newFixedThreadPool(maxNumberOfEmployeesPerJob);


        // CREATE TASKS
        Collection<EmployeeFavoriteVacationCalculator> tasks = new ArrayList<>();
        for (Employee employee : employees) {
            tasks.add(new EmployeeFavoriteVacationCalculator(employee, service));

            updateTimestamp(employee.getCreationTime());
        }

        try {
            // EXECUTE ALL TASKS
            List<Future<List<Integer>>> futures =
                    executorService.invokeAll(tasks);
            List<Integer> favoriteMonths = new ArrayList<>();
            for (Future<List<Integer>> future : futures) {
                favoriteMonths.addAll(future.get());
            }


            // FIND MOST OCCURRING NUMBER OF VACATIONS
            int commonFavoriteVacationMonth = getPopularMonth(favoriteMonths);
            // LOG FAVORITE MONTH
            logFavoriteMonth(
                    commonFavoriteVacationMonth,
                    employees.stream()
                            .map(Employee::getUniqueId)
                            .collect(Collectors.toList()));

        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        } finally {
            executorService.shutdown();
        }

        if (employees.size() < maxNumberOfEmployeesPerJob) {
            resetTimestamp();
        }
    }

    private void logFavoriteMonth(final int commonFavoriteVacationMonth,
                                  List<Integer> employeesIds) {
        if (commonFavoriteVacationMonth == -1
                || commonFavoriteVacationMonth == 0) {
            log.info(String.format("No common favorite month on %s for " +
                            "employees with ids %s",
                    dateFormat.format(new Date()), employeesIds));
        } else {
            log.info(String.format(
                    "Favorite month on %s for employees with ids %s is %d",
                    dateFormat.format(new Date()),
                    employeesIds,
                    commonFavoriteVacationMonth));
        }
    }

    private int getPopularMonth(final List<Integer> favoriteMonths)
    {
        int popularMonth = -1;
        int numberOfOccurencesOfPopularMonth = 1;
        Map<Integer, Integer> occurences = new HashMap<>();
        for (Integer month : favoriteMonths) {
            if (occurences.containsKey(month)) {
                int monthOccurences = occurences.get(month) + 1;
                occurences.put(month, monthOccurences);
                if (monthOccurences > numberOfOccurencesOfPopularMonth) {
                    numberOfOccurencesOfPopularMonth = monthOccurences;
                    popularMonth = month;
                }
            } else {
                occurences.put(month, 1);
            }
        }
        return popularMonth;
    }

    private void updateTimestamp(final Timestamp potentialNewTimestamp) {
        timestamp = potentialNewTimestamp.after(timestamp)
                ? potentialNewTimestamp
                : timestamp;
    }

    private void resetTimestamp() {
        this.timestamp = new Timestamp(0);
    }
}
