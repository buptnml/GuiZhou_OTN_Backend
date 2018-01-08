package com.bupt.webservice.com.pojo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component("WebServiceConfigInfo")
public class WebServiceConfigInfo {
    @Value("${basic_version_id}")
    private Long BASIC_VERSION_ID;
    @Value("${default_net_element_type}")
    private String DEFAULT_NET_ELEMENT_TYPE;
    @Value("${default_link_type}")
    private String DEFAULT_LINK_TYPE;
    @Value("${default_link_length}")
    private Float DEFAULT_LINK_LENGTH;
    @Value("${default_link_loss}")
    private Float DEFAULT_LINK_LOSS;
    @Value("${default_bussiness_rate}")
    private String DEFAULT_BUSSINESS_RATE;

    public Long getBASIC_VERSION_ID() {
        return BASIC_VERSION_ID;
    }

    public String getDEFAULT_NET_ELEMENT_TYPE() {
        return DEFAULT_NET_ELEMENT_TYPE;
    }

    public String getDEFAULT_LINK_TYPE() {
        return DEFAULT_LINK_TYPE;
    }

    public Float getDEFAULT_LINK_LENGTH() {
        return DEFAULT_LINK_LENGTH;
    }

    public Float getDEFAULT_LINK_LOSS() {
        return DEFAULT_LINK_LOSS;
    }

    public String getDEFAULT_BUSSINESS_RATE() {
        return DEFAULT_BUSSINESS_RATE;
    }
}
