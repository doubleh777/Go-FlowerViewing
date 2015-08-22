package com.HWHH.henry.goflower_viewing;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by Henry on 2015. 3. 29..
 */
public class PropertyManager {

    private Context context;
    private Properties properties;
    private final String FILE_NAME = "APIKey.properties";

    public PropertyManager(Context context){
        this.context = context;
        properties = new Properties();

        try{
            Log.i("test","1");
            AssetManager assetManager = context.getAssets();
            InputStream inputStream = assetManager.open(FILE_NAME);
            Log.i("test","2");
            properties.load(inputStream);
        }catch(IOException e){
            Log.e("AssetPropertyReader", e.toString());
        }

    }

    public String getAPIKey(String key){
        return properties.getProperty(key);
    }
}
