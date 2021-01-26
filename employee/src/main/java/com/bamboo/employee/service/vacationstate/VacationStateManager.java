package com.bamboo.employee.service.vacationstate;

import com.bamboo.employee.exceptions.InvalidStateTransitionException;
import com.bamboo.employee.model.VacationStatus;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Component
public class VacationStateManager {

    private class StateTransitionPair {
        VacationStatus currentState;
        VacationStatus input;

        public StateTransitionPair(final VacationStatus currentState,
                                   final VacationStatus input) {
            this.currentState = currentState;
            this.input = input;
        }

        @Override
        public boolean equals(final Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            StateTransitionPair that = (StateTransitionPair) o;
            return currentState == that.currentState && input == that.input;
        }

        @Override
        public int hashCode() {
            return Objects.hash(currentState, input);
        }
    }

    private final Map<StateTransitionPair, VacationStatus> STATE_MACHINE;

    public VacationStateManager(){
        STATE_MACHINE = new HashMap<>();

        STATE_MACHINE.put(
                new StateTransitionPair(VacationStatus.SUBMITTED,
                        VacationStatus.APPROVED), VacationStatus.APPROVED);

        STATE_MACHINE.put(new StateTransitionPair(VacationStatus.SUBMITTED,
                VacationStatus.REJECTED), VacationStatus.REJECTED);
    }

    public VacationStatus getValidStatus(VacationStatus current,
                                         VacationStatus input) throws InvalidStateTransitionException {
        StateTransitionPair s = new StateTransitionPair(current, input);
        VacationStatus result = STATE_MACHINE.get(s);
        if (result == null) {
            throw new InvalidStateTransitionException(current, input);
        }
        return result;
    }


}
