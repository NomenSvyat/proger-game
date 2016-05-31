package com.sml.utils;

import android.Manifest;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresPermission;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileStorage {
    private static final String NO_MEDIA_FILE = ".nomedia";
    private static final String FILE_PREFIX = "file://";
    private final int appNameRes;
    private Context context;

    public FileStorage(Context context) {
        this.context = context;
        appNameRes = context.getApplicationInfo().labelRes;
    }

    @NonNull
    private static String getFileName() {
        return "rec_tmp";
    }

    /**
     * @param dir directory file to create
     * @return true - if directory didn't exist and was created, otherwise - false
     * @throws IOException - if some error occurred while dir create
     */
    private static boolean createDir(File dir) throws IOException {
        if (!dir.exists()) {
            if (!dir.mkdirs()) {
                if (!dir.exists()) {
                    throw new IOException("Dir \"" + dir + "\" create error");
                }
            }

            return true;
        }
        return false;
    }

    /**
     * Creates file ".nomedia" in dir
     *
     * @param dir
     * @throws IOException
     */
    private static void createNoMediaFile(File dir) throws IOException {
        File noMediaFile = new File(dir, NO_MEDIA_FILE);

        FileOutputStream outputStream = new FileOutputStream(noMediaFile);
        outputStream.close();
    }

    public static String addFilePrefix(final String path) {
        if (path.startsWith(FILE_PREFIX)) {
            return path;
        }
        return FILE_PREFIX + path;
    }

    @RequiresPermission(allOf = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE})
    public File getOutputFile() throws IOException {
        // Create a media file name
        File mediaFile;
        String subdir = "rec";
        File dir = new File(getAppDir(), subdir);

        createDir(dir);

        mediaFile = new File(dir + File.separator
                + getFileName());

        return mediaFile;
    }

    private File getAppDir() throws IOException {
        File appDir = new File(
                Environment.getExternalStorageDirectory(),
                context.getResources().getString(appNameRes));

        if (createDir(appDir)) {
            createNoMediaFile(appDir);
        }
        return appDir;
    }

    public String getRealPathFromURI(Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] projection = {MediaStore.Images.Media.DATA};
            cursor = context.getContentResolver().query(contentUri, projection, null, null, null);
            if (cursor == null) {
                return "";
            }
            int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(columnIndex);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }
}
