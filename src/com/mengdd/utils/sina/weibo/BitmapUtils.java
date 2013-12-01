package com.mengdd.utils.sina.weibo;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;

public class BitmapUtils {
    public static final String CACHES_PATH = "mnt/sdcard/meng_po/cache/";

    public static Bitmap getBitmap(String path) {

        Bitmap bitmap = getBitmapFromFile(path);
        if (bitmap == null) {
            try {
                URL url = new URL(path);
                HttpURLConnection conn = (HttpURLConnection) url
                        .openConnection();
                conn.setDoInput(true);
                conn.connect();
                bitmap = BitmapFactory.decodeStream(conn.getInputStream());
                saveBitmap(path, bitmap);
                conn.disconnect();
            }
            catch (MalformedURLException e) {
                e.printStackTrace();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        return bitmap;
    }

    public static Drawable getDrawable(String path) {
        Drawable bitmap = null;
        try {
            URL url = new URL(path);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoInput(true);
            conn.connect();
            bitmap = Drawable.createFromStream(conn.getInputStream(), null);
            conn.disconnect();
        }
        catch (MalformedURLException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    public static Bitmap getBitmapFromFile(String url) {
        return BitmapFactory.decodeFile(CACHES_PATH + url + ".png");
    }

    /**
     * 保存图片
     * 
     * @param url
     * @param bitmap
     */
    public static void saveBitmap(String url, Bitmap bitmap) {
        File file = new File(CACHES_PATH + url + ".png");

        if (!file.exists()) {
            file.getParentFile().mkdirs();
            try {
                file.createNewFile();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        FileOutputStream os;
        try {
            os = new FileOutputStream(file);
            bitmap.compress(CompressFormat.PNG, 100, os);
            os.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

}
