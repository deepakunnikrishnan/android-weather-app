package com.androidnerds.weatherview.testutils;


import android.text.TextUtils;

import com.androidnerds.weatherview.data.remote.ApiConstants;

import org.junit.Test;

import static org.junit.Assert.*;

public class MockResponseFileReaderTest {

    @Test
    public void testReadFile() {
        MockResponseFileReader fileReader = new MockResponseFileReader(ApiConstants.LOCATION_SEARCH);
        assertFalse(TextUtils.isEmpty(fileReader.getContent()));
    }
}