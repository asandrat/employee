package com.bamboo.employee.exceptions;

import com.bamboo.employee.model.VacationStatus;

public class InvalidStateTransitionException extends RuntimeException {

    public InvalidStateTransitionException(final VacationStatus current,
                                           final VacationStatus input) {
        super("Failed to transition from " + current + " to " + input);
    }
}
