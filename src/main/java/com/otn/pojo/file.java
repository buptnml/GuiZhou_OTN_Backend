package com.otn.pojo;

/**
 * @Auther: 李景然
 * @Date: 2018/7/19 22:17
 * @Description:
 */

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;



@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "file")
public class file{
    @XmlElement(name = "file_name")
    private String fileName;
    @XmlElement(name = "file_type")
    private String fileType;
    @XmlElement(name = "file_stream")
    private String data;

    public file() {
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public file(String fileName, String fileType, String data) {
        this.fileName = fileName;
        this.fileType = fileType;
        this.data = data;
    }
}
