package com.bamboo.employee.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SupportedParametersTest {


    @Test
    void isSupportedShouldReturnFalseWhenBadParams() {
        Assertions.assertFalse(SupportedParameters.isSupported(null));
        Assertions.assertFalse(SupportedParameters.isSupported(""));
        Assertions.assertFalse(SupportedParameters.isSupported(" "));
        Assertions.assertFalse(SupportedParameters.isSupported("x"));
        Assertions.assertFalse(SupportedParameters.isSupported("NAME "));
        Assertions.assertFalse(SupportedParameters.isSupported(" NAME"));
        Assertions.assertFalse(SupportedParameters.isSupported(" NAME "));
    }

    @Test
    void isSupportedShouldReturnTrueForGoodParams() {
        Assertions.assertTrue(SupportedParameters.isSupported("NAME"));
        Assertions.assertTrue(SupportedParameters.isSupported("SURNAME"));
        Assertions.assertTrue(SupportedParameters.isSupported("UNIQUEID"));
        Assertions.assertTrue(SupportedParameters.isSupported(
                "EMPLOYEEUNIQUEID"));
        Assertions.assertTrue(SupportedParameters.isSupported("FROM"));
        Assertions.assertTrue(SupportedParameters.isSupported("TO"));
        Assertions.assertTrue(SupportedParameters.isSupported("STATUS"));
        Assertions.assertTrue(SupportedParameters.isSupported("DURATION"));

        Assertions.assertTrue(SupportedParameters.isSupported("name"));
        Assertions.assertTrue(SupportedParameters.isSupported("surname"));
        Assertions.assertTrue(SupportedParameters.isSupported("uniqueiD"));
        Assertions.assertTrue(SupportedParameters.isSupported(
                "employeeuniqueid"));
        Assertions.assertTrue(SupportedParameters.isSupported("from"));
        Assertions.assertTrue(SupportedParameters.isSupported("to"));
        Assertions.assertTrue(SupportedParameters.isSupported("status"));
        Assertions.assertTrue(SupportedParameters.isSupported("duration"));

        Assertions.assertTrue(SupportedParameters.isSupported("To"));
        Assertions.assertTrue(SupportedParameters.isSupported("sTatUs"));
        Assertions.assertTrue(SupportedParameters.isSupported("dUration"));
    }
}
