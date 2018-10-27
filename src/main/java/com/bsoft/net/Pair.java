package com.bsoft.net;

/**
 * Created by sky on 2018/10/27.
 */
public class Pair<A, B> {

    private A key;
    private B value;

    public Pair(A a, B b) {
        this.key = a;
        this.value = b;
    }

    public A getKey() {
        return key;
    }

    public void setKey(A key) {
        this.key = key;
    }

    public B getValue() {
        return value;
    }

    public void setValue(B value) {
        this.value = value;
    }
}
