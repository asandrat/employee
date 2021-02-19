package com.bamboo.employee;

import com.bamboo.employee.model.Employee;
import com.bamboo.employee.model.FavoriteVacation;
import com.bamboo.employee.model.Vacation;
import com.bamboo.employee.model.VacationStatus;
import com.bamboo.employee.service.employee.EmployeeService;

import java.util.Collection;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

public class EmployeeFavoriteVacationCalculator implements Callable<Integer> {

    private final Employee employee;
    private final EmployeeService service;
    private static final int NUMBER_OF_MONTHS = 12;

    public EmployeeFavoriteVacationCalculator(final Employee employee,
                                              final EmployeeService service) {
        this.employee = employee;
        this.service = service;
    }

    @Override
    public Integer call() {

        Collection<Vacation> getApprovedEmployeesVacations =
                approvedEmployeesVacations();

        int[] monthValues = calculateMonthValuesOccurrences(getApprovedEmployeesVacations);
        // kada se prbaci u manyToOne vraca int[]
        int favoriteMonth = findFavoriteMonth(monthValues);

        if (favoriteMonth != 0) {
            FavoriteVacation favoriteVacation = new FavoriteVacation(
                    employee.getUniqueId(),
                    favoriteMonth);
            service.createEmployeesFavoriteVacation(favoriteVacation);
        }

        return favoriteMonth;
    }

    private Collection<Vacation> approvedEmployeesVacations() {
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

    private int findFavoriteMonth(final int[] monthValuesOccurrences) {
        int favoriteMonth = 0;
        int mostFrequentMonth = 0;
        for (int i = 0; i < NUMBER_OF_MONTHS; i++) {
            if (monthValuesOccurrences[i] > mostFrequentMonth) {
                mostFrequentMonth = monthValuesOccurrences[i];
                favoriteMonth = i + 1; // plus one because indices start w/ 0
            }
        }
        return favoriteMonth;
    }
}
