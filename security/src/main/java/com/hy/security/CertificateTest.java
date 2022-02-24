package com.hy.security;

import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.security.PublicKey;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPublicKey;
import java.util.Arrays;
import java.util.Base64;

/**
 * 测试读取证书。
 *
 * @author hy 2018/1/25
 */
public class CertificateTest {

    //    public static final String BAIDU_CERTIFICATE_PATH = "E:\\密钥证书\\baidu.cer";
    public static final String BAIDU_CERTIFICATE_PATH = "/Users/huangye/tmp/baidu.com.cer";

    @Test
    public void readCertificate1() throws Exception {
        File file = new File(BAIDU_CERTIFICATE_PATH);
        FileInputStream fis = new FileInputStream(file);

        CertificateFactory certificateFactory = CertificateFactory.getInstance("X509");
        X509Certificate cert = (X509Certificate) certificateFactory.generateCertificate(fis);

        System.out.println(cert.toString());

        // 公钥
        PublicKey publicKey = cert.getPublicKey();
        byte[] bytes = publicKey.getEncoded();
        System.out.println(Arrays.toString(bytes));
        System.out.println(Base64.getEncoder().encodeToString(publicKey.getEncoded()));

        fis.close();
    }

    // 理解base64的证书文件pem里面的到底什么内容什么意思
    @Test
    public void readCertificate2() throws Exception {
        File file = new File("/Users/huangye/work/remote/cert/mitmproxy-ca-cert.pem");

        CertificateFactory factory = CertificateFactory.getInstance("X.509");
        X509Certificate certificate = (X509Certificate) factory.generateCertificate(new FileInputStream(file));
        PublicKey publicKey = certificate.getPublicKey();
        System.out.println("publicKey class: \n" + publicKey.getClass());

        RSAPublicKey rsaPublicKey = (RSAPublicKey) publicKey;
        System.out.println("publicKey: \n" + rsaPublicKey);

        byte[] bs = rsaPublicKey.getEncoded();
        System.out.println("bs: \n" + bs.length);

        System.out.println("publicKey: \n" + Base64.getEncoder().encodeToString(rsaPublicKey.getEncoded()));
    }


}


























