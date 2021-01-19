package com.bamboo.employee.repository;

import com.bamboo.employee.model.Employee;
import com.bamboo.employee.model.Vacation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

@Component
public class FileReaderAndWriterImpl implements FileReaderAndWriter {
    @Value("${spring.file.name.employees}")
    private String fileNameEmployees;

    @Value("${spring.file.name.vacations}")
    private String fileNameVacations;

    @Override
    public Map<String, Employee> findAllEmployees() {
        //read from file
        Map<String, Employee> map = new HashMap<>();
        try {
            FileInputStream fileInputStream =
                    new FileInputStream(fileNameEmployees);
            if (isFileEmpty(new File(fileNameEmployees))) {
                System.out.println("employees.txt is empty");
                return map;
            }
            ObjectInputStream objectInputStream =
                    new ObjectInputStream(fileInputStream);
            while (true) {
                Employee employee = (Employee) objectInputStream.readObject();
                if (employee == null) {
                    break;
                } else {
                    map.put(employee.getId(), employee);
                }
            }
        } catch (EOFException eofException) {
            eofException.printStackTrace();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("Cannot read employees file");
        }
        return map;
    }

    @Override
    public void saveAllEmployees(Map<String, Employee> employeeMap) {
        //write to file
        try {
            FileOutputStream fileOutputStream =
                    new FileOutputStream(fileNameEmployees);
            ObjectOutputStream objectOutputStream =
                    new ObjectOutputStream(fileOutputStream);
            for (String id : employeeMap.keySet()) {
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
    public Map<String, Vacation> findAllVacations() {
        Map<String, Vacation> map = new HashMap<>();
        try {
            FileInputStream fileInputStream =
                    new FileInputStream(fileNameVacations);
            if (isFileEmpty(new File(fileNameVacations))) {
                System.out.println("vacations.txt is empty");
                return map;
            }
            ObjectInputStream objectInputStream =
                    new ObjectInputStream(fileInputStream);
            while (true) {
                Vacation vacation = (Vacation) objectInputStream.readObject();
                if (vacation == null) {
                    break;
                } else {
                    map.put(vacation.getId(), vacation);
                }
            }
        } catch (EOFException eofException) {
            eofException.printStackTrace();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("Cannot read vacations file");
        }
        return map;
    }

    @Override
    public void saveAllVacations(Map<String, Vacation> map) {
        try {
            FileOutputStream fileOutputStream =
                    new FileOutputStream(fileNameVacations);
            ObjectOutputStream objectOutputStream =
                    new ObjectOutputStream(fileOutputStream);
            for (String id : map.keySet()) {
                objectOutputStream.writeObject(map.get(id));
            }
            objectOutputStream.writeObject(null);
            objectOutputStream.flush();
            objectOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean isFileEmpty(File file) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(file));
        return br.readLine() == null;
    }
}
