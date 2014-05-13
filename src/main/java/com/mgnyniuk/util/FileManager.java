package com.mgnyniuk.util;

import java.io.File;

/**
 * Created by maksym on 5/13/14.
 */
public class FileManager {

    public static void deleteConfigFiles(int quantity) {

        for (int i = 0; i < quantity; i++) {
            File configFile = new File("config" + i + ".xml");

            if (configFile.delete()) {
                System.out.println(configFile.getName() + " is deleted!");
            } else {
                System.out.println("Delete operation is failed.");
            }

        }

    }

    public static void deleteOutputFiles(int quantity) {

        for (int i = 0; i < quantity; i++) {
            File configFile = new File("output" + i + ".xml");

            if (configFile.delete()) {
                System.out.println(configFile.getName() + " is deleted!");
            } else {
                System.out.println("Delete operation is failed.");
            }

        }

    }
}
