package com.example.whha.utils;

import android.util.Log;
import com.example.whha.Register;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.tencent.mmkv.MMKV;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class TokenTools {
    public static MMKV mmkv;
    final static String TAG = "TokenTools";

    public TokenTools(){
        mmkv = MMKV.defaultMMKV();
    }
    static public boolean ExistToken(){
        if(mmkv.contains("token")){
            String api = "http://10.0.2.2:8000/v1/api/sayhello";
            String token = mmkv.decodeString("token");
            URL url;
            HttpURLConnection connection = null;
            try{
                url = new URL(api);
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setRequestProperty("Authorization", token);
                connection.connect();

                int respondedCode = connection.getResponseCode();
                if (respondedCode == 200) {
                    InputStream inputStream = connection.getInputStream();
                    JsonParser jsonParser = new JsonParser();
                    JsonElement rootElement = jsonParser.parse(new InputStreamReader(inputStream));
                    if(rootElement.isJsonObject()){
                        JsonObject jsonObject = rootElement.getAsJsonObject();
                        Log.i(TAG, "ExistToken: " + jsonObject);
                        return jsonObject.get("code").getAsInt() == 200;
                    }
                }else{
                    return false;
                }
            } catch (Exception e){
                Log.e(TAG, "ExistToken: ", e.getCause());
                return false;
            }finally {
                if( connection != null) {
                    connection.disconnect();
                }
            }
        }
        return false;
    }

    static public boolean Login(String username, String password) throws Exception {
        JSONObject toLoginJson = new JSONObject();
        URL url;
        HttpURLConnection connection = null;
        try{
            toLoginJson.put("account", username);
            toLoginJson.put("passwd", password);
            String api = "http://10.0.2.2:8000/auth/login";
            url = new URL(api);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            connection.setDoOutput(true);
            OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
            writer.write(toLoginJson.toString());
            writer.flush();
            connection.connect();
            int respondedCode = connection.getResponseCode();
            if (respondedCode == 200){
                InputStream inputStream = connection.getInputStream();
                JsonParser jsonParser = new JsonParser();
                JsonElement rootElement = jsonParser.parse(new InputStreamReader(inputStream));
                if(rootElement.isJsonObject()){
                    JsonObject jsonObject = rootElement.getAsJsonObject();
                    Log.i(TAG, "ExistToken: " + jsonObject);
                    if(jsonObject.get("code").getAsInt() == 200){
                        mmkv.encode("token", jsonObject.get("token").getAsString());
                        return true;
                    }
                }
            }else return false;
        }catch (Exception e){
            Log.i(TAG, "Login: ", e);
        }finally {
            if(connection != null){
                connection.disconnect();
            }
        }
        return false;
    }

    public static String register(String username, String password, String nickname) throws Exception{
        Log.i(TAG, "register: ");
        JSONObject toRegisterJson = new JSONObject();
        URL url;
        HttpURLConnection connection = null;
        try {
            toRegisterJson.put("account", username);
            toRegisterJson.put("passwd", password);
            toRegisterJson.put("nickname", nickname);
            String api = "http://10.0.2.2:8000/auth/register";
            url = new URL(api);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            connection.setDoOutput(true);
            OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
            writer.write(toRegisterJson.toString());
            writer.flush();
            connection.connect();
            int respondedCode = connection.getResponseCode();
            if (respondedCode == 200){
                InputStream inputStream = connection.getInputStream();
                JsonParser jsonParser = new JsonParser();
                JsonElement rootElement = jsonParser.parse(new InputStreamReader(inputStream));
                if(rootElement.isJsonObject()){
                    JsonObject jsonObject = rootElement.getAsJsonObject();
                    Log.i(TAG, "ExistToken: " + jsonObject);
                    if(jsonObject.get("code").getAsInt() == 200){
                        return "注册成功";
                    }else{
                        return jsonObject.get("message").getAsString();
                    }
                }
            }else return "注册失败";
        }catch (Exception e){
            Log.i(TAG, "Register: ", e);
            return "注册失败";
        }finally {
            if(connection != null){
                connection.disconnect();
            }
        }
        return "注册失败";
    }


}
