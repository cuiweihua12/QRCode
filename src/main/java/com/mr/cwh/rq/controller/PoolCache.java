package com.mr.cwh.rq.controller;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @program: login
 * @description:
 * @author: cuiweihua
 * @create: 2020-06-23 17:46
 */
public class PoolCache {
    //缓存超时时间10分
    private static Long timeOutSecond = 600L;
    //半小时倾一次缓存
    private static Long cleanIntervalSecond = 1800L;
    public static Map<String,ScanPool> cacheMao  = new HashMap<String,ScanPool>();
    static {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    try {
                        Thread.sleep(cleanIntervalSecond*1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    clean();
                }
            }
            public void clean(){
                if (cacheMao.keySet().size()>0){
                    Iterator<String> iterator = cacheMao.keySet().iterator();
                    while(iterator.hasNext()){
                        String next = iterator.next();
                        ScanPool pool = cacheMao.get(next);
                        if (System.currentTimeMillis() - pool.getCreateTime() > timeOutSecond*1000){
                            cacheMao.remove(next);
                        }
                    }
                }
            }
        });
    }
}
