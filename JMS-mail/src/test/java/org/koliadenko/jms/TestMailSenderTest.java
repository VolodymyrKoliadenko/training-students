/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.koliadenko.jms;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailAuthenticationException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = AppConfig.class)
public class TestMailSenderTest {

    @Autowired
    private TestMailSender sender;

    /**
     * Test of send method, of class TestMailSender.
     */
    @Test(expected = MailAuthenticationException.class) //incorrect password
    public void testSend() {
        System.out.println("send");
        String to = "vovakk@mail.ru";
        String subj = "";
        String text = "111";
        sender.send(to, subj, text);

        System.out.println("Test mail work");
    }

}
