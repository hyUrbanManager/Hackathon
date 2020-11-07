package com.hy.security;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Collection;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

@RunWith(Parameterized.class)
public class HMacTest {

    private String mRaw;

    @Parameterized.Parameters
    public static Collection<String> generateParams() {
        return Arrays.asList(
                "W65IC-LGMM11A-G_iiyama_0",
                "CP8601K_benq_cp_0",
                "L55EE-BO1311A-G_lsk_0",
                "L75EG-BOP150A-G_cleartouch_0",
                "K65CB-CS2350A-G_cleartouch_0",
                "W75ID-BOP150A-G_iiyama_v2_0"
        );
    }

    public HMacTest(String raw) {
        mRaw = raw;
    }

    @Test
    public void testPrintAppKey() {
        Assert.assertTrue(mRaw.split("_").length >= 3);
        String result = hMacSha1(mRaw);
        System.out.println(mRaw + ": " + result);
    }

    private static final String KEY = "seewo";
    private static final String HMAC_SHA1_ALGORITHM = "HmacSHA1";
    private static final String ENCODING = "UTF-8";

    public static String hMacSha1(String str) {
        try {
            byte[] data = KEY.getBytes(ENCODING);
            SecretKey secretKey = new SecretKeySpec(data, HMAC_SHA1_ALGORITHM);
            Mac mac = Mac.getInstance(HMAC_SHA1_ALGORITHM);
            mac.init(secretKey);
            byte[] text = str.getBytes(ENCODING);
            byte[] macData = mac.doFinal(text);
            StringBuilder result = new StringBuilder();
            for (final byte element : macData) {
                result.append(Integer.toString((element & 0xff) + 0x100, 16).substring(1));
            }
            return result.toString();
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException | InvalidKeyException e) {
            System.out.println("error");
        }
        return null;
    }

}
