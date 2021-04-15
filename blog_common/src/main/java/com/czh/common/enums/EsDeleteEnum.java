package com.czh.common.enums;

/**
 * @author Dawn294
 * @Description
 * @date 2021/4/15
 */
public enum EsDeleteEnum {
    IS_DELETE(1),
    NOT_DELETE(0);

    private Integer code;

    EsDeleteEnum() {
    }

    EsDeleteEnum(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }}
