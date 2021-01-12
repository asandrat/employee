package com.bamboo.employee.repository;

import com.bamboo.employee.model.Employee;
import com.bamboo.employee.model.Vacation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.io.*;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class EmployeeRepositoryImpl implements EmployeeRepository {
    @Value("${spring.file.name.employees}")
    private String fileName;

    private Map<Integer, Employee> employeeMap;

    @PostConstruct
    public void init() {
        System.out.println("init: " + fileName);
        employeeMap = findAll();
    }


    @Override
    public Map<Integer, Employee> findAll() {
        //read from file
        Map<Integer, Employee> map = new HashMap<>();
        try {
            FileInputStream fileInputStream = new FileInputStream(fileName);
            ObjectInputStream objectInputStream =
                    new ObjectInputStream(fileInputStream);
            while (true) {
                Employee employee = (Employee) objectInputStream.readObject();
                if (employee == null) {
                    break;
                } else {
//                    employee.setVacations(Collections.singletonList(new
//                    Vacation(
//                            1, employee.getId(), LocalDate.of(2021, 1, 1),
//                            LocalDate.of(2021, 1, 7), 7,
//                            VacationStatus.fromString("SUBMITTED"))));
                    //System.out.println(employee.getName());
                    map.put(employee.getId(), employee);
                }
            }
        } catch (EOFException eofException) {
            eofException.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (WriteAbortedException writeAbortedException) {
            writeAbortedException.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }

    @Override
    public void saveAll(Map<Integer, Employee> employeeMap) {
        //write to file
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(fileName);
            ObjectOutputStream objectOutputStream =
                    new ObjectOutputStream(fileOutputStream);
            for (Integer id : employeeMap.keySet()) {
                objectOutputStream.writeObject(employeeMap.get(id));
            }
            objectOutputStream.writeObject(null);
            objectOutputStream.flush();
            objectOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addEmployee(Employee employee) {
        employeeMap.put(employee.getId(), employee);
        saveAll(employeeMap);
    }

    @Override
    public void removeEmployee(int id) {
        employeeMap.remove(id);
        saveAll(employeeMap);
    }

    @Override
    public void addVacationToEmployee(int employeeId, Vacation vacation) {
        Employee employee = findEmployee(employeeId);
        //employee.getVacations().add(vacation);
        if (employee == null) {
            return;
        }
        if (employee.getVacations().size() == 0) {
            employee.setVacations(Collections.singletonList(vacation));
        } else {
            employee.getVacations().add(vacation);
        }
        saveAll(employeeMap);
    }

    @Override
    public void removeVacation(int id, int employeeId) {
        Employee employee = findEmployee(id);
        if (employee == null) {
            return;
        }
        if (employee.getVacations().size() == 0) {
            return;
        }
        Vacation vacation = findVacation(id, employeeId);
        if (vacation == null) {
            return;
        }
        employee.getVacations().remove(vacation);
        saveAll(employeeMap);
    }

    @Override
    public Employee findEmployee(int id) {
        return employeeMap.get(id);
    }

    public Vacation findVacation(int id, int employeeId) {
        return employeeMap
                .get(employeeId)
                .getVacations()
                .stream()
                .filter(vacation -> vacation.getId() == id)
                .findFirst()
                .orElse(null);
    }

}
