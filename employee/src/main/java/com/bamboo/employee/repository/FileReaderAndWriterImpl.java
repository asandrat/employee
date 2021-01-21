package com.bamboo.employee.repository;

import com.bamboo.employee.model.Employee;
import com.bamboo.employee.model.Vacation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

    final static Logger logger =
            LogManager.getLogger(FileReaderAndWriter.class.getName());

    @Override
    public Map<String, Employee> findAllEmployees() {
        //read from file
        Map<String, Employee> map = new HashMap<>();
        try (ObjectInputStream objectInputStream =
                     createObjectInputStream(fileNameEmployees)) {

            if (objectInputStream == null) {
                return map;
            }

            map = readEmployeesFromFile(objectInputStream);

        } catch (EOFException eofException) {
            logger.fatal("Can't read employees from file", eofException);
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("File employees.txt not found.");
        }
        return map;
    }

    @Override
    public Map<String, Vacation> findAllVacations() {
        Map<String, Vacation> map = new HashMap<>();
        try (ObjectInputStream objectInputStream =
                     createObjectInputStream(fileNameVacations)) {

            if (objectInputStream == null) {
                return map;
            }

            map = readVacationsFromFile(objectInputStream);

        } catch (EOFException eofException) {
            logger.fatal("Can't read vacations from file", eofException);
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("File vacations.txt not found.");
        }
        return map;
    }

    private Map<String, Vacation> readVacationsFromFile
            (ObjectInputStream objectInputStream)
            throws IOException, ClassNotFoundException {

        Map<String, Vacation> resultMap = new HashMap<>();
        Vacation vacation = (Vacation) objectInputStream.readObject();
        while (vacation != null) {
            resultMap.put(vacation.getId(), vacation);
            vacation = (Vacation) objectInputStream.readObject();
        }
        return resultMap;
    }

    private Map<String, Employee> readEmployeesFromFile
            (ObjectInputStream objectInputStream)
            throws IOException, ClassNotFoundException {

        Map<String, Employee> resultMap = new HashMap<>();
        Employee employee = (Employee) objectInputStream.readObject();
        while (employee != null) {
            resultMap.put(employee.getId(), employee);
            employee = (Employee) objectInputStream.readObject();
        }
        return resultMap;
    }

    @Override
    public void saveAllEmployees(Map<String, Employee> employeeMap) {
        //write to file
        try (ObjectOutputStream objectOutputStream =
                     createObjectOutputStream(fileNameEmployees)) {

            writeEmployeesToFile(employeeMap, objectOutputStream);

        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("Can't write to file " + fileNameEmployees);
        }
    }

    @Override
    public void saveAllVacations(Map<String, Vacation> vacationMap) {
        try (ObjectOutputStream objectOutputStream =
                     createObjectOutputStream(fileNameVacations)) {

            writeVacationsToFile(vacationMap, objectOutputStream);

        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("Can't write to file " + fileNameVacations);
        }
    }

    public void writeEmployeesToFile(Map<String, Employee> employeeMap,
                                     ObjectOutputStream objectOutputStream) throws IOException {
        for (String id : employeeMap.keySet()) {
            objectOutputStream.writeObject(employeeMap.get(id));
        }
        objectOutputStream.writeObject(null);
        objectOutputStream.flush();
    }

    public void writeVacationsToFile(Map<String, Vacation> vacationMap,
                                     ObjectOutputStream objectOutputStream) throws IOException {
        for (String id : vacationMap.keySet()) {
            objectOutputStream.writeObject(vacationMap.get(id));
        }
        objectOutputStream.writeObject(null);
        objectOutputStream.flush();
    }


    @Override
    public boolean isFileEmpty(File file) {
        return file.length() == 0;
    }

    private ObjectInputStream createObjectInputStream(String fileName)
            throws IOException {

        if (isFileEmpty(new File(fileName))) {
            System.out.println(fileName + " is empty");
            return null;
        }

        FileInputStream fileInputStream = new FileInputStream(fileName);
        return new ObjectInputStream(fileInputStream);
    }

    private ObjectOutputStream createObjectOutputStream(String fileName)
            throws IOException {

        FileOutputStream fileOutputStream = new FileOutputStream(fileName);
        return new ObjectOutputStream(fileOutputStream);
    }
}
