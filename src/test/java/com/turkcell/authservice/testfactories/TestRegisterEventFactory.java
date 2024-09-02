package com.turkcell.authservice.testfactories;

import com.turkcell.pair3.core.events.RegisterEvent;

public class TestRegisterEventFactory {
    public static String defaultRegisterEventEmail = "user@example.com";
    public static String defaultRegisterEventPassword = "password";

    public static RegisterEvent defaultRegisterEvent() {
        return new RegisterEvent(
                TestRegisterEventFactory.defaultRegisterEventEmail, TestRegisterEventFactory.defaultRegisterEventPassword);
    }
}
