package com.team3_3;


import com.team3_3.Planner.utils.Hash;

import java.nio.charset.StandardCharsets;

public class Main {
    public static void main(String[] args) {
        String s = Hash.SHA1("afa19aeu@uea.ac.uk"+Math.random());
        String S = s.substring(0,16);
        System.out.println(S.length());
        System.out.println(S);
    }
}
