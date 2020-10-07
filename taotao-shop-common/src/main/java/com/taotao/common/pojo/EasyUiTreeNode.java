package com.taotao.common.pojo;

import java.io.Serializable;

/**
 * 商品类目
 * @author xxbb
 */
public class EasyUiTreeNode implements Serializable {
    /**
     * 商品类目Id
     */
    private long id;
    /**
     * 商品类目描述
     */
    private String text;
    /**
     * 是否存在子节点
     */
    private String state;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "EasyUiTreeNode{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", state='" + state + '\'' +
                '}';
    }
}

