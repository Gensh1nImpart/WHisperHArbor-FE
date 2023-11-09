package com.example.whha.utils;

import android.util.Log;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class PublishPost {
    public static boolean Publish(String content, boolean vis){
        String api = "http://10.0.2.2:8000/v1/api/post";
        JSONObject toPublish = new JSONObject();
        URL url;
        HttpURLConnection connection = null;
        try{
            toPublish.put("content", content);
            toPublish.put("encrypted", vis);
            url = new URL(api);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Authorization", TokenTools.mmkv.decodeString("token"));
            connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            connection.setDoOutput(true);
            OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
            writer.write(toPublish.toString());
            writer.flush();
            connection.connect();
            int respondedCode = connection.getResponseCode();
            if (respondedCode == 200){
                InputStream inputStream = connection.getInputStream();
                JsonParser jsonParser = new JsonParser();
                JsonElement rootElement = jsonParser.parse(new InputStreamReader(inputStream));
                if(rootElement.isJsonObject()){
                    JsonObject jsonObject = rootElement.getAsJsonObject();
                    if(jsonObject.get("code").getAsInt() == 200){
                        return true;
                    }else{
                        return false;
                    }
                }
            }else return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }finally {
            if(connection != null){
                connection.disconnect();
            }
        }
        return false;
    }
}
