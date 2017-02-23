package com.solverpeng.common.entity;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

/**
 * <pre>
 *      author: solverpeng
 *      blog  : http://solverpeng.com
 *      time  : 2017/2/23
 *      desc  : 邮件认证实体
 * </pre>
 */
public class MailAuthenticator extends Authenticator {
    private String userName = null;
    private String password = null;

    public MailAuthenticator() {
    }

    public MailAuthenticator(String username, String password) {
        this.userName = username;
        this.password = password;
    }

    protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(userName, password);
    }
}  