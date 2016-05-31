package com.sml.repositories;

import android.os.Environment;

import com.sml.utils.ICodeRepository;

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
public class CodeRepository implements ICodeRepository {
    private static final String CODE_PATH = "/data/com.sml/code";
    private int next = 0;

    private static CodeRepository instance;
    public static CodeRepository getInstance() {
        if (instance == null)
            instance = new CodeRepository();
        return instance;
    }

    private CodeRepository() {}

    public List<File> getFiles() {
        File codeDir = new File(Environment.getDataDirectory().getAbsolutePath(), CODE_PATH);
        if (!codeDir.exists()) return null;
        if (codeDir.listFiles() == null) return null;

        return Arrays.asList(codeDir.listFiles());
    }

    public void saveCodeFile(String fileContent) {
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

    public File getNextFile() {
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

    public void clearRepository() {
        File codeDir = new File(Environment.getDataDirectory() + CODE_PATH);
        if (codeDir.exists() && codeDir.listFiles().length > 0) {
            for (File file : codeDir.listFiles()) {
                file.delete();
            }
        }
    }
}
