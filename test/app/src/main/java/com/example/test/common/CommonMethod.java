package com.example.test.common;

import android.os.AsyncTask;

import java.io.InputStream;
import java.util.concurrent.ExecutionException;

public class CommonMethod {
    static InputStream in = null; //<= static method

    public static InputStream excuteGet(AsyncTask<String, String, InputStream> task){
        try {
            in = task.execute().get();
            return in;
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
}
