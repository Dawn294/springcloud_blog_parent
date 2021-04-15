package com.czh.common.enums;

/**
 * @author Dawn294
 * @Description
 * @date 2021/4/15
 */
public enum EsActiveEnum {
    UP(1),
    DOWN(0);

    private Integer code;

    EsActiveEnum() {
    }

    EsActiveEnum(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }}
