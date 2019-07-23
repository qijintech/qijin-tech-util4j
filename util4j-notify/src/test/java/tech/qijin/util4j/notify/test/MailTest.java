package tech.qijin.util4j.notify.test;

import org.junit.Test;

import jodd.mail.Email;
import jodd.mail.MailServer;
import jodd.mail.SendMailSession;
import jodd.mail.SmtpServer;

/**
 * @author michealyang
 * @date 2019/3/15
 * 开始做眼保健操：←_← ↑_↑ →_→ ↓_↓
 **/
public class MailTest {
    @Test
    public void test() {
        Email email = Email.create()
                .from("john@jodd.org")
                .to("michealyang@aliyun.com")
                .subject("Hello!")
                .textMessage("A plain text message...");
        SmtpServer smtpServer = MailServer.create()
                .host("smtp.sendgrid.net")
                .port(587)
                .auth("mail_test2", "aviagames2019")
                .buildSmtpMailServer();
        SendMailSession session = smtpServer.createSession();
        session.open();
        session.sendMail(email);
        session.close();
    }
}
