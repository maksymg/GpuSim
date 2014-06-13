package com.mgnyniuk.core.distributed;

import com.gpusim2.config.GridSimConfig;
import com.gpusim2.config.GridSimOutput;
import com.gpusim2.config.IncompatibleVersionException;
import com.mgnyniuk.core.com.mgnyniuk.core.parallel.NotifyingThread;
import com.mgnyniuk.core.com.mgnyniuk.core.parallel.ThreadListener;
import com.mgnyniuk.core.com.mgnyniuk.core.parallel.WorkerThread;
import com.mgnyniuk.ui.MainWindow;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by maksym on 5/24/14.
 */
public class SimulationRunner implements Callable<Boolean>, Serializable {

    private final String CONFIG = "config%s.xml";
    private final String OUTPUT = "output%s.xml";
    private int overallProcessesQuantity;
    private int partProcessesQuantity;
    private int startIndex;

    public SimulationRunner() {}

    public SimulationRunner(int overallProcessesQuantity, int partProcessesQuantity, int startIndex) {
        this.overallProcessesQuantity = overallProcessesQuantity;
        this.partProcessesQuantity = partProcessesQuantity;
        this.startIndex = startIndex;
    }

    private void serializeConfigs(Map<Integer, GridSimConfig> configMap) throws FileNotFoundException {

        for (Integer i = 0; i < overallProcessesQuantity; i++) {

            GridSimConfig gridSimConfig = configMap.get(i + startIndex);

            FileOutputStream out = new FileOutputStream("config" + (i + startIndex) + ".xml");
            XMLEncoder xmlEncoder = new XMLEncoder(out);
            xmlEncoder.writeObject(gridSimConfig);
            xmlEncoder.flush();
            xmlEncoder.close();
        }

    }

    private void deserializeOutputs(Map<Integer, GridSimOutput> outputMap) throws FileNotFoundException, IncompatibleVersionException {

        GridSimOutput gridSimOutput;

        for (int i = 0; i < overallProcessesQuantity; i++) {

            FileInputStream in = new FileInputStream("output" + (i + startIndex) + ".xml");
            XMLDecoder xmlDecoder = new XMLDecoder(in);
            gridSimOutput = (GridSimOutput) xmlDecoder.readObject();
            xmlDecoder.close();

            outputMap.put(i + startIndex, gridSimOutput);
        }
    }

    @Override
    public Boolean call() throws Exception {
        System.out.print("Hello Hazelcast from Master!!!");

        serializeConfigs(MainWindow.configMap);

        //ConfigurationUtil.serializeConfigs(gridSimConfigList, startIndex);
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
                //notifyingThread.start();
                es.execute(notifyingThread);
            }

            es.shutdown();

            while(!es.isTerminated()) {
                continue;
            }
        }

        deserializeOutputs(MainWindow.outputMap);

        return true;
    }
}
