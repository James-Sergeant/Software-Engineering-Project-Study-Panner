package com.team3_3;

import com.google.common.hash.Hashing;

import java.nio.charset.StandardCharsets;

public class Main {
    public static void main(String[] args) {
        String originalString = "Hello World";
        String sha256hex = Hashing.goodFastHash(8)
                .hashString(originalString, StandardCharsets.UTF_8)
                .toString();
        String sha256hex2 = Hashing.goodFastHash(8)
                .hashString(originalString, StandardCharsets.UTF_8)
                .toString();
        System.out.println(sha256hex +" "+sha256hex2);
    }
}
