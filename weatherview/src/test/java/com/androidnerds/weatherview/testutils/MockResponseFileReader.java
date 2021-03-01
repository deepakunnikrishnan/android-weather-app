package com.androidnerds.weatherview.testutils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MockResponseFileReader {

    private String path;
    private String content;

    public MockResponseFileReader(String path) {
        this.path = path;
        try {
            init(this.path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void init(String path) throws IOException {
        InputStreamReader inputStreamReader = new InputStreamReader(this.getClass().getClassLoader().getResourceAsStream(path));
        BufferedReader r = new BufferedReader(inputStreamReader);
        StringBuilder total = new StringBuilder();
        for (String line; (line = r.readLine()) != null; ) {
            total.append(line).append('\n');
        }
        content = total.toString();
    }

    public String getContent() {
        return content;
    }
}
