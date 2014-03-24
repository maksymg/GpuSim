package com.mgnyniuk.core.com.mgnyniuk.core.parallel;

/**
 * Created by maksym on 3/24/14.
 */
public class ThreadListener implements ThreadCompleteListener {
    public int quantityOfEndedThreads;

    @Override
    public void notifyOfThreadComplete(Thread thread) {
        System.out.println(thread.getName() + " ended!");
        quantityOfEndedThreads++;
    }
}
