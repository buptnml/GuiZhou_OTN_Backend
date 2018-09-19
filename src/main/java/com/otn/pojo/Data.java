package com.otn.pojo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * @Auther: 李景然
 * @Date: 2018/7/19 22:16
 * @Description:
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "data")
public class Data implements Serializable {
    private static final long serialVersionUID = 1L;

    public Data() {
    }

    public com.otn.pojo.file getFile() {
        return file;
    }

    public void setFile(com.otn.pojo.file file) {
        this.file = file;
    }

    @XmlElement(name = "file")
    private file file;

    public Data(String fileName,String fileType,String data) {
        this.file = new file(fileName,fileType,data);
    }

}