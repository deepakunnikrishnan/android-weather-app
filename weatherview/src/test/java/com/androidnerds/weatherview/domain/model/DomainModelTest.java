package com.androidnerds.weatherview.domain.model;

import org.junit.Test;

import pl.pojo.tester.api.assertion.Assertions;
import pl.pojo.tester.api.assertion.Method;

public class DomainModelTest {


    @Test
    public void testDayForecast() {
        final Class<?> classUnderTest = DayForecast.class;
        Assertions.assertPojoMethodsFor(classUnderTest)
                .testing(Method.GETTER, Method.SETTER, Method.CONSTRUCTOR)
                .areWellImplemented();
    }

    @Test
    public void testLocationInfo() {
        final Class<?> classUnderTest = LocationInfo.class;
        Assertions.assertPojoMethodsFor(classUnderTest)
                .testing(Method.GETTER, Method.SETTER, Method.CONSTRUCTOR)
                .areWellImplemented();
    }

    @Test
    public void testWeatherInfo() {
        final Class<?> classUnderTest = WeatherInfo.class;
        Assertions.assertPojoMethodsFor(classUnderTest)
                .testing(Method.GETTER, Method.SETTER, Method.CONSTRUCTOR)
                .areWellImplemented();
    }

}