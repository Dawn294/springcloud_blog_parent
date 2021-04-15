package com.czh.common.enums;

/**
 * @author Dawn294
 * @Description
 * @date 2021/4/15
 */
public enum  EsTypeEnum {
    OWN(1),
    OTHER(0);

    private Integer code;

    EsTypeEnum() {
    }

    EsTypeEnum(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }}
