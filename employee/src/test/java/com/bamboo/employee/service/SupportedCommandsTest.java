package com.bamboo.employee.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SupportedCommandsTest {
    @Test
    void isSupportedCommandShouldReturnFalseForBadCmds() {
        Assertions.assertFalse(SupportedCommands.isSupportedCommand(null));
        Assertions.assertFalse(SupportedCommands.isSupportedCommand(""));
        Assertions.assertFalse(SupportedCommands.isSupportedCommand(" "));
        Assertions.assertFalse(SupportedCommands.isSupportedCommand(" " +
                "employee_addition "));
        Assertions.assertFalse(SupportedCommands.isSupportedCommand("asd1"));
        Assertions.assertFalse(SupportedCommands.isSupportedCommand("1"));
    }

    @Test
    void isSupportedShouldReturnTrueForGoodCmds() {
        Assertions.assertTrue(SupportedCommands.isSupportedCommand("EMPLOYEE_ADDITION"));
        Assertions.assertTrue(SupportedCommands.isSupportedCommand(
                "EMPLOYEE_REMOVAL"));
        Assertions.assertTrue(SupportedCommands.isSupportedCommand(
                "VACATION_ADDITION"));
        Assertions.assertTrue(SupportedCommands.isSupportedCommand(
                "VACATION_ADDITION"));
        Assertions.assertTrue(SupportedCommands.isSupportedCommand(
                "HELP"));
    }
}
