package com.bamboo.employee.service.validationstrategy;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.doThrow;


@ExtendWith(MockitoExtension.class)
class ValidatorTest {

    @Mock
    private Map<String, ValidationStrategy> strategies;

    @InjectMocks
    private Validator validator;

    @Test
    void validEmployeeAdditionShouldCallAddEmpValidator() {
    }
}
