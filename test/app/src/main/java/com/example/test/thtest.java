package com.example.test;

import android.os.Handler;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;



public class thtest extends Thread{
    public InputStream dataInputStream;
    public OutputStream dataOutputStream;
    private Socket socket;
    public String ip = "192.168.0.92";
    private int port = 5900;
    final String TAG = "TAG+Thread";
    Handler handler;

    public thtest(Handler handler) {
        this.handler = handler;
    }

    @Override
    public void run() {
        try {
            Log.d(TAG, "접속");
            socket = new Socket(ip, port);
            dataOutputStream = socket.getOutputStream();
            dataInputStream = socket.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        byte[] buffer = new byte[1024];
        int bytes;
        String tmp = "";
        Log.d(TAG, "수신 시작");
        while(true) {
            try {
                Log.d(TAG, "수신 대기");
                bytes = dataInputStream.read(buffer);
                Log.d(TAG, "byte = " + bytes);
                if(bytes > 0) {
                    tmp = new String(buffer, 0, bytes);
                } else {
                    Log.d(TAG, "재접속");
                    socket = new Socket(ip, port);
                    dataOutputStream = socket.getOutputStream();
                    dataInputStream = socket.getInputStream();
                }
                Log.d(TAG,tmp);
                handler.obtainMessage(0,tmp).sendToTarget();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public void send(String date) throws IOException{
        if(date != null) {
            byte[] inst = date.getBytes();
            dataOutputStream.write(inst);
        }
    }
    public void sends(String content, String date) throws IOException{
        if(date != null && content != null) {
            String temp = date + " " + content;
            byte[] inst = temp.getBytes();
            dataOutputStream.write(inst);
        }
    }
}