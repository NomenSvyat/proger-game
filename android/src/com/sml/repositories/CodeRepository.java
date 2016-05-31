package com.sml.repositories;

import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.List;

/**
 * Created on 31.05.16.
 *
 * @author Timofey Plotnikov <timofey.plot@gmail.com>
 */
public class CodeRepository {
    private static final String CODE_PATH = "/data/com.sml/code";
    private static int next = 0;

    private CodeRepository() {}

    public static List<File> getFiles() {
        File codeDir = new File(Environment.getDataDirectory().getAbsolutePath(), CODE_PATH);
        if (!codeDir.exists()) return null;

        return Arrays.asList(codeDir.listFiles());
    }

    public static void saveCodeFile(String fileContent) {
        String fileName = String.valueOf(System.currentTimeMillis());
        File outputFile = new File(Environment.getDataDirectory().getAbsolutePath(), CODE_PATH + "/" + fileName);
        if (!outputFile.exists())
            outputFile.getParentFile().mkdirs();

        try {
            OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(outputFile));
            writer.write(fileContent, 0, fileContent.length());
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static File nextFile() {
        List<File> files = getFiles();
        if (files != null && files.size() > 0) {
            try {
                next += 1;
                return files.get(next - 1);
            }
            catch (IndexOutOfBoundsException e) {
                e.printStackTrace();
                next = 1;
                return files.get(0);
            }
        } else {
            return null;
        }
    }
}
