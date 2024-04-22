package org.example.util;

import org.example.exceptions.BusinessException;

import java.beans.Encoder;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
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

    public static byte[] applyECDSASig(PrivateKey privateKey, String input) {
        Signature dsa;
        byte[] output = new byte[0];
        try {
            dsa = Signature.getInstance("ECDSA", "BC");
            dsa.initSign(privateKey);
            byte[] strByte = input.getBytes();
            dsa.update(strByte);
            byte[] realSig = dsa.sign();
            output = realSig;
        } catch (Exception e) {
            throw new BusinessException("Error in applying ECDSA Signature", e);
        }
        return output;
    }

    public static boolean verifyECDSASig(PublicKey publicKey, String data, byte[] signature) {
        try {
            Signature ecdsaVerify = Signature.getInstance("ECDSA", "BC");
            ecdsaVerify.initVerify(publicKey);
            ecdsaVerify.update(data.getBytes());
            return ecdsaVerify.verify(signature);
        } catch (Exception e) {
            throw new BusinessException("Error in verifying ECDSA Signature", e);
        }
    }

    public static String getStringFromKey(java.security.Key key) {
        return java.util.Base64.getEncoder().encodeToString(key.getEncoded());
    }
}
