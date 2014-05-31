package com.mgnyniuk.core.com.mgnyniuk.core.parallel;


/**
 * Created by maksym on 3/24/14.
 */
public class ThreadListener implements ThreadCompleteListener {
    private int quantityOfEndedThreads;

    @Override
    public void notifyOfThreadComplete(Thread thread) {
        System.out.println(thread.getName() + " ended!");
        quantityOfEndedThreads++;

    }

    public int getQuantityOfEndedThreads() {
        return quantityOfEndedThreads;
    }
}
