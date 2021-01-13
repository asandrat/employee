package com.bamboo.employee.model.vacation.state;

import com.bamboo.employee.model.Vacation;
import com.bamboo.employee.model.VacationStatus;

public class Submitted implements VacationState {
    Vacation vacation;

    public Submitted() {
    }

    public Submitted(Vacation vacation) {
        this.vacation = vacation;
    }

    @Override
    public void rejectRequest() {
        System.out.println("You have successfully rejected the request");
        vacation.setVacationState(vacation.getRejectedState());
        vacation.setVacationStatus(VacationStatus.REJECTED);
    }

    @Override
    public void approveRequest() {
        System.out.println("You have successfully approved the request");
        vacation.setVacationState(vacation.getApprovedState());
        vacation.setVacationStatus(VacationStatus.APPROVED);
    }
}
