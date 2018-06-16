package org.koliadenko.jero;

import org.zeromq.ZContext;
import org.zeromq.ZMQ;

public class SocketRun implements Runnable {

    @Override
    public void run() {
        try (ZContext context = new ZContext()) {
            ZMQ.Socket socket = context.createSocket(ZMQ.REP);
            socket.bind("tcp://*:5555");
            System.out.println(socket.getLastEndpoint());
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