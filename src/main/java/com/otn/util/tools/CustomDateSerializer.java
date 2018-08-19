package com.otn.util.tools;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;


/**
 * 自定义返回JSON 数据格中日期格式化处理
 */
public class CustomDateSerializer extends JsonSerializer<Date> {
    @Override
    public void serialize(Date value, JsonGenerator jgen,
                          SerializerProvider provider) throws IOException {
        SimpleDateFormat df2 = new SimpleDateFormat("yyyy年MM月dd日");
        df2.setTimeZone(TimeZone.getTimeZone("GMT"));
        String formattedDate = df2.format(value);
        jgen.writeString(formattedDate);
    }

}