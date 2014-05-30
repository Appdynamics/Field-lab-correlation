package com.appdynamics.selab.tcpserver;

import com.appdynamics.selab.util.Payload;
import com.appdynamics.selab.util.CustomQueue;
import com.appdynamics.selab.util.Pipeline;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;

public class TCPServer {

    private CustomQueue queue;
    private Pipeline pipeline;

	public TCPServer() {
        pipeline = new Pipeline();
        queue = new CustomQueue();
        (new QueueProcessor(queue)).start();
        (new QueueProcessor(queue)).start();
        (new Intermediary(pipeline, queue)).start();
        (new Intermediary(pipeline, queue)).start();
	}

	public void startServer(int port) throws Exception {

        Socket connectionSocket;
		System.out.println("Starting TCPServer...");
        ServerSocket server = new ServerSocket(port);
		System.out.println("TCPServer Started");

		while (true) {
            connectionSocket = server.accept();
			DataOutputStream toClient = new DataOutputStream(connectionSocket.getOutputStream());
			Payload clientReq = receiveData(connectionSocket);
			System.out.println("FROM CLIENT: " + clientReq.getInnerPayload());
			toClient.writeBytes("Client Request Received\n");

            try {
                connectionSocket.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
		}

	}

	private Payload receiveData(Socket connectionSocket) throws Exception {

		ObjectInputStream ois = new ObjectInputStream(connectionSocket.getInputStream());
        Payload payload =  (Payload) ois.readObject();
        processPayload(payload);
        return payload;
	}

	private void queryAD() throws Exception {
		
		URL url = new URL("http://www.appdynamics.com");

		BufferedReader bin = new BufferedReader (new InputStreamReader( url.openStream() ));
		
		String line;
		
		while ((line = bin.readLine()) != null) {
			System.out.println(line);
        }
	}

    private void multiQuery(String[] toQuery) {
        for (String s : toQuery) {
            pipeline.put(s);
        }
    }

    public void processPayload(Payload payload )
    {
        System.out.println("Processing payload >>>" + payload.getInnerPayload());
        try
        {
            Thread.sleep(1000);	
			queryAD();
            multiQuery(new String[] { "http://www.yahoo.com", "http://www.amazon.com", "http://slashdot.org"});
        } 
		catch (InterruptedException ie)
        {
            ie.printStackTrace();
        }
		catch (Exception e)
		{ 
			e.printStackTrace(); 
		}
    }

	public static void main(String argv[]) throws Exception {

		TCPServer server = new TCPServer();
		server.startServer(Integer.parseInt(argv[0]));
	}

    /**
     * Sneaky intermediate queue.  Having a double queue handoff makes just using find-entry-points insufficient for
     * figuring out how to do thread correlation.  With the double handoff thread profiling will be necessary.
     *
     */
    private static class Intermediary extends Thread {

        private Pipeline pipeline;
        private CustomQueue customQueue;

        Intermediary(Pipeline pipeline, CustomQueue queue) {
            this.pipeline = pipeline;
            this.customQueue = queue;
        }

        public void run() {

            while (true) {
                Object o = pipeline.take();
                if (o != null) {
                    processQueue(o);
                }
            }
        }

        private void processQueue(Object o) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException ie) {
                // won't happen
            }

            try {
                URL url = new URL((String) o);
                putInQueue(url);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        private void putInQueue(Object o) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException ie) {
                // won't happen
            }

            customQueue.enqueue(o);
        }
    }

    /**
     * Terminal queue.  Calls backends.
     *
     */
    private static class QueueProcessor extends Thread {

        private CustomQueue queueToProcess;

        QueueProcessor(CustomQueue queueToProcess) {
            this.queueToProcess = queueToProcess;
        }

        public void run() {

            while (true) {
                Object o = queueToProcess.dequeue();
                if (o != null) {
                    callURL(o);
                }
            }
        }

        private void callURL(Object o) {
            try {

                URL url = (URL) o;
                BufferedReader bin = new BufferedReader (new InputStreamReader( url.openStream() ));

                String line;

                while ((line = bin.readLine()) != null) {
                    System.out.println(line);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
