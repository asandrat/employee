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

        List<String> dateList = vacationsOfEmployee
                .stream()
                .map(VacationDTO::getDateFrom)
                .collect(Collectors.toList());

        dateList.addAll(vacationsOfEmployee
                .stream()
                .map(VacationDTO::getDateTo)
                .collect(Collectors.toList()));

        List<String> months = dateList
                .stream()
                .map(date -> date.substring(5, 7))
                .map(month -> {
                    if (month.startsWith("0")) {
                        return month.substring(1);
                    } else {
                        return month;
                    }
                })
                .collect(Collectors.toList());

        Map<String, Long> occurrences = months
                .stream()
                .collect(Collectors.groupingBy(w -> w, Collectors.counting()));

        String favouriteMonthString = Collections
                .max(occurrences.entrySet(),
                        (entry1, entry2) -> (int) (entry1.getValue() - entry2.getValue())).getKey();

        return Integer.parseInt(favouriteMonthString);
    }

}
