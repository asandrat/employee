package com.bamboo.employee.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class VacationStatusTest {

    @Test
    void shouldReturnFalseIfStatusIsNotSupported() {
        Assertions.assertFalse(VacationStatus.isSupportedStatus("asd"));
        Assertions.assertFalse(VacationStatus.isSupportedStatus(""));
        Assertions.assertFalse(VacationStatus.isSupportedStatus(" "));
        Assertions.assertFalse(VacationStatus.isSupportedStatus("null"));
        Assertions.assertFalse(VacationStatus.isSupportedStatus(null));
    }
}
