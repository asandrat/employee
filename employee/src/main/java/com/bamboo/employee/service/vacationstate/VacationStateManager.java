package com.bamboo.employee.service.vacationstate;

import com.bamboo.employee.exceptions.InvalidStateTransitionException;
import com.bamboo.employee.model.VacationStatus;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class VacationStateManager {

    private final Map<VacationStatus, Set<VacationStatus>> STATE_MACHINE;

    public VacationStateManager(){
        STATE_MACHINE = new HashMap<>();

        Set<VacationStatus> validStatesForSubmitted = new HashSet<>();
        validStatesForSubmitted.add(VacationStatus.APPROVED);
        validStatesForSubmitted.add(VacationStatus.REJECTED);
        STATE_MACHINE.put(VacationStatus.SUBMITTED, validStatesForSubmitted);
    }

    public VacationStatus getValidStatus(VacationStatus current,
                                         VacationStatus target) {
        Set<VacationStatus> validStates = STATE_MACHINE.get(current);
        if (validStates != null && validStates.contains(target)) {
            return target;
        }
        throw new InvalidStateTransitionException(current, target);
    }
}
