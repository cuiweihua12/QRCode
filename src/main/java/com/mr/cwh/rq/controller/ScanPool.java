package com.mr.cwh.rq.controller;

/**
 * @program: login
 * @description:
 * @author: cuiweihua
 * @create: 2020-06-23 17:48
 */
public class ScanPool {
    //创建时间
    private Long createTime = System.currentTimeMillis();

    //登录状态
    private boolean scanFlag =false;

    public boolean isScanFlag() {
        return scanFlag;
    }

    public void setScanFlag(boolean scanFlag) {
        this.scanFlag = scanFlag;
    }

    public Boolean isScan(){
        return scanFlag;
    }
    public void setScan(Boolean scanFlag){
        this.scanFlag = scanFlag;
    }
    public Long getCreateTime(){
        return createTime;
    }
    private void setCreateTime(Long createTime){
        this.createTime = createTime;
    }
    /**
     * 获取扫描状态,如果还没扫描.则等待固定描述
     */
    public synchronized boolean getScanStatus()  {
        try {
            if (!isScan()){
                this.wait();
            }
            if (isScan()){
                return true;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }
    /**
     * 扫码之后设置扫码状态
     */
    public synchronized void scanSuccess(){
        try {
            setScan(true);
            this.notifyAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public synchronized void notifyPool(){
        try {
            this.notifyAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
