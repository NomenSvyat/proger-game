package com.sml.repositories;

import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created on 31.05.16.
 *
 * @author Timofey Plotnikov <timofey.plot@gmail.com>
 */
public class CodeRepository {
    private static final String CODE_PATH = "/com.sml.progergame/code";

    private static CodeRepository instance;
    public static CodeRepository getInstance() {
        if (instance == null)
            instance = new CodeRepository();
        return instance;
    }

    private CodeRepository() {}

    public List<File> getFiles() {
        List<File> files = new ArrayList<>();
        File codeDir = new File(Environment.getDataDirectory().getAbsolutePath(), CODE_PATH);
        if (!codeDir.exists()) return null;

        String[] paths = codeDir.list();
        for (String path : paths) {
            files.add(new File(path));
        }

        return files;
    }

    public void saveCodeFile(String fileContent) {
        String fileName = String.valueOf(System.currentTimeMillis());
        File outputFile = new File(Environment.getDataDirectory().getAbsolutePath(), CODE_PATH + "/" + fileName);
        if (!outputFile.exists())
            outputFile.mkdirs();

        try {
            OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(outputFile));
            writer.write(fileContent, 0, fileContent.length());
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
