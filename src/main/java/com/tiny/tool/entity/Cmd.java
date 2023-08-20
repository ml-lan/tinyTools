package com.tiny.tool.entity;

/**
 * @author mzl 脚本实体
 */
public class Cmd{

    public Cmd(){

    }
    private String id;
    /**
     * 名称
     */
    private String name;
    /**
     * 路径
     */
    private String path;

    /**
     * 描述
     */
    private String describe;

    /**
     * 端口
     */
    private String port;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }


    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }
}
