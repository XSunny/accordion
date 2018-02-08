package com.bsoft.accordion.core.task;

/**
 * Created by sky on 2018/2/7.
 */
public class Link {

    private String head;

    private String direction;

    public Link() {
    }

    public Link(String head, String direction) {
        this.head = head;
        this.direction = direction;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }
}
