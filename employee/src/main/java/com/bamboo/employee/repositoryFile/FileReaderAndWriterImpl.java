package com.bamboo.employee.repositoryFile;
import com.bamboo.employee.entitiesFile.EmployeeFile;
import com.bamboo.employee.entitiesFile.VacationFile;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class FileReaderAndWriterImpl implements FileReaderAndWriter {
    @Value("${spring.file.name.employees}")
    private String fileNameEmployees;

    @Value("${spring.file.name.vacations}")
    private String fileNameVacations;

    final static Logger logger =
            LogManager.getLogger(FileReaderAndWriter.class.getName());

    @Override
    public Map<String, EmployeeFile> findAllEmployees() {
        Map<String, EmployeeFile> map;
        List<EmployeeFile> employeeList = findAll(fileNameEmployees);

        map = employeeList.stream().collect(Collectors.toMap(EmployeeFile::getId,
                employee -> employee));

        return map;
    }

    @Override
    public Map<String, VacationFile> findAllVacations() {
        Map<String, VacationFile> map;
        List<VacationFile> vacationList = findAll(fileNameVacations);

        map = vacationList.stream().collect(Collectors.toMap(VacationFile::getId,
                vacation -> vacation));

        return map;
    }

    private <T> List<T> findAll(String file) {
        List<T> list = new ArrayList<>();
        try (ObjectInputStream objectInputStream =
                     createObjectInputStream(file)) {
            if (objectInputStream == null) {
                return list;
            }
            list = readFromFile(objectInputStream);
        } catch (EOFException eofException) {
            logger.fatal(
                    "Can't read "
                            + file.substring(0, file.indexOf('.'))
                            + " from file", eofException);
        } catch (IOException e) {
            throw new RuntimeException("File " + file + " not found.");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("class not found exception");
        }
        return list;
    }

    private <T> List<T> readFromFile(ObjectInputStream objectInputStream)
            throws IOException, ClassNotFoundException {
        List<T> resultList = new ArrayList<>();
        T objectInFile = (T) objectInputStream.readObject();

        while (objectInFile != null) {
            resultList.add(objectInFile);
            objectInFile = (T) objectInputStream.readObject();
        }
        return resultList;
    }

    @Override
    public void saveAllEmployees(Map<String, EmployeeFile> employeeMap) {
        saveAll(employeeMap, fileNameEmployees);
    }

    @Override
    public void saveAllVacations(Map<String, VacationFile> vacationMap) {
        saveAll(vacationMap, fileNameVacations);
    }

    private <T> void saveAll(Map<String, T> map, String file) {
        try (ObjectOutputStream objectOutputStream =
                     createObjectOutputStream(file)) {

            writeToFile(map, objectOutputStream);

        } catch (IOException e) {
            throw new IllegalArgumentException("Can't write to file " + file);
        }
    }

    private <T> void writeToFile(Map<String, T> map,
                                 ObjectOutputStream objectOutputStream)
            throws IOException {
        for (String id : map.keySet()) {
            objectOutputStream.writeObject(map.get(id));
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
