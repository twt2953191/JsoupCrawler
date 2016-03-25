package org.twt.thread;

import java.lang.Thread.UncaughtExceptionHandler;

public class ThreadExceptionHandler implements UncaughtExceptionHandler{
    public void uncaughtException(Thread th, Throwable t){
        System.out.println("there is a exception: " + t.getMessage());
    }
}