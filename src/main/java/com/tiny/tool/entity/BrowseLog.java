package com.tiny.tool.entity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * @author mzl 浏览日志对象
 */
public class BrowseLog {


    /**
     * 序号
     */
    private String no;
    /**
     * 日志字符串
     */
    private String log;

    /**
     * 请求的URI和HTTP协议
     */
    private String request;

    /**
     * 访问时间和时区
     */
    private String timeLocal;

    /**
     * 客户端地址
     */
    private String remoteAddr;

    /**
     * HTTP请求状态
     */
    private String status;

    /**
     * 发送给客户端文件内容大小
     */
    private String bodyBytesSent;

    /**
     * url跳转来源
     */
    private String httpReferer;

    /**
     * 用户终端浏览器等信息
     */
    private String httpUserAgent;
    /**
     * 请求过程中，upstream响应时间
     */
    private String httpXForwardedFor;

    /**
     * 客户端用户名称
     */
    private String remoteUser;

    /**
     * 请求方式
     */
    private String action;

    public BrowseLog() {

    }
    public BrowseLog(String no, String log) {
        this.no = no;
        this.log = log;
        String sign = " - ";
        int index = log.indexOf(sign);
        if (index > -1) {
            this.remoteAddr = log.substring(0, index);
            log = log.substring(index + sign.length());
            sign = " [";
            index = log.indexOf(sign);
            if (index > -1) {
                this.remoteUser = log.substring(0, index);
                log = log.substring(index + sign.length());
                sign = "] \"";
                index = log.indexOf(sign);
                if (index > -1) {
                    this.timeLocal = log.substring(0, index);
                    log = log.substring(index + sign.length());
                    sign = "\" ";
                    index = log.indexOf(sign);
                    if (index > -1) {
                        this.request = log.substring(0, index);
                        log = log.substring(index + sign.length());
                        sign = " ";
                        index = log.indexOf(sign);
                        if (index > -1) {
                            this.status = log.substring(0, index);
                            log = log.substring(index + sign.length());
                            sign = " \"";
                            index = log.indexOf(sign);
                            if (index > -1) {
                                this.bodyBytesSent = log.substring(0, index);
                                log = log.substring(index + sign.length());
                                sign = "\" \"";
                                index = log.indexOf(sign);
                                if (index > -1) {
                                    this.httpReferer = log.substring(0, index);
                                    log = log.substring(index + sign.length());
                                    sign = "\"";
                                    index = log.indexOf(sign);
                                    if (index > -1) {
                                        this.httpUserAgent = log.substring(0, index);
                                        log = log.substring(index + sign.length());
                                        if (log.length() > 0) {
                                            this.httpUserAgent = log;
                                        }
                                    }
                                }
                            } else {
                                this.bodyBytesSent = log;
                                if (this.bodyBytesSent.equals("-")) {
                                    this.bodyBytesSent = "";
                                    this.httpReferer = "-";
                                }
                            }
                        }
                    }
                }

            }
        }
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }
    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }


    public String getTimeLocal() {
        if (this.timeLocal != null && this.timeLocal.length() > 0) {
            DateTimeFormatter originFormatter = DateTimeFormatter.ofPattern("dd/MMM/yyyy:HH:mm:ss", Locale.US);
            //将时间格式字符串转化为LocalDateTime对象，需传入日期对象格式化器
            LocalDateTime parseDate = LocalDateTime.parse(timeLocal.split(" ")[0], originFormatter);
            DateTimeFormatter targetFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            return parseDate.format(targetFormatter);
        }
        return "";
    }


    public String getRemoteUser() {
        return remoteUser;
    }

    public void setRemoteUser(String remoteUser) {
        this.remoteUser = remoteUser;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public String getRequest() {
        if(this.request!=null && this.request.length()>0){
            return this.request.split(" ")[1];
        }
        return "";
    }

    public void setTimeLocal(String timeLocal) {
        this.timeLocal = timeLocal;
    }

    public String getRemoteAddr() {
        return remoteAddr;
    }

    public void setRemoteAddr(String remoteAddr) {
        this.remoteAddr = remoteAddr;
    }

    public String getBodyBytesSent() {
        return bodyBytesSent;
    }

    public void setBodyBytesSent(String bodyBytesSent) {
        this.bodyBytesSent = bodyBytesSent;
    }

    public String getHttpReferer() {
        return httpReferer;
    }

    public void setHttpReferer(String httpReferer) {
        this.httpReferer = httpReferer;
    }

    public String getHttpUserAgent() {
        return httpUserAgent;
    }

    public void setHttpUserAgent(String httpUserAgent) {
        this.httpUserAgent = httpUserAgent;
    }

    public String getHttpXForwardedFor() {
        return httpXForwardedFor;
    }

    public void setHttpXForwardedFor(String httpXForwardedFor) {
        this.httpXForwardedFor = httpXForwardedFor;
    }

    public String getAction() {
        if(this.request!=null && this.request.length()>0){
            return this.request.split(" ")[0];
        }
        return "";
    }

}
