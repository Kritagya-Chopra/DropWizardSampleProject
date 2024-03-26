package com.assignment.client;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter

public class TokenEncyption {
   static int key=10;

    public static String encrypt(String data)
    {
        String token="";
        for(char c : data.toCharArray())
        {
            char ch= (char) (c+key);
            token+=ch;
        }

        return token;

    }

    public static String decrypt(String token)
    {
        String data="";
        for(char c : token.toCharArray())
        {
            char ch= (char) (c-key);
            data+=ch;
        }

        return data;
    }






}
