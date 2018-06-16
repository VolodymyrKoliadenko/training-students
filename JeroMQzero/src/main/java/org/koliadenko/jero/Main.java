package org.koliadenko.jero;

import org.zeromq.ZContext;
import org.zeromq.ZMQ;
import org.zeromq.ZMQ.Socket;

/**
 * Ноябрь 2017
 *
 * @author Администратор
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        try (ZContext context = new ZContext()) {
            Socket socket = context.createSocket(ZMQ.REP);
            socket.bind("tcp://*:5555");
            System.out.println(socket.getLastEndpoint());
            
            (new ClientRun()).run();

            while (!Thread.currentThread().isInterrupted()) {
                System.out.println("__ ruNNNN");

                byte[] reply = socket.recv(0);

                // Print received message
                System.out.println(
                        "MAIN loop=> " + ": " + new String(reply, ZMQ.CHARSET));

                // Send a response
                String response = "Hello, Vova";
                socket.send(response.getBytes(ZMQ.CHARSET), 0);
            }
        }
    }

}
