package com.example.test.common;

import com.example.test.join.UserVO;
import com.example.test.my.BabyInfoVO;

import java.util.ArrayList;
import java.util.List;

public class CommonVal {
    public static String httpip = "http://192.168.0.50";
//http://121.148.239.238:5524
    public static List<BabyInfoVO> baby_list = new ArrayList<>();
    public static UserVO curuser = new UserVO();
    public static BabyInfoVO curbaby = new BabyInfoVO();
    public static List<String> family_title = new ArrayList<>();
    public static List<String> user_id = new ArrayList<>();
    public static String curFamily;
    public static String user;
}

