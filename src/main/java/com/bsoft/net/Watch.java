package com.bsoft.net;

/**
 * Created by sky on 2018/10/26.
 */
public class Watch {

    long start;

    long end;

    public static Watch New(){
        return new Watch();
    }

    public Watch start(){
        start = System.currentTimeMillis();
        return this;
    }

    public Watch end(){
        end = System.currentTimeMillis();
        return this;
    }

    public long timeCost(){
        return end - start;
    }

    @Override
    public String toString() {
        return (end - start)+" ms";
    }
}
