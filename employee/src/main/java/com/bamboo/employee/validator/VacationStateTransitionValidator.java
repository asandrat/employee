package com.bamboo.employee.validator;

import com.bamboo.employee.custom.exception.InvalidStateTransitionException;
import com.bamboo.employee.entity.Vacation;
import com.bamboo.employee.entity.VacationStatus;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class VacationStateTransitionValidator {

    private static final Map<VacationStatus, List<VacationStatus>> VALID_TRANSITIONS;

    static {
        VALID_TRANSITIONS = new HashMap<>();
        VALID_TRANSITIONS.put(
                VacationStatus.SUBMITTED,
                Arrays.asList(
                        VacationStatus.APPROVED,
                        VacationStatus.REJECTED)
        );
        VALID_TRANSITIONS.put(
                VacationStatus.REJECTED,
                Collections.singletonList(
                        VacationStatus.APPROVED
                )
        );        VALID_TRANSITIONS.put(
                VacationStatus.APPROVED,
                Collections.emptyList()
        );
    }

    public void validateVacationTransitionStatus(
            Vacation vacation,
            VacationStatus newStatus
    ) {
        VacationStatus currentStatus = vacation.getVacationStatus();
        List<VacationStatus> validStates = VALID_TRANSITIONS.getOrDefault(
                currentStatus,
                Collections.emptyList()
        );
        if (!validStates.contains(newStatus)) {
            throw new InvalidStateTransitionException(
                    "Could not transfer from state " + currentStatus +
                            " to " + newStatus
            );
        }
    }
}
