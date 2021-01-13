package com.bamboo.employee.model.vacation.state;

import com.bamboo.employee.model.Vacation;
import com.bamboo.employee.model.VacationStatus;

public class Rejected implements VacationState {
    Vacation vacation;

    public Rejected(Vacation vacation) {
        this.vacation = vacation;
    }

    public Rejected() { }

    @Override
    public void rejectRequest() {
        System.out.println(
                "You have already reject this request"
        );
    }

    @Override
    public void approveRequest() {
        System.out.println("You have successfully approved the request");
        vacation.setVacationState(vacation.getApprovedState());
        vacation.setVacationStatus(VacationStatus.APPROVED);
    }
}
