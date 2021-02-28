package com.androidnerds.weatherview.data.remote.dto;

import org.junit.Test;

import pl.pojo.tester.api.assertion.Assertions;
import pl.pojo.tester.api.assertion.Method;


public class DayForecastDtoTest {

    @Test
    public void testDayForecastDto() {
        final Class<?> classUnderTest = DayForecastDtoTest.class;
        Assertions.assertPojoMethodsFor(classUnderTest)
                .testing(Method.GETTER, Method.SETTER, Method.CONSTRUCTOR)
                .areWellImplemented();
    }

}