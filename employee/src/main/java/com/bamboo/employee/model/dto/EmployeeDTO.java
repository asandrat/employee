package com.bamboo.employee.model.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class EmployeeDTO {

    @NotNull
    private String uniqueId;
    @NotNull
    @Size(min=2, max=80)
    private String name;
    @NotNull
    @Size(min=2, max=80)
    private String surname;

    public String getUniqueId() {
        return uniqueId;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public void setUniqueId(final String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void setSurname(final String surname) {
        this.surname = surname;
    }
}
