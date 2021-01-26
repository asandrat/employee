package com.bamboo.employee.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class EmployeeDTO {

    private String id;

    @NotBlank(message = "Name is required")
    @Size(min = 3, message = "Name must contains at least 3 letter")
    private String name;

    @NotBlank(message = "Surname is required")
    @Size(min = 3, message = "Surname must contains at least 3 letter")
    private String surname;

    public EmployeeDTO() {
    }

    public EmployeeDTO(String id, String name, String surname) {
        this.id = id;
        this.name = name;
        this.surname = surname;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }
}
