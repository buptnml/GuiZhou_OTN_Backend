package com.bupt.pojo;

/**
 * OSNR值的计算结果类
 * 计算结果一旦生成，不许更改
 * 构造器设计为： 要么有OSNR的值，要么有错误信息，两者不同时存在，且必须存在其一
 */
final public class OSNRResult {
    private final String netElementName;
    private final Double result;

    public OSNRResult(String netElementName, Double result) {
        this.netElementName = netElementName;
        this.result = result;
    }

    public String getNetElementName() {
        return netElementName;
    }

    public Double getResult() {
        return result;
    }
}

