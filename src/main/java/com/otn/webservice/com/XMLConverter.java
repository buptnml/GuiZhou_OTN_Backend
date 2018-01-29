package com.otn.webservice.com;

import com.otn.webservice.com.pojo.WrappedXml;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.StringReader;
import java.util.List;


public class XMLConverter {
    public XMLConverter() {
    }

    public List getData(String data, REQUEST_TYPES ResourceType) {
        return getData(data, ResourceType, "stream");
    }

    /**
     * 根据请求的路径（或字符串）来解析xml
     *
     * @param path
     * @param ResourceType 对应枚举类型
     * @param RequestType
     * @return 返回对应rawdata类型的list
     */
    public List getData(String path, REQUEST_TYPES ResourceType, String RequestType) {
        try {
            WrappedXml result = xml2Object(path, WrappedXml.class, RequestType);
            if (result == null)
                throw new Exception("请检查连接是否正确");
            else {
                switch (ResourceType) {
                    case amp: {
                        return result.getAmpDataList();
                    }
                    case bussiness: {
                        return result.getBussinessDataList();
                    }
                    case netElement: {
                        return result.getNetElementDataList();
                    }
                    case link: {
                        return result.getlinkDataList();
                    }
                    default: {
                        throw new Exception("未找到对应类型，请检查ResourceType与连接是否对应正确");
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private <T> T xml2Object(String xmlStr, Class<T> c, String type) {
        try {
            JAXBContext context = JAXBContext.newInstance(c);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            T t;
            switch (type) {
                case "file": {
                    t = (T) unmarshaller.unmarshal(new File(xmlStr));
                    break;
                }
                case "stream": {
                    t = (T) unmarshaller.unmarshal(new StringReader(xmlStr));
                    break;
                }
                default: {
                    throw new Exception("unspecified type:" + type);
                }
            }
            return t;

        } catch (JAXBException e) {
            e.printStackTrace();
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }


    /**
     * 请传入对应枚举类型来返回对应xml数据
     */
    public enum REQUEST_TYPES {
        link, netElement, amp, bussiness
    }
}