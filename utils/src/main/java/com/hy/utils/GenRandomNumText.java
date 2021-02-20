package com.hy.utils;

import org.junit.Test;

import java.io.File;
import java.io.FileWriter;
import java.util.Random;

/**
 * 生成随机数文本工具
 *
 * @author huangye
 */
public class GenRandomNumText {

    @Test
    public void gen() {
        System.out.println("start");
        long start = System.currentTimeMillis();

        Random random = new Random();

        File folder = new File("random");
        if (folder.exists()) {
            folder.delete();
        }
        folder.mkdir();

        for (int i = 0; i < 10; i++) {
            try (FileWriter fw = new FileWriter(new File("random/random" + i + ".txt"))) {
                for (int j = 0; j < 200; j++) {
                    fw.append("no." + j + "\n{\n");
                    for (int k = 0; k < 20; k++) {
                        for (int l = 0; l < 10; l++) {
                            int r = random.nextInt(256);

                            if (r < 10) {
                                fw.append("  ");
                            } else if (r < 100) {
                                fw.append(" ");
                            }
                            fw.append(String.valueOf(r));

                            if (k == 19 && l == 9) {
                                fw.append("   ");
                            } else {
                                fw.append(",  ");
                            }
                        }
                        fw.append("\n");
                    }
                    fw.append("}\n\n");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        System.out.println("end. cost: " + (System.currentTimeMillis() - start) + "ms");

    }
}
