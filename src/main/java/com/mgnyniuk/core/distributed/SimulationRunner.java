package com.mgnyniuk.core.distributed;

import java.io.Serializable;
import java.util.concurrent.Callable;

/**
 * Created by maksym on 5/24/14.
 */
public class SimulationRunner implements Callable<Boolean>, Serializable {

    @Override
    public Boolean call() throws Exception {
        System.out.print("Hello Hazelcast from Master!!!");

        return true;
    }
}
