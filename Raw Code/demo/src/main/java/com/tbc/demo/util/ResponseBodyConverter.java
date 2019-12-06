package com.tbc.demo.util;

import java.io.BufferedReader;
import java.io.IOException;

public class ResponseBodyConverter {
    public static String convertResponseBody(BufferedReader in){
        if (in == null) return null;
        StringBuilder sb = new StringBuilder();

        String line = null;
        try {
            while ((line = in.readLine()) != null) {
                sb.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
}
