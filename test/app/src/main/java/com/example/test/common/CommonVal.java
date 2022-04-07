package com.example.test.common;

import com.example.test.join.UserVO;
import com.example.test.my.BabyInfoVO;

import java.util.ArrayList;
import java.util.List;

public class CommonVal {
    //본인 ip로 테스트하고 푸쉬전에 :5524로 변경해 주시면 됩니다.     http://121.148.239.238:5524   ㄱㅎ:192.168.0.50
    public static String httpip = "http://121.148.239.238:5524";

    public static List<BabyInfoVO> baby_list = new ArrayList<>();
    public static UserVO curuser = new UserVO();
    public static BabyInfoVO curbaby = new BabyInfoVO();
    public static List<String> family_title = new ArrayList<>();
    public static List<String> user_id = new ArrayList<>();
    public static String curFamily;
    public static String user;
}

