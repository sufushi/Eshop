package com.rdc.shop.eshop.bean;

import java.util.Date;

public class Comment {

    private Long id;
    private Long rate;
    private String content;
    private Date commentTime;
    private Order order;

    public Comment() {
    }

    public Comment(Long id, Long rate, String content, Date commentTime, Order order) {
        this.id = id;
        this.rate = rate;
        this.content = content;
        this.commentTime = commentTime;
        this.order = order;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRate() {
        return rate;
    }

    public void setRate(Long rate) {
        this.rate = rate;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCommentTime() {
        return commentTime;
    }

    public void setCommentTime(Date commentTime) {
        this.commentTime = commentTime;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", rate=" + rate +
                ", content='" + content + '\'' +
                ", commentTime=" + commentTime +
                ", order=" + order +
                '}';
    }
}
