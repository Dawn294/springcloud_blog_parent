package com.czh.admin.entity;

import lombok.Data;

import java.util.Date;

/**
 * @auth admin
 * @date
 * @Description
 */
@Data
public class EmailEntity {

    private String from;
    private String replyTo;
    private String[] to;
    private String[] cc;
    private String[] bcc;
    private Date sentDate;
    private String subject;
    private String text;

}
