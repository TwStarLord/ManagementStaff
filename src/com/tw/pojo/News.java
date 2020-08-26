package com.tw.pojo;

import java.io.Serializable;

public class News implements Serializable {

    private Long id;
    private String sender;
    private Integer senderdepartid;
    private String recordtime;
    private String operation;
    private String receiver;
    private Integer receiverdepartid;
    private String status;
    private String isread;

    public Long getId() {
        return id;
    }

    public String getIsread() {
        return isread;
    }

    public void setIsread(String isread) {
        this.isread = isread;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public Integer getSenderdepartid() {
        return senderdepartid;
    }

    public void setSenderdepartid(Integer senderdepartid) {
        this.senderdepartid = senderdepartid;
    }

    public String getRecordtime() {
        return recordtime;
    }

    public void setRecordtime(String recordtime) {
        this.recordtime = recordtime;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public Integer getReceiverdepartid() {
        return receiverdepartid;
    }

    public void setReceiverdepartid(Integer receiverdepartid) {
        this.receiverdepartid = receiverdepartid;
    }

    public String getStatus() {
        return status;
    }

    private String descripetion;

    public String getDescripetion() {
        return descripetion;
    }

    public void setDescripetion(String descripetion) {
        this.descripetion = descripetion;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public News() {
    }

//    确认出差安排


    public News(Long id, String status, String isread) {
        this.id = id;
        this.status = status;
        this.isread = isread;
    }

    public News(String sender, Integer senderdepartid, String recordtime, String operation, String receiver, Integer receiverdepartid, String status, String isread, String descripetion) {
        this.sender = sender;
        this.senderdepartid = senderdepartid;
        this.recordtime = recordtime;
        this.operation = operation;
        this.receiver = receiver;
        this.receiverdepartid = receiverdepartid;
        this.status = status;
        this.isread = isread;
        this.descripetion = descripetion;
    }

    @Override
    public String toString() {
        return "Information{" +
                "id=" + id +
                ", sender='" + sender + '\'' +
                ", senderdepartid=" + senderdepartid +
                ", recordtime='" + recordtime + '\'' +
                ", operation='" + operation + '\'' +
                ", receiver='" + receiver + '\'' +
                ", receiverdepartid=" + receiverdepartid +
                ", status=" + status +
                '}';
    }
}
