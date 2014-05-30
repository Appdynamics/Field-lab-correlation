package com.appdynamics.selab.tcpclient;

import com.appdynamics.selab.util.Payload;

import java.io.*;
import java.net.*;

public class TCPClient {

	//Fetch the appdynamics transaction delegate

	private Socket server;
	private DataOutputStream outToServer;
	private BufferedReader inFromServer;

	public TCPClient(String server, int port) throws Exception {

		this.server = new Socket(server, port);
		this.outToServer = new DataOutputStream(this.server.getOutputStream());
		this.inFromServer = new BufferedReader(new InputStreamReader(this.server.getInputStream()));

	}

    public String sendMessage(String str) throws Exception
    {
        return sendRequest(str);
    }

	public String sendRequest(String request) throws IOException
    {
        try
        {
            Payload payload = new Payload(request);
            this.outToServer.write(objectToBytes(payload));

            return this.inFromServer.readLine();

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public static byte[] objectToBytes(Payload payload) throws IOException
    {
        if(payload == null)
        {
            return null;
        }

        ByteArrayOutputStream baos = new ByteArrayOutputStream(1024);
        ObjectOutputStream oos = null;

        try
        {
            oos = new ObjectOutputStream(baos);
            oos.writeObject(payload);
        }
        finally
        {
            if (oos != null)
            {
                oos.close();
            }
        }
        return baos.toByteArray();
    }

    public void close() {
        try {
            server.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
