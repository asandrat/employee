package com.bamboo.employee;

import com.bamboo.employee.model.EmployeeDTO;
import com.bamboo.employee.model.VacationDTO;
import com.bamboo.employee.service.employee.EmployeeService;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

public class EmployeeFavouriteMonthCalculator implements Callable<Integer> {
    private final EmployeeDTO employeeDTO;
    private final EmployeeService employeeService;

    public EmployeeFavouriteMonthCalculator(final EmployeeDTO employeeDTO,
                                            final EmployeeService employeeService) {
        this.employeeDTO = employeeDTO;
        this.employeeService = employeeService;
    }

    @Override
    public Integer call() {
        int favouriteMonth;
        favouriteMonth = findFavouriteMonthOfEmployee();
        if (favouriteMonth == 0) {
            System.out.println("No vacations found.");
        }
        employeeService.saveFavouriteMonth(employeeDTO, favouriteMonth);
        return favouriteMonth;
    }

    private int findFavouriteMonthOfEmployee() {
        Collection<VacationDTO> vacationsOfEmployee =
                employeeService.findAllVacationsOfEmployee(employeeDTO.getId());
        if (vacationsOfEmployee.size() == 0) {
            return 0;
        }

        List<String> vacationDates =
                extractDatesFromVacations(vacationsOfEmployee);

        List<Integer> monthsOfVacations =
                extractMonthsFromVacations(vacationDates);

        return calculateFavouriteMonth(monthsOfVacations);
    }

    private List<String> extractDatesFromVacations(Collection<VacationDTO> vacationsOfEmployee) {
        List<String> dateList = vacationsOfEmployee
                .stream()
                .map(VacationDTO::getDateFrom)
                .collect(Collectors.toList());

        dateList.addAll(vacationsOfEmployee
                .stream()
                .map(VacationDTO::getDateTo)
                .collect(Collectors.toList()));
        return dateList;
    }

    private List<Integer> extractMonthsFromVacations(List<String> vacationDates) {
        return vacationDates
                .stream()
                .map(date -> date.substring(5, 7))
                .map(month -> {
                    if (month.startsWith("0")) {
                        return month.substring(1);
                    } else {
                        return month;
                    }
                }).map(Integer::parseInt)
                .collect(Collectors.toList());
    }

    private int calculateFavouriteMonth(List<Integer> monthsOfVacations) {
        Map<Integer, Long> monthOccurrences = monthsOfVacations
                .stream()
                .collect(Collectors.groupingBy(month -> month,
                        Collectors.counting()));

        return Collections
                .max(monthOccurrences.entrySet(), (entry1, entry2)
                        -> (int) (entry1.getValue() - entry2.getValue()))
                .getKey();
    }

}
