package com.bamboo.employee.model.vacation.state;

import com.bamboo.employee.model.Vacation;

public class Approved implements VacationState {
    Vacation vacation;

    public Approved(Vacation vacation) {
        this.vacation = vacation;
    }

    public Approved() { }

    @Override
    public void rejectRequest() {
        System.out.println(
                "You can't reject the request which has been already approved"
        );
    }

    @Override
    public void approveRequest() {
        System.out.println(
                "You have already approve this request"
        );
    }
}
