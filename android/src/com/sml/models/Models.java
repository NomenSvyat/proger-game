package com.sml.models;

import java.util.ArrayList;

/**
 * Created on 30.05.16.
 *
 * @author Timofey Plotnikov <timofey.plot@gmail.com>
 */
public class Models {
    public static class FirstModel {
        public ArrayList<Model> items;
    }

    public static class Model {
        public String url;
    }

    public static class DownloadModel {
        public String download_url = "";
    }
}
