package cn.heckman.modulemail.service;

public interface Mailservice {

    boolean sendMail(String title, String content, String receiver, String cc, String from);

    boolean sendHtmlMail(String title, String content, String receiver, String cc, String from);

}
