package com.mgnyniuk.core;

import com.gpusim2.config.GridSimConfig;
import com.mgnyniuk.core.com.mgnyniuk.core.parallel.NotifyingThread;
import com.mgnyniuk.core.com.mgnyniuk.core.parallel.ThreadListener;
import com.mgnyniuk.core.com.mgnyniuk.core.parallel.WorkerThread;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by maksym on 3/24/14.
 */
public class ExperimentRunner {

    private final String CONFIG = "config%s.xml";
    private final String OUTPUT = "output%s.xml";
    private int overallProcessesQuantity;
    private int partProcessesQuantity;
    private List<GridSimConfig> gridSimConfigList;
    private int startIndex;

    public ExperimentRunner() {}

    public ExperimentRunner(int overallProcessesQuantity, int partProcessesQuantity, List<GridSimConfig> gridSimConfigList, int startIndex) {
        this.overallProcessesQuantity = overallProcessesQuantity;
        this.partProcessesQuantity = partProcessesQuantity;
        this.gridSimConfigList = gridSimConfigList;
        this.startIndex = startIndex;
    }

    public Boolean runExperimnet() throws IOException {

        ThreadListener threadListener = new ThreadListener();

        int parallelPacks = 0;
        boolean isWithModulo = false;
        if (overallProcessesQuantity % partProcessesQuantity == 0) {
            parallelPacks = overallProcessesQuantity / partProcessesQuantity;
        } else {
            parallelPacks = overallProcessesQuantity / partProcessesQuantity + 1;
            isWithModulo = true;
        }

        for (int j = 0; j < parallelPacks; j++) {
            ExecutorService es = Executors.newCachedThreadPool();

            int processingConfigQuantity = 0;
            if (isWithModulo && j == (parallelPacks - 1)) {
                processingConfigQuantity = overallProcessesQuantity % partProcessesQuantity;
            } else {
                processingConfigQuantity = partProcessesQuantity;
            }

            for (int i = startIndex; i < startIndex + processingConfigQuantity; i++) {

                NotifyingThread notifyingThread = new WorkerThread("GpuSimV2.jar",
                        String.format(CONFIG, (i + j * partProcessesQuantity)),
                        String.format(OUTPUT, (i + j * partProcessesQuantity)));
                notifyingThread.addListener(threadListener);
                es.execute(notifyingThread);
            }

            es.shutdown();

            while(!es.isTerminated()) {
                continue;
            }
        }

        return true;
    }

}
