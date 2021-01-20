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

            Employee employee = (Employee) objectInputStream.readObject();
            while (employee != null) {
                map.put(employee.getId(), employee);
                employee = (Employee) objectInputStream.readObject();

            }
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

            Vacation vacation = (Vacation) objectInputStream.readObject();
            while (vacation != null) {
                map.put(vacation.getId(), vacation);
                vacation = (Vacation) objectInputStream.readObject();
            }

        } catch (EOFException eofException) {
            logger.fatal("Can't read vacations from file", eofException);
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("File vacations.txt not found.");
        }
        return map;
    }

    @Override
    public void saveAllEmployees(Map<String, Employee> employeeMap) {
        //write to file
        try (ObjectOutputStream objectOutputStream =
                     createObjectOutputStream(fileNameEmployees)) {

            for (String id : employeeMap.keySet()) {
                objectOutputStream.writeObject(employeeMap.get(id));
            }
            objectOutputStream.writeObject(null);
            objectOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveAllVacations(Map<String, Vacation> map) {
        try (ObjectOutputStream objectOutputStream =
                     createObjectOutputStream(fileNameVacations)) {

            for (String id : map.keySet()) {
                objectOutputStream.writeObject(map.get(id));
            }
            objectOutputStream.writeObject(null);
            objectOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean isFileEmpty(File file) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(file));
        return br.readLine() == null;
    }

    @Override
    public ObjectInputStream createObjectInputStream(String fileName)
            throws IOException {

        if (isFileEmpty(new File(fileName))) {
            System.out.println(fileName + " is empty");
            return null;
        }

        FileInputStream fileInputStream = new FileInputStream(fileName);
        return new ObjectInputStream(fileInputStream);
    }

    @Override
    public ObjectOutputStream createObjectOutputStream(String fileName)
            throws IOException {

        FileOutputStream fileOutputStream = new FileOutputStream(fileName);
        return new ObjectOutputStream(fileOutputStream);
    }
}
