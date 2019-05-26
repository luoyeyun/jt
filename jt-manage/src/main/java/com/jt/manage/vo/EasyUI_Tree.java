package com.jt.manage.vo;

/**
 * 封装页面展示商品分类树结构
 */
public class EasyUI_Tree {
    private Long id;    //id
    private String text;    //名字--文本信息
    private String state;   //是否展开树结构   open--展开  close--关闭

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public EasyUI_Tree() {
    }

    public EasyUI_Tree(Long id, String text, String state) {
        this.id = id;
        this.text = text;
        this.state = state;
    }

    @Override
    public String toString() {
        return "EasyUI_Tree{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", state='" + state + '\'' +
                '}';
    }
}
