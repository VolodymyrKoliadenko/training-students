package org.koliadenko.jero;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.zeromq.ZMsg;

public class ClientRun implements Runnable {

    @Override
    public void run() {
       
        MDClientapi clientSession = new MDClientapi("tcp://localhost:5555", true);

        int count;
        for (count = 0; count < 4; count++) {
            
            try {
                Thread.sleep(90);
            } catch (InterruptedException ex) {
                Logger.getLogger(ClientRun.class.getName()).log(Level.SEVERE, null, ex);
            }
                ZMsg request = new ZMsg();
                request.addString("Hello world in runnable");
                
                ZMsg reply = clientSession.send("echo", request);
                if (reply != null) {
                    reply.destroy();
                } else {
                    break; // Interrupt or failure
                }
            
        }

        System.out.printf("%d requests/replies processed\n", count);
        clientSession.destroy();
    }

}
