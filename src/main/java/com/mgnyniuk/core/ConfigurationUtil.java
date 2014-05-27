package com.mgnyniuk.core;

import com.gpusim2.config.GridSimConfig;
import com.gpusim2.config.GridSimOutput;
import com.gpusim2.config.IncompatibleVersionException;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by maksym on 3/24/14.
 */
public class ConfigurationUtil {
    public static void serializeConfigs(List<GridSimConfig> gridSimConfigList, int startIndex) throws FileNotFoundException {
        int i = startIndex;
        for (GridSimConfig gridSimConfig : gridSimConfigList) {
            FileOutputStream out = new FileOutputStream("config" + i + ".xml");
            XMLEncoder xmlEncoder = new XMLEncoder(out);
            xmlEncoder.writeObject(gridSimConfig);
            xmlEncoder.flush();
            xmlEncoder.close();

            i++;

        }
    }

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
}
