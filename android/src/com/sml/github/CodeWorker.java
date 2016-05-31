package com.sml.github;

import android.os.Environment;
import android.util.Log;

import com.sml.models.Models;
import com.sml.network.RestClient;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import okhttp3.ResponseBody;
import retrofit2.Response;

/**
 * Created on 31.05.16.
 *
 * @author Timofey Plotnikov <timofey.plot@gmail.com>
 */
public class CodeWorker implements Runnable {

    private static final String TAG = "CodeWorker";

    private String url;

    public CodeWorker(String url) {
        this.url = url;
    }

    @Override
    public void run() {
        try {
            Response<Models.DownloadModel> response = RestClient.getInstance().fetchFile(url).execute();
            if (response.errorBody() != null) {
                Log.d(TAG,response.errorBody().string());
                return;
            }

            if (response.body() != null && !response.body().download_url.isEmpty()) {
                download(response.body().download_url);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void download(String downloadUrl) throws IOException {
        Response<ResponseBody> response = RestClient.getInstance().downloadFile(downloadUrl).execute();
        if (response.errorBody() != null) {
            Log.d(TAG, response.errorBody().string());
            return;
        }

        if (response.body() != null) {
            saveToFile(response.body().string().replaceAll("\t", "    "));
        }

    }
    private void saveToFile(String contents) {
        String fileName = String.valueOf(System.currentTimeMillis());
        File outputFile = new File(Environment.getDataDirectory().getAbsolutePath(), "/com.sml.progergame/code/" + fileName);
        if (!outputFile.exists())
            outputFile.mkdirs();

        try {
            OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(outputFile));
            writer.write(contents, 0, contents.length());
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
