package com.example.test.common;

import android.os.AsyncTask;

import java.io.InputStream;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class CommonMethod {
    static InputStream in = null; //<= static method

    public static InputStream excuteGet(AsyncTask<String, String, InputStream> task){
        try {
            in = task.execute().get(6000, TimeUnit.MILLISECONDS);
            return in;
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        return null;
    }
}
