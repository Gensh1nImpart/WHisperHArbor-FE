package com.example.whha.Test;

import com.example.whha.model.PublicPost;
import com.example.whha.utils.GetPostList;

import java.util.List;

public class GetPostList_test {
    public static void main(String[] args) {
        List<PublicPost> datas = GetPostList.getPublicPost(10,1);
    }
}
