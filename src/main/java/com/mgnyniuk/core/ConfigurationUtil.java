package com.mgnyniuk.core;

import com.gpusim2.config.GridSimConfig;
import com.gpusim2.config.GridSimOutput;
import com.gpusim2.config.GridSimResourceConfig;
import com.gpusim2.config.IncompatibleVersionException;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by maksym on 3/24/14.
 */
public class ConfigurationUtil {

    public static List<GridSimOutput> loadOutputs(int startIndex, int partProcessesQuantity) throws FileNotFoundException, IncompatibleVersionException {

        GridSimOutput gridSimOutput;
        List<GridSimOutput> gridSimOutputList = new ArrayList<GridSimOutput>();

        for (int i = startIndex; i < startIndex + partProcessesQuantity; i++) {
            FileInputStream in = new FileInputStream("output" + i + ".xml");
            XMLDecoder xmlDecoder = new XMLDecoder(in);
            gridSimOutput = (GridSimOutput) xmlDecoder.readObject();
            xmlDecoder.close();

            //Main.outputMap.put(i, gridSimOutput);
            gridSimOutputList.add(gridSimOutput);
        }

        //System.out.println("OutputMap size: " + Main.outputMap.size());
        return gridSimOutputList;
    }

    public static Map<Integer, GridSimOutput> deserializeOutputsToMap() throws FileNotFoundException {

        String workingDir = System.getProperty("user.dir");
        System.out.println("Current working directory : " + workingDir);
        File workDir = new File(workingDir);
        File[] filesList = workDir.listFiles((dir, name) -> name.matches("output.*\\.xml"));

        Map<Integer, GridSimOutput> gridSimOutputMap = new HashMap<>();

        for (int i = 0; i < filesList.length; i++) {
            FileInputStream in = new FileInputStream("output" + i + ".xml");
            XMLDecoder xmlDecoder = new XMLDecoder(in);
            GridSimOutput gridSimOutput = (GridSimOutput) xmlDecoder.readObject();
            xmlDecoder.close();

            gridSimOutputMap.put(i, gridSimOutput);
        }

        return gridSimOutputMap;
    }

    public static Map<Integer, GridSimConfig> deserializeConfigsToMap() throws FileNotFoundException {

        String workingDir = System.getProperty("user.dir");
        System.out.println("Current working directory : " + workingDir);
        File workDir = new File(workingDir);
        File[] filesList = workDir.listFiles((dir, name) -> name.matches("config.*\\.xml"));

        Map<Integer, GridSimConfig> gridSimConfigMap = new HashMap<>();

        for (int i = 0; i < filesList.length; i++) {
            FileInputStream in = new FileInputStream("config" + i + ".xml");
            XMLDecoder xmlDecoder = new XMLDecoder(in);
            GridSimConfig gridSimConfig = (GridSimConfig) xmlDecoder.readObject();
            xmlDecoder.close();

            gridSimConfigMap.put(i, gridSimConfig);
        }

        return gridSimConfigMap;
    }
}
