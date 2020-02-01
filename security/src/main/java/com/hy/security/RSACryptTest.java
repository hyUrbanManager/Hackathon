package com.hy.security;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Arrays;
import java.util.Base64;

import javax.crypto.Cipher;

/**
 * Created by Administrator on 2018/1/25.
 *
 * @author hy 2018/1/25
 */

public class RSACryptTest {

    public static PublicKey KEY1;

    public static final String BAIDU_CERTIFICATE_PATH = "E:\\密钥证书\\baidu.cer";

    public PublicKey getKey() throws Exception {
        File file = new File(BAIDU_CERTIFICATE_PATH);
        FileInputStream fis = new FileInputStream(file);

        CertificateFactory certificateFactory = CertificateFactory.getInstance("X509");
        X509Certificate cert = (X509Certificate) certificateFactory.generateCertificate(fis);

        // 公钥
        PublicKey publicKey = cert.getPublicKey();

        fis.close();

        return publicKey;
    }

    public static final int[] encryptedData = {
            0x4d, 0x3a, 0x48, 0x3a, 0x6f, 0xeb, 0xd2, 0xf4, 0xe2, 0x7d, 0xef, 0x13, 0xed, 0x3b, 0x14, 0xa9
            , 0x59, 0x0e, 0x01, 0x21, 0x9c, 0x90, 0xff, 0x07, 0x56, 0xec, 0x39, 0x8e, 0xa3, 0x5a, 0x1c, 0xcc
            , 0x43, 0x65, 0xd7, 0x02, 0x28, 0xef, 0xfb, 0xce, 0x38, 0x2f, 0xb8, 0xd2, 0xd1, 0xe9, 0xaf, 0x84
            , 0xca, 0xdd, 0xc2, 0x63, 0xe4, 0xdf, 0x95, 0x9d, 0x7f, 0xb1, 0xc8, 0x8f, 0xe2, 0xc7, 0xbc, 0x9d
            , 0x47, 0x0a, 0x35, 0x4a, 0x2c, 0x5e, 0xe9, 0x3a, 0xbd, 0x84, 0xa0, 0x92, 0x0c, 0xe5, 0x05, 0x8e
            , 0x6d, 0xc5, 0x07, 0x84, 0xe4, 0x65, 0x81, 0xa6, 0x50, 0x39, 0x14, 0xbd, 0x89, 0x9b, 0x2b, 0xde
            , 0x3b, 0x97, 0x98, 0xc0, 0xda, 0x4b, 0x96, 0x50, 0xa2, 0x58, 0x68, 0x36, 0xc2, 0x81, 0x7c, 0x26
            , 0xd5, 0xff, 0xca, 0xdb, 0xa8, 0x34, 0x53, 0xc2, 0x7a, 0x54, 0x85, 0x64, 0x71, 0x67, 0x56, 0x37
            , 0xde, 0x28, 0xc6, 0xe2, 0x26, 0xa0, 0xef, 0x34, 0xdf, 0x11, 0x8f, 0x46, 0x82, 0xba, 0x55, 0x71
            , 0x86, 0x2d, 0xcf, 0x54, 0x97, 0x29, 0x7d, 0x3a, 0xe4, 0x74, 0x39, 0x30, 0xdb, 0x72, 0xf7, 0x2c
            , 0x71, 0x00, 0x4c, 0xf9, 0x58, 0x11, 0x5e, 0x5a, 0x4f, 0x98, 0x51, 0xc8, 0x93, 0xb7, 0xa6, 0xf2
            , 0xb1, 0xc7, 0x41, 0xe1, 0xb3, 0xc5, 0x5c, 0x56, 0x43, 0xe1, 0xb7, 0x89, 0x55, 0xb1, 0x4a, 0x8a
            , 0xc4, 0x28, 0x32, 0x3a, 0xd5, 0x08, 0x9d, 0x34, 0xd7, 0x91, 0xbd, 0x03, 0x5d, 0x28, 0xe0, 0x12
            , 0x3d, 0x50, 0x06, 0xa8, 0x2c, 0x43, 0xb7, 0x2b, 0x7a, 0x14, 0xb1, 0x0c, 0xc1, 0x63, 0x89, 0xb7
            , 0x57, 0x70, 0x95, 0x1c, 0x71, 0xc8, 0xcf, 0x3d, 0xf7, 0x20, 0x2b, 0x18, 0x54, 0x34, 0xfc, 0x5d
            , 0x14, 0x75, 0xf6, 0xe4, 0x1a, 0x41, 0x27, 0x58, 0x24, 0x91, 0xe7, 0xe4, 0x7c, 0x7f, 0xae, 0x24
            , 0x46, 0xe9, 0x1a, 0x16, 0x1b, 0x07, 0x01, 0x41, 0xed, 0xa3, 0xe1, 0x09, 0xf5, 0xc9, 0x67, 0x8d
            , 0x57, 0x9d, 0x9d, 0x4f, 0x17, 0xe4, 0x52, 0x83, 0xd9, 0x99, 0x72, 0xc8, 0xc4, 0x39, 0x1e, 0x93
            , 0xcf, 0x86, 0xcd, 0x8b, 0xba, 0xaa, 0xc9, 0x88, 0xa1, 0xc0, 0x8b, 0x23, 0x12, 0x1f, 0xcc, 0x47
            , 0x87, 0x6e, 0x80, 0xc2, 0xb2, 0x84, 0xc8, 0x5c, 0x97, 0x6e, 0x24, 0x5e, 0x82, 0x45, 0xd5, 0x68
            , 0x47, 0xd1, 0x06, 0x15, 0x69, 0x85, 0x22, 0x04, 0x4d, 0x88, 0xb6, 0x91, 0xbf, 0x75, 0xe7, 0x04
            , 0xc5, 0x3c, 0x7d, 0xd0, 0x8a, 0xde, 0x5b, 0x91, 0x5c, 0x96, 0x86, 0x0f, 0xc1, 0x02, 0x88, 0xc7
            , 0xdc, 0x9d, 0xbc, 0xed, 0xd4, 0x22, 0x14, 0x1c, 0xfd, 0x74, 0x5c, 0x7d, 0xb0, 0xe0, 0x26, 0x84
            , 0x12, 0xa3, 0xa7, 0x81, 0x8a, 0x04, 0xae, 0x10, 0x63, 0x9b, 0x3d, 0xb0, 0xa7, 0x9b, 0x1a, 0x3a
            , 0xa5, 0x05, 0x80, 0x81, 0x63, 0xef, 0x6b, 0x11, 0xc3, 0x20, 0xb2, 0x87, 0xbd, 0x5e, 0x87, 0x56
            , 0xaf, 0xdb, 0x90, 0x31, 0xe5, 0x28, 0xc7, 0x20, 0x0e, 0x1b, 0xd8, 0x4e, 0x87, 0xee, 0x51, 0xe5
            , 0x7c, 0x58, 0xb5, 0xa5, 0x37, 0x4f, 0x4c, 0xe8, 0x65, 0xb2, 0x14, 0xa9, 0x3f, 0xfe, 0xdd, 0x6e
            , 0x2a, 0xfd, 0x55, 0x88, 0xd7, 0xee, 0x20, 0xff, 0x5a, 0x00, 0x02, 0xed, 0x44, 0xec, 0xde, 0x1c
            , 0xba, 0xe4, 0x39, 0xd0, 0x19, 0xe5, 0x6b, 0x0e, 0x01, 0xef, 0x24, 0x0b, 0x3c, 0xe2, 0x0e, 0xdd
            , 0x82, 0xe8, 0x20, 0x98, 0x8b, 0xec, 0x0b, 0x35, 0x68, 0x2f, 0xc4, 0xdb, 0xef, 0x2e, 0xe6, 0xd6
            , 0x32, 0xf2, 0x9c, 0x9d, 0x4c, 0x9b, 0x69, 0xe5, 0xc7, 0x45, 0xb5, 0x05, 0x85, 0xde, 0x84, 0x94
            , 0x17, 0x88, 0xbc, 0x2e, 0x7c, 0xb5, 0x5b, 0xf5, 0xfb, 0x18, 0x4c, 0x41, 0xa6, 0x21, 0x07, 0x33
            , 0x14, 0x9a, 0x67, 0x2e, 0xc2, 0x03, 0x6c, 0xf1, 0xe1, 0x62, 0x56, 0x18, 0x0f, 0xcd, 0xfd, 0xf5
            , 0x9b, 0xec, 0x8d, 0x5e, 0x5c, 0x83, 0xba, 0x95, 0xe5, 0xf0, 0x44, 0xba, 0x01, 0x24, 0xb9, 0x22
            , 0xea, 0xd7, 0x01, 0x6a, 0x8c, 0x1b, 0xa6, 0x89, 0x0a, 0x4e, 0xb3, 0xd4, 0xb9, 0x6e, 0xab, 0x59
            , 0xb1, 0x44, 0x7b, 0x8b, 0x7f, 0x86, 0x3c, 0x8b, 0xa5, 0xd4, 0x8e, 0xb7, 0x44, 0x2f, 0xf2, 0x55
            , 0x06, 0x27, 0x4e, 0x6a, 0x0c, 0x21, 0x91, 0xf6, 0x7d, 0xa2, 0xa8, 0xcb, 0x24, 0x2d, 0xb3, 0x8e
            , 0xdf, 0x37, 0xa8, 0xc0, 0xcf, 0x0a, 0x9b, 0x17, 0x6b, 0x25, 0x94, 0x3d, 0xbe, 0x28, 0x48, 0x52
            , 0x49, 0xbd, 0x1c, 0x24, 0x30, 0x82, 0x58, 0xc5, 0x00, 0xc8, 0x1f, 0xdd, 0xbd, 0x48, 0x0a, 0x33
            , 0x4a, 0x23, 0x27, 0x3a, 0x4f, 0x44, 0xc1, 0xbd, 0x47, 0x73, 0x56, 0xaf, 0x1c, 0x08, 0x2b, 0xd6
            , 0x6a, 0x95, 0x4a, 0xe7, 0xfd, 0x0d, 0x26, 0xee, 0xfb, 0x24, 0x6f, 0x36, 0xe8, 0x48, 0x80, 0x00
            , 0x1f, 0x65, 0xcd, 0x22, 0x05, 0x46, 0x88, 0x18, 0x32, 0xf3, 0x91, 0x74, 0x6d, 0x10, 0x05, 0xae
            , 0x19, 0xb8, 0xa9, 0x26, 0xa2, 0xa9, 0x33, 0x28, 0x23, 0xfb, 0xd8, 0xaf, 0x5e, 0xfd, 0x50, 0xc4
            , 0x51, 0x63, 0x89, 0xe3, 0xf4, 0xb6, 0xa6, 0x0b, 0xbb, 0x6e, 0x8f, 0x6e, 0xeb, 0x80, 0xa4, 0x94
            , 0x07, 0x32, 0x43, 0x1f, 0xd5, 0xc6, 0xda, 0xcb, 0xe5, 0x3c, 0x6f, 0xcf, 0x39, 0xdb, 0xb5, 0x9e
            , 0x10, 0xe8, 0xc0, 0xaf, 0xfb, 0x32, 0xbc, 0xd8, 0xe9, 0x65, 0x07, 0x48, 0x9e, 0x46, 0xc1, 0x63
            , 0x15, 0x95, 0x63, 0x41, 0x79, 0xac, 0xd7, 0x8c, 0x0c, 0x86, 0x75, 0x7a, 0x2c, 0xda, 0x5f, 0xe3
            , 0x65, 0xa3, 0x6c, 0x76, 0xf7, 0xb5, 0xe5, 0x9e, 0x73, 0xc0, 0xbd, 0xda, 0xf7, 0x9f, 0xf5, 0x0e
            , 0xed, 0x59, 0xad, 0x3e, 0x65, 0xf4, 0x9c, 0x09, 0x0e, 0xc4, 0x65, 0x84, 0xf2, 0x85, 0x02, 0x30
            , 0xc7, 0x00, 0xc9, 0x43, 0xf7, 0x17, 0x0f, 0x60, 0x40, 0x90, 0xd2, 0xd4, 0xfb, 0x05, 0xba, 0xa5
            , 0x4e, 0x47, 0x30, 0x85, 0x48, 0xca, 0x76, 0xa7, 0x9d, 0xb2, 0x08, 0xdf, 0x36, 0x4a, 0x42, 0x38
            , 0xfe, 0x29, 0x04, 0x70, 0x7d, 0x46, 0xcc, 0x25, 0x30, 0xc8, 0xae, 0xe3, 0x8b, 0xfe, 0x44, 0x09
            , 0x5b, 0x62, 0xd3, 0x95, 0xf5, 0xd0, 0x23, 0x06, 0xe0, 0xad, 0x5a, 0x18, 0x85, 0x02, 0x4e, 0x2e
            , 0x0f, 0xae, 0x83, 0x02, 0xa9, 0x74, 0x9a, 0xf1, 0x5b, 0xbc, 0xc5, 0x92, 0xa9, 0xe2, 0x36, 0xce
            , 0x06, 0x28, 0x27, 0x9d, 0x12, 0x70, 0xb5, 0x39, 0xcb, 0xa1, 0x51, 0x9b, 0xa2, 0xbd, 0x2c, 0x4d
            , 0x45, 0x36, 0x90, 0x49, 0xfb, 0x8a, 0x75, 0x24, 0x2b, 0x83, 0x87, 0x99, 0xdd, 0x64, 0xd1, 0x91
            , 0xb4, 0x0b, 0xa5, 0x17, 0x74, 0x1a, 0x69, 0x9c, 0x09, 0xf8, 0xf7, 0x40, 0xfa, 0xc4, 0x5a, 0x56
            , 0xed, 0x37, 0x44, 0xd2, 0x39, 0x09, 0x95, 0x13, 0xa5, 0xe3, 0x5b, 0xe2, 0xf5, 0xc8, 0xe7, 0x07
            , 0xe1, 0xa8, 0xbe, 0x8e, 0xf0, 0x33, 0xdb, 0x3a, 0x77, 0x36, 0x76, 0xc4, 0x6c, 0x60, 0xec, 0xf1
            , 0x45, 0xf7, 0x86, 0xe0, 0xcb, 0x90, 0xf9, 0xfe, 0xfb, 0x3a, 0x4a, 0x27, 0xbf, 0x75, 0xee, 0x38
            , 0x29, 0xcb, 0x83, 0x41, 0xc5, 0x9e, 0x1d, 0x07, 0x1d, 0x4e, 0xb7, 0xa6, 0x97, 0x2b, 0x15, 0xbc
            , 0xfa, 0x42, 0x4d, 0x00, 0x2b, 0xce, 0xfd, 0x29, 0xd2, 0x76, 0xfd, 0x32, 0x40, 0x92, 0x57, 0x35
            , 0xef, 0xe8, 0x79, 0xb2, 0x51, 0x1c, 0x81, 0x23, 0x55, 0x3d, 0x83, 0xf6, 0xe9, 0x45, 0x89, 0x02
            , 0x74, 0x6f, 0x00, 0x81, 0x91, 0xc4, 0x2a, 0xd2, 0x2f, 0x93, 0x00, 0xc4, 0xca, 0x78, 0x25, 0x97
            , 0x8e, 0x7c, 0x82, 0x9b, 0x76, 0x86, 0xcd, 0x85, 0xec, 0x7c, 0x5d, 0x60, 0xda, 0xca, 0xf3, 0x1e
            , 0x1a, 0xc8, 0x6d, 0xb0, 0x34, 0x6d, 0xf2, 0x2c, 0x0e, 0x75, 0x66, 0x0b, 0x6a, 0xde, 0x43, 0xb8
            , 0xeb, 0x5b, 0x01, 0xe4, 0x2e, 0x72, 0x33, 0xbe, 0xd0, 0x8f, 0x30, 0x84, 0x82, 0x17, 0x26, 0x9b
            , 0x56, 0x09, 0x95, 0xbd, 0x43, 0xea, 0xc0, 0x19, 0xdc, 0x37, 0x46, 0x03, 0x58, 0xf7, 0xf5, 0xaa
            , 0x4b, 0x44, 0xa6, 0x07, 0x97, 0x0a, 0x82, 0x7c, 0xb3, 0x0a, 0x07, 0xda, 0xae, 0xd3, 0x63, 0x74
            , 0xfa, 0xd1, 0xa8, 0x37, 0xf6, 0x2a, 0x31, 0xca, 0xdc, 0xea, 0xbd, 0x87, 0x0e, 0x05, 0xde, 0xbe
            , 0xb2, 0xa7, 0x09, 0x4f, 0x03, 0xb4, 0xce, 0x2b, 0x66, 0x36, 0xf9, 0xd2, 0xe1, 0xe7, 0x0d, 0xf0
            , 0x55, 0xf8, 0x04, 0xfb, 0xa4, 0x2b, 0xe5, 0xc2, 0xd1, 0x55, 0xc7, 0xef, 0x37, 0x0a, 0x6e, 0x30
            , 0xf3, 0xcc, 0x30, 0xff, 0xe3, 0xbb, 0x35, 0x2a, 0xa5, 0xaf, 0xd8, 0x41, 0x50, 0x2d, 0xea, 0xca
            , 0x84, 0x60, 0x95, 0x9f, 0xcd, 0x1d, 0x13, 0x39, 0xc1, 0x67, 0x62, 0xee, 0x82, 0x23, 0xfe, 0xd8
            , 0xfd, 0xd7, 0xb9, 0x64, 0x01, 0xa8, 0x22, 0x99, 0xf5, 0xc9, 0x19, 0xf3, 0xba, 0x4b, 0x0d, 0xfb
            , 0x96, 0x4b, 0x12, 0xf3, 0xf5, 0x18, 0xc7, 0x65, 0x51, 0xfd, 0x45, 0x2d, 0xa9, 0xe5, 0xdf, 0xb1
            , 0x8f, 0x2e, 0xbe, 0x3f, 0xe5, 0x6e, 0x4d, 0xed, 0x7a, 0x2c, 0xb5, 0xb8, 0x71, 0x4a, 0x2e, 0x0c
            , 0x89, 0xb8, 0xe6, 0x85, 0x48, 0x16, 0x8f, 0x35, 0x36, 0x7a, 0x09, 0x7e, 0x60, 0xde, 0x17, 0x71
            , 0xdd, 0xe8, 0xe7, 0xaa, 0x53, 0x2d, 0xb5, 0x55, 0x68, 0x04, 0xcb, 0x6b, 0xa8, 0x2b, 0xa1, 0xae
            , 0x3a, 0x21, 0x1e, 0xd0, 0x1b, 0x00, 0x5d, 0x4b, 0xc1, 0x7b, 0x6b, 0xba, 0x3b, 0x85, 0x0f, 0xda
            , 0xa3, 0x45, 0xd0, 0xc9, 0x18, 0x1b, 0x36, 0x41, 0x0e, 0xc0, 0x86, 0x61, 0x4f, 0xd5, 0xf1, 0xea
            , 0xfb, 0x39, 0xb4, 0x62, 0x6c, 0x10, 0x06, 0x44, 0x8b, 0x7b, 0x63, 0xe7, 0xc2, 0x6d, 0xa7, 0xfb
            , 0x66, 0x37, 0xdd, 0xd8, 0x8d, 0x4a, 0xd3, 0x3b, 0x2d, 0xe2, 0x2e, 0x3c, 0x72, 0xe5, 0x56, 0xee
            , 0x8a, 0x58, 0x7e, 0x2d, 0xab, 0xc4, 0xf6, 0xbe, 0x3f, 0x10, 0x34, 0x2c, 0x99, 0xa7, 0x0d, 0x86
            , 0x94, 0xa6, 0x23, 0xf1, 0x41, 0x64, 0xa4, 0x7d, 0xa5, 0x9e, 0x8e, 0x76, 0x5d, 0x0e, 0x8e, 0xad
    };

