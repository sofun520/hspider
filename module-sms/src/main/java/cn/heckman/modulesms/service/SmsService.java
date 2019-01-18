package cn.heckman.modulesms.service;

public interface SmsService {

    /**
     * 通知类短信
     *
     * @param tempId    模板id
     * @param values    变量替换值数组
     * @param phone     手机号码
     * @throws Exception
     */
    void sendTempSms(String tempId, String[] values, String phone) throws Exception;

}
