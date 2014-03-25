package com.mgnyniuk.core;

import com.gpusim2.config.GridSimConfig;
import com.mgnyniuk.core.com.mgnyniuk.core.parallel.NotifyingThread;
import com.mgnyniuk.core.com.mgnyniuk.core.parallel.ThreadListener;
import com.mgnyniuk.core.com.mgnyniuk.core.parallel.WorkerThread;

import java.io.IOException;
import java.util.List;

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

        //ConfigurationUtil.serializeConfigs(gridSimConfigList, startIndex);
        ThreadListener threadListener = new ThreadListener();

        for (int j = 0; j < overallProcessesQuantity/partProcessesQuantity; j++) {
            for (int i = startIndex; i < startIndex + partProcessesQuantity; i++) {

                NotifyingThread notifyingThread = new WorkerThread("GpuSimV2.jar",
                        String.format(CONFIG, (i + j * partProcessesQuantity)),
                        String.format(OUTPUT, (i + j * partProcessesQuantity)));
                notifyingThread.addListener(threadListener);
                notifyingThread.start();
            }

            while (threadListener.quantityOfEndedThreads != partProcessesQuantity) {
                System.out.print(threadListener.quantityOfEndedThreads);
                continue;
            }
            threadListener.quantityOfEndedThreads = 0;
        }

        //ConfigurationUtil.loadOutputs(startIndex,partProcessesQuantity);
        return true;
    }

}
