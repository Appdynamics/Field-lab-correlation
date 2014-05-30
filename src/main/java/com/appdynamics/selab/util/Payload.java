package com.appdynamics.selab.util;

import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Payload implements Serializable
{
    private String message;

    private Map<String,Object> innerPayload = new ConcurrentHashMap<String,Object>();


    public Payload(String message)
    {
        this.message = message;
        this.innerPayload.put("Very important message from client:", message);
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    public Map<String, Object> getInnerPayload()
    {
        return innerPayload;
    }

	public void put(String key, String value)
	{ 
		this.innerPayload.put(key, value);
	}
}
