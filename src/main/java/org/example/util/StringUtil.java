package org.example.util;

import org.example.exceptions.BusinessException;

import java.beans.Encoder;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.spec.EncodedKeySpec;

public class StringUtil {
    public static String applySha256(String input) {
       try{
       MessageDigest digest = MessageDigest.getInstance("SHA-256");
         byte[] hash = digest.digest(input.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
           for (byte b : hash) {
               String hex = Integer.toHexString(0xff & b);
               if (hex.length() == 1) hexString.append('0');
               hexString.append(hex);
           }
              return hexString.toString();
       }
       catch(Exception e){
           throw new BusinessException("Error in hashing the input", e);
       }
    }

    public static String getDifficultyString(int difficulty) {
        return new String(new char[difficulty]).replace('\0', '0');
    }
}
