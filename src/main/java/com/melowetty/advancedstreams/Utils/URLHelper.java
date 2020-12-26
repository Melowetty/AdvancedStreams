package com.melowetty.advancedstreams.Utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

public class URLHelper {
    public static ArrayList<String> getContent(String surl) {
        ArrayList<String> content = new ArrayList<>();
        try {
            URL url = new URL(surl);
            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream(), "UTF8"));
            String Line;
            while ((Line = in.readLine()) != null)
                content.add(Line);
            in.close();
            return content;
        } catch (Exception e) {
            return content;
        }
    }

    public static String arrayListToString(ArrayList<String> alist) {
        String content = "";
        for (String s : alist)
            content = String.valueOf(content) + s;
        return content;
    }
}
