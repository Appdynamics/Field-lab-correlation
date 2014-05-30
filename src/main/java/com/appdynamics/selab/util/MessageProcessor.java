package com.appdynamics.selab.util;

import javax.jms.QueueReceiver;
import javax.jms.Message;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

public class MessageProcessor extends Thread {

    private QueueReceiver receiver;
    private NotAThread runner;

    public MessageProcessor(QueueReceiver qr) {
        this.receiver = qr;
        runner = new NotAThread();
    }

    public void shutdown() {
        runner.shutdown();
    }

    public void run() {
        runner.doRun();
    }

    private class NotAThread {

        private volatile boolean running;

        NotAThread() {
            running = true;
        }

        public void shutdown() {
            synchronized (this) {
                running = false;
                interrupt();
            }
        }

        private void doRun() {

            boolean isRunning = running;

            while (isRunning) {

                try {
                    Message rcvdMsg = receiver.receive();
                    if (rcvdMsg != null) {
                        doSomethingSpecial(rcvdMsg);
                    } else {
                        isRunning = false;
                    }
                } catch (InterruptedException ie) {
                    // fall-through, if we were interrupted we'll figure it out
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (isRunning) {
                    isRunning = running;
                }
            }
        }

        private void doSomethingSpecial(Message m) throws Exception {

            URL url = new URL("http://www.cnn.com");

            BufferedReader reader = new BufferedReader (new InputStreamReader( url.openStream() ));

            String line;

            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            try {
                reader.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
