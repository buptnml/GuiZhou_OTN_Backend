package com.otn.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import sun.misc.BASE64Encoder;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @Auther: 李景然
 * @Date: 2018/7/18 19:35
 * @Description:
 */
public class FileHelper {

    private static Logger LOGGER = LoggerFactory.getLogger(FileHelper.class);


    public static String encodeBase64File(String path) throws Exception {
        File file = new File(path);
        FileInputStream inputFile = new FileInputStream(file);
        byte[] buffer = new byte[(int) file.length()];
        inputFile.read(buffer);
        inputFile.close();
        return new BASE64Encoder().encode(buffer);
    }


    public static String createXMLStreamForUpload(String fileName, String fileType, String data) {
        String xmlString = "";
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.newDocument();
            document.setXmlStandalone(true);

            Element root = document.createElement("data");
            document.appendChild(root);

            Element childFile = document.createElement("file");
            root.appendChild(childFile);


            Element file_name = document.createElement("file_name");
            file_name.setTextContent(fileName);

            Element file_type = document.createElement("file_type");
            file_type.setTextContent(fileType);

            Element file_steam = document.createElement("file_steam");
            file_steam.setTextContent(data);


            childFile.appendChild(file_name);
            childFile.appendChild(file_type);
            childFile.appendChild(file_steam);

            TransformerFactory transFactory = TransformerFactory.newInstance();
            Transformer transformer = transFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource domSource = new DOMSource(document);

            // xml transform String
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            transformer.transform(domSource, new StreamResult(bos));
            xmlString = bos.toString("utf-8");
            LOGGER.warn(xmlString.toString());
        } catch (Exception e) {
            LOGGER.warn(e.getMessage());
        }
        return xmlString;
    }

    public static String saveUrlAs(String url) {
        String filePath = System.getProperty("catalina.home") + "\\csvURL\\";
        //String filePath ="d:/csvURL/";
        String fileName = getFilePath(url, filePath);
        //创建不同的文件夹目录
        File file = new File(filePath);
        //判断文件夹是否存在
        if (!file.exists()) {
            //如果文件夹不存在，则创建新的的文件夹
            file.mkdirs();
        }
        FileOutputStream fileOut = null;
        HttpURLConnection conn = null;
        InputStream inputStream = null;
        try {
            // 建立链接
            URL httpUrl = new URL(url);
            conn = (HttpURLConnection) httpUrl.openConnection();
            //以Post方式提交表单，默认get方式
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            // post方式不能使用缓存
            conn.setUseCaches(false);
            //连接指定的资源
            conn.connect();
            //获取网络输入流
            inputStream = conn.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(inputStream);
            //判断文件的保存路径后面是否以/结尾
            if (!filePath.endsWith("/")) {
                filePath += "/";
            }
            //写入到文件（注意文件保存路径的后面一定要加上文件的名称）
            fileOut = new FileOutputStream(fileName);
            BufferedOutputStream bos = new BufferedOutputStream(fileOut);

            byte[] buf = new byte[4096];
            int length = bis.read(buf);
            //保存文件
            while (length != -1) {
                bos.write(buf, 0, length);
                length = bis.read(buf);
            }
            bos.close();
            bis.close();
            conn.disconnect();
        } catch (Exception e) {
            LOGGER.warn(e.getMessage());
            //e.printStackTrace();
        }
        return fileName;
    }

    private static String getFilePath(String url, String filePath) {
        String[] strArr = url.split("/");
        String fileName = strArr[strArr.length - 1];
        return filePath + fileName;
    }
}
