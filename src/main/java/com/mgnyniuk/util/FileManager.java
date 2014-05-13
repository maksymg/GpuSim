package com.mgnyniuk.util;

import java.io.File;
import java.io.FilenameFilter;

/**
 * Created by maksym on 5/13/14.
 */
public class FileManager {

    public static void deleteFilesFromCurrentDir(String namePattern) {

        String workingDir = System.getProperty("user.dir");
        System.out.println("Current working directory : " + workingDir);
        File workDir = new File(workingDir);
        File[] filesList = workDir.listFiles((dir, name) -> name.matches(namePattern));

        for (File file : filesList) {

            if (file.delete()) {
                System.out.println(file.getName() + " is deleted!");
            } else {
                System.out.println("Delete operation is failed.");
            }

        }

    }
}
