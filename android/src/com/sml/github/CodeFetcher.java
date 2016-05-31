package com.sml.github;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created on 31.05.16.
 *
 * @author Timofey Plotnikov <timofey.plot@gmail.com>
 */
public class CodeFetcher {

    private static final int THREAD_COUNT = 5;
    private ArrayList<String> urls;

    public CodeFetcher(ArrayList<String> urls) {
        this.urls = urls;
    }

    public void fetch() {
        ExecutorService service = Executors.newFixedThreadPool(THREAD_COUNT);
        for (String url : urls) {
            Runnable worker = new CodeWorker(url);
            service.execute(worker);
        }

        service.shutdown();
        try { service.awaitTermination(60, TimeUnit.SECONDS); }
        catch (InterruptedException e) { e.printStackTrace(); }
    }
}