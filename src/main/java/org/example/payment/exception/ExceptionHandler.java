package org.example.payment.exception;

public class ExceptionHandler implements Thread.UncaughtExceptionHandler {
    @Override
    public void uncaughtException(Thread t, Throwable e) {
        System.out.println("Sorry! We got some problems when processing your request.");
        System.out.println(e.getMessage());
    }
}
