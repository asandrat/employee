package com.bamboo.employee;

import com.bamboo.employee.model.Employee;
import com.bamboo.employee.model.FavoriteVacation;
import com.bamboo.employee.model.Vacation;
import com.bamboo.employee.model.VacationStatus;
import com.bamboo.employee.service.employee.EmployeeService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class EmployeeFavoriteVacationCalculator implements Callable<List<Integer>> {

    private final Employee employee;
    private final EmployeeService service;
    private static final int NUMBER_OF_MONTHS = 12;

    public EmployeeFavoriteVacationCalculator(final Employee employee,
                                              final EmployeeService service) {
        this.employee = employee;
        this.service = service;
    }

    @Override
    public List<Integer> call() {

        Collection<Vacation> approvedEmployeesVacations =
                getApprovedEmployeesVacations();

        int[] monthValues = calculateMonthValuesOccurrences(approvedEmployeesVacations);
        List<Integer> favoriteMonths = findFavoriteMonths(monthValues);

        if (favoriteMonths.size() != 0) {
            // remove existing favorite vacations
            service.removeEmployeesFavoriteVacations(employee.getUniqueId());

            // persist employees favorite vacations
            favoriteMonths.forEach(favoriteMonth -> {
                FavoriteVacation favoriteVacation = new FavoriteVacation(
                        employee.getUniqueId(),
                        favoriteMonth);
                service.createEmployeesFavoriteVacation(favoriteVacation);
            });

        }
        return favoriteMonths;
    }

    private Collection<Vacation> getApprovedEmployeesVacations() {
        return service
                .findAllEmployeesVacations(employee.getUniqueId())
                .stream()
                .filter(vacation ->
                        vacation.getStatus().equals(VacationStatus.APPROVED))
                .collect(Collectors.toList());

    }

    private int[] calculateMonthValuesOccurrences(
            final Collection<Vacation> approvedEmployeesVacations) {

        int[] monthValuesOccurrences = new int[NUMBER_OF_MONTHS];
        for (Vacation vacation : approvedEmployeesVacations) {
            monthValuesOccurrences[vacation.getFrom().getMonthValue() - 1] += 1;
            monthValuesOccurrences[vacation.getTo().getMonthValue() - 1] += 1;
        }
        return monthValuesOccurrences;
    }

    private List<Integer> findFavoriteMonths(final int[] monthValuesOccurrences) {
        int max = Arrays.stream(monthValuesOccurrences)
                .max()
                .orElse(0);

        // employee has no vacations
        if (max == 0) {
            return new ArrayList<>();
        }

        return IntStream.rangeClosed(1, NUMBER_OF_MONTHS)
                .filter(index -> monthValuesOccurrences[index - 1] == max)
                .boxed()
                .collect(Collectors.toList());
    }
}
