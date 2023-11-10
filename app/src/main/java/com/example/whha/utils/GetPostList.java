package com.example.whha.utils;

import android.util.Log;
import com.example.whha.model.PublicPost;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class GetPostList {
    final static String TAG = "GetPostList";

    public static List<PublicPost> getPublicPost(int limit, int offset){
        String api = "http://10.0.2.2:8000/v1/api/publicPost?offset=" + offset + "&limit=" + limit;
        String token = TokenTools.mmkv.decodeString("token");
        URL url;
        HttpURLConnection connection = null;
        List<PublicPost> publicPosts = new ArrayList<>();
        try{
            url = new URL(api);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Authorization", token);
            connection.connect();

            int reponseCode = connection.getResponseCode();
            if(reponseCode == 200){
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                JsonParser parser = new JsonParser();
                JsonObject jsonResponse = (JsonObject)parser.parse(response.toString());
                JsonArray postsArray = jsonResponse.getAsJsonArray("message");
                for (JsonElement postElement : postsArray) {
                    JsonObject postObject = postElement.getAsJsonObject();
                    String content = postObject.get("content").getAsString();
                    String nickname = postObject.get("nickname").getAsString();
                    String time = postObject.get("time").getAsString();
                    PublicPost post = new PublicPost(nickname, content, time);
                    publicPosts.add(post);
                }
                Log.i(TAG, "getPublicPost: " + publicPosts);
                return publicPosts;

            }else{
                return null;
            }
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }finally {
            if( connection != null) {
                connection.disconnect();
            }
        }
    }
}
