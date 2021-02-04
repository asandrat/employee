package com.bamboo.employee.model.dto;

public class VacationUpdateDTO {
    private String uniqueId;
    private String status;

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(final String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(final String status) {
        this.status = status;
    }
}
