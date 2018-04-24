package com.hy.parser;

import org.junit.Test;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 * Created by Administrator on 2018/4/24.
 *
 * @author hy 2018/4/24
 */
public class WebSvgToAndroid {

    @Test
    public void test() {
        try {
            main(new String[]{"123"});
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String svgTextFilePath = "G:\\AndroidStudioProject2\\LifeHelper" +
            "\\app\\src\\main\\assets\\svg.txt";
    public static String outputPath = "G:\\AndroidStudioProject2\\LifeHelper\\app\\" +
            "src\\main\\res\\drawable";
    public static String dp = "48";

    public static void main(String[] args) throws Exception {
        File file = new File(svgTextFilePath);
        FileInputStream fis = new FileInputStream(file);

        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser parser = factory.newSAXParser();
        XMLReader reader = parser.getXMLReader();

        reader.setContentHandler(new H());

        reader.parse(new InputSource(fis));

        fis.close();
    }

    static class H extends DefaultHandler {

        static final String m1 = "<vector xmlns:android=\"http://schemas.android.com/apk/res/android\"\n" +
                "        android:width=\"48dp\"\n" +
                "        android:height=\"48dp\"\n" +
                "        android:viewportHeight=\"1024.0\"\n" +
                "        android:viewportWidth=\"1024.0\">\n";

        static final String m2 = "</vector>";
        static final String m3 = "  <path\n" +
                "        android:fillColor=\"";
        static final String m4 = "\"\n        android:pathData=\"";
        static final String m5 = "\"/>\n";

        File file;
        FileOutputStream fos;

        @Override
        public void startElement(String uri, String localName, String qName,
                                 Attributes attributes) throws SAXException {
            try {
                if (qName.equals("symbol")) {
                    if (fos != null) {
                        fos.write(m2.getBytes());
                    }
                    String fileName = attributes.getValue(0).replace('-', '_');
                    if (fos != null) {
                        fos.close();
                    }
                    file = new File(outputPath + "\\" + fileName + ".xml");
                    fos = new FileOutputStream(file);
                    System.out.println(fos);
                    fos.write(m1.getBytes());

                } else if (qName.equals("path")) {
                    String data = attributes.getValue(0).replaceAll(" ", ",");
                    String color = attributes.getValue(1);
                    fos.write(m3.getBytes());
                    fos.write(color.getBytes());
                    fos.write(m4.getBytes());
                    fos.write(data.getBytes());
                    fos.write(m5.getBytes());
                }
            } catch (Exception e) {
//                e.printStackTrace();
            }
        }
    }

}
