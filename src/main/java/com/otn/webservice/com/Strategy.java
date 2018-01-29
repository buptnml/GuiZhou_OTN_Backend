package com.otn.webservice.com;


import com.otn.webservice.com.pojo.RawAmpData;
import com.otn.webservice.com.pojo.RawBussinessData;
import com.otn.webservice.com.pojo.RawLinkData;
import com.otn.webservice.com.pojo.RawNetElementData;

import java.util.List;


public interface Strategy {
    List<RawLinkData> getRawLinkData();

    List<RawAmpData> getRawAmpData();

    List<RawBussinessData> getRawBusData();

    List<RawNetElementData> getRawNetElementData();
}