    public static final int[] encryptedData2 = {
            0x6c, 0x80, 0xde, 0xf3, 0x29, 0x89, 0xcd, 0x44, 0xe1, 0x30, 0x52, 0x65, 0x9f, 0x13, 0x72, 0x36,
            0x13, 0x71, 0x23, 0xb0, 0x88, 0x47, 0x68, 0xab, 0x59, 0x7f, 0x64, 0x18, 0x29, 0xa5, 0xb7, 0xc2,
            0x9a, 0x2f, 0x6c, 0xa1, 0x73, 0xb9, 0x86, 0x54, 0x2c, 0x20, 0x43, 0xdd, 0xe8, 0x32, 0xb5, 0xf0,
            0x34, 0xcd, 0xc0, 0x28, 0x6a, 0xa3, 0xa3, 0x13, 0xce, 0xed, 0x45, 0xd7, 0x55, 0x84, 0x6a, 0x47,
            0xa5, 0x2e, 0xd0, 0xd4, 0x2e, 0x75, 0x9c, 0x80, 0x4d, 0x4c, 0xa3, 0x9a, 0x06, 0x13, 0xd5, 0xde,
            0xb9, 0x15, 0x02, 0x39, 0xd0, 0x23, 0xf3, 0xf4, 0x12, 0x7f, 0x77, 0xea, 0x52, 0x69, 0x1a, 0xf4,
            0xa4, 0xaf, 0xb7, 0xe8, 0x20, 0x97, 0xc2, 0x4b, 0x49, 0x09, 0x92, 0x9e, 0x2e, 0x36, 0x61, 0x65,
            0x40, 0x85, 0x50, 0x4e, 0xb2, 0x77, 0x53, 0x16, 0x33, 0x28, 0xea, 0x59, 0x41, 0x3b, 0x54, 0x54,
            0x4d, 0xe5, 0xc5, 0x2c, 0xfd, 0xf8, 0x02, 0x5b, 0x8d, 0x25, 0xfc, 0x2b, 0x8f, 0xd4, 0x54, 0xc4,
            0x36, 0xd5, 0x42, 0x12, 0x94, 0x4e, 0x48, 0x50, 0xf1, 0x0d, 0x66, 0x22, 0x60, 0x5d, 0x7e, 0x29,
            0xcc, 0xd8, 0x06, 0x09, 0x52, 0xaa, 0x7c, 0x37, 0x25, 0x6d, 0xda, 0xb7, 0x36, 0x83, 0x8b, 0x02,
            0x4b, 0x4a, 0x1b, 0xb6, 0x1c, 0xe8, 0x7b, 0xd2, 0xd2, 0x6b, 0x90, 0xb9, 0xe6, 0x87, 0x8d, 0x2a,
            0x28, 0xda
    };

    @Test
    public void encrypt() throws Exception {
        PublicKey key = getKey();
        byte[] data = new byte[190];
        for (int i = 0; i < 190; i++) {
            data[i] = (byte) encryptedData2[i];
        }

        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] decryptData = cipher.doFinal(data);
        String s = new String(decryptData);
        System.out.println(s);

        System.out.println(Arrays.toString(encryptedData2));
        System.out.println(Arrays.toString(data));
    }

    @Test
    public void decrypt() {

    }

    // PKCS#8 1024bit
    public static final String base64PublicKey =
            "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCz9deoUdYoDrIh7kbsQDoU2sb/" +
                    "oEAz28mirkMNYY36aF3RCfuv1kKzlZLnb1g9reKPWlFjYhhun77Fm9CJTl4kRBNT" +
                    "o58bY7wNr9qeoHpd+6apxHdTt6DIxS7VCDgLPapUrjK5GnI1ivaYW505MwVp3ddW" +
                    "Ia0SiWyEaFlytuZWgQIDAQAB";

    public static final String base64PrivateKey =
            "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBALP116hR1igOsiHu" +
                    "RuxAOhTaxv+gQDPbyaKuQw1hjfpoXdEJ+6/WQrOVkudvWD2t4o9aUWNiGG6fvsWb" +
                    "0IlOXiREE1OjnxtjvA2v2p6gel37pqnEd1O3oMjFLtUIOAs9qlSuMrkacjWK9phb" +
                    "nTkzBWnd11YhrRKJbIRoWXK25laBAgMBAAECgYAAhCgGN5Xhr4kbR8+0Rcrk3GvN" +
                    "t+v3Oh4t9UoD88+wdDQwsVKjgn3WfgtZ1pvuMV2BA8VP/6wKRTMK5hOHEU570upV" +
                    "iE0RAbjJIr9Tl9/OacUBV3kGPxamkMPoJhV6DTMpwTe6Ha2/5a4XuSOaaG5UTkcK" +
                    "7w1yVn806niiWECtsQJBAOQbzQMOit2tmhEkkwiKIqdyauQRTxvAnUATNOcFiRYa" +
                    "RtoQ8keiZF7SIfcXLDLMQThjRpJ/LZmKQKBYK07bYisCQQDJ9uqHPRTlqISYooSC" +
                    "+AK3CeGhQv8CJLnurXHYLOCNZecutb92Zi6LebNajB1LeYRqbPbrQcnHpyZDK74H" +
                    "VJADAkA1Vx7okRT942N8kL3lKFAUMdg2/qkuByt+WT9sqkm1Jm3c/kt5XsrztVWF" +
                    "7yBBvKufoO9WwHCMT9zu4c82wGxDAkA3AhOaxh489ws2b31cFqTWqdBUleTS9qHV" +
                    "ylPppz96A7lV1ZbSbr5aqskY7nTEK9LPEeKm7QJCaNpZDT7yzCt1AkEAvppBKX2D" +
                    "Ga6ffSNAekpEjamjDOwBU3nDftBhRkArL0s1sWzvzlEt6ZRcunCrDpAZ/7ucE9Tp" +
                    "iGVUnSGuCHvFJg==";

    public static final String myData = "hello, world!";

    // BASE64
    public static final String myEncryptData = "f21S8Ptht6rDE9s9MBMX0QIJNtWXJ4mXYWL3W" +
            "A9hgOgf6VWwb6h7ekEbXe2nNsFdKteopruxvqtJdiyMzTeuUohTM4d6CyG13Ea/3GSxXdwyzz" +
            "jZE1i/g2FnWR04AiWEun809oClcwYbyHk/Rk3/zoCXO21XkvvaHFI1W6zApYM=";

    // 私钥加密。
    @Test
    public void myEncrypt() throws Exception {
        byte[] keyB = Base64.getDecoder().decode(base64PrivateKey);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyB);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        RSAPrivateKey privateKey = (RSAPrivateKey) keyFactory.generatePrivate(keySpec);

        // 加密。
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);
        byte[] dd = cipher.doFinal(myData.getBytes());

        System.out.println(Arrays.toString(dd));

        byte[] bbb = Base64.getEncoder().encode(dd);
        System.out.println(new String(bbb));
    }

    @Test
    public void myDecrypt() throws Exception {
        byte[] keyB = Base64.getDecoder().decode(base64PublicKey);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyB);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        RSAPublicKey publicKey = (RSAPublicKey) keyFactory.generatePrivate(keySpec);

        // 加密。
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, publicKey);
        byte[] dd = cipher.doFinal(myEncryptData.getBytes());

        System.out.println(Arrays.toString(dd));
        System.out.println(new String(dd));
    }

    @Test
    public void sshKeyTest() throws Exception {
        // data
        String rawText = "hello, world!";

        // key file
//        String head = "gitlab_cvte_huangye";
        String head = "github";
        String keyPath = "/Users/huangye/.ssh/" + head + "_id_rsa";
        FileReader fileReader = new FileReader(keyPath);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String line;
        StringBuilder sb = new StringBuilder();
        while ((line = bufferedReader.readLine()) != null) {
            if (line.startsWith("-")) {
                continue;
            }
            sb.append(line.trim());
        }
        String base64key = sb.toString();
        bufferedReader.close();
        fileReader.close();

        String pubPath = "/Users/huangye/.ssh/" + head + "_id_rsa.pub";
        fileReader = new FileReader(pubPath);
        bufferedReader = new BufferedReader(fileReader);
        String base64pub = bufferedReader.readLine().split(" ")[1];
        bufferedReader.close();
        fileReader.close();

        System.out.println("key: " + base64key);

        byte[] key = Base64.getDecoder().decode(base64key);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(key);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        RSAPrivateKey privateKey = (RSAPrivateKey) keyFactory.generatePrivate(keySpec);

        // 加密。
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);
        byte[] encryptBytes = cipher.doFinal(rawText.getBytes());
        byte[] encryptText = Base64.getEncoder().encode(encryptBytes);

        System.out.println("encrypt: " + new String(encryptText));

        System.out.println("pub: " + base64pub);
        byte[] pub = Base64.getDecoder().decode(base64pub);
        keySpec = new PKCS8EncodedKeySpec(pub);
        keyFactory = KeyFactory.getInstance("RSA");
        RSAPublicKey publicKey = (RSAPublicKey) keyFactory.generatePrivate(keySpec);

        // 加密。
        cipher.init(Cipher.DECRYPT_MODE, publicKey);
        byte[] dd = cipher.doFinal(myEncryptData.getBytes());

        System.out.println("decrypt: " + new String(dd));
    }

    @Test
    public void sshKeyTest2() throws Exception {
        // data
        String rawText = "hello, world!";

        // key file
        String base64key = base64PrivateKey;
        String base64pub = base64PublicKey;

        System.out.println("key: " + base64key);

        byte[] key = Base64.getDecoder().decode(base64key);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(key);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        RSAPrivateKey privateKey = (RSAPrivateKey) keyFactory.generatePrivate(keySpec);

        // 加密。
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);
        byte[] encryptBytes = cipher.doFinal(rawText.getBytes());
        byte[] encryptText = Base64.getEncoder().encode(encryptBytes);

        System.out.println("encrypt: " + new String(encryptText));

        System.out.println("pub: " + base64pub);
        byte[] pub = Base64.getDecoder().decode(base64pub);
        keySpec = new PKCS8EncodedKeySpec(pub);
        keyFactory = KeyFactory.getInstance("RSA");
        RSAPublicKey publicKey = (RSAPublicKey) keyFactory.generatePrivate(keySpec);

        // 加密。
        cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, publicKey);
        byte[] dd = cipher.doFinal(myEncryptData.getBytes());

        System.out.println("decrypt: " + new String(dd));
    }
}
