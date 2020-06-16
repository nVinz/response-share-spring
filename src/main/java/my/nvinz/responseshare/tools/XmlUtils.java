package my.nvinz.responseshare.tools;

import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class XmlUtils {

    public static boolean isXml(String requestBody) {
        try {
            SAXParser saxParser = SAXParserFactory.newInstance().newSAXParser();
            InputStream stream = new ByteArrayInputStream(requestBody.getBytes("UTF-8"));
            saxParser.parse(stream, new DefaultHandler());
            return true;
        } catch (Exception e) {
            // not valid XML String
            return false;
        }
    }

}
