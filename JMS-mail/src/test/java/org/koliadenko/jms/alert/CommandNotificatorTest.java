/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.koliadenko.jms.alert;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.koliadenko.jms.AppConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = AppConfig.class)
public class CommandNotificatorTest {

    @Autowired
    private CommandNotificator cn;

    /**
     * Test of sendCommand method, of class CommandNotificator.
     */
    @Test
    public void testSendCommand() {
        System.out.println("sendCommand");
        String command = "42";

        String expResult = "OK " + command;
        String result = cn.sendCommand(command);
        assertEquals(expResult, result);

    }

}
