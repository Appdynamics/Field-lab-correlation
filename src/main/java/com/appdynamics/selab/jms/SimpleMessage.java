package com.appdynamics.selab.jms;

import javax.jms.Destination;
import javax.jms.Message;

import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;
import java.util.HashMap;

public class SimpleMessage implements Message {

    private Map<String, Object> props;
    private Destination dest;

    public SimpleMessage() {
        props = new HashMap<String, Object>();
    }

    public void	acknowledge() {}

    public void	clearBody() {}

    public void	clearProperties() {
        props.clear();
    }

    public boolean getBooleanProperty(String name) {
        return ((Boolean) props.get(name)).booleanValue();
    }

    public byte	getByteProperty(String name) {
        return ((Byte) props.get(name)).byteValue();
    }

    public double getDoubleProperty(String name){
        return ((Double) props.get(name)).doubleValue();
    }

    public float getFloatProperty(String name) {
        return ((Float) props.get(name)).floatValue();
    }

    public int getIntProperty(String name) {
        return ((Integer) props.get(name)).intValue();
    }

    public String getJMSCorrelationID() { return null; }

    public byte[] getJMSCorrelationIDAsBytes() { return null; }

    public int getJMSDeliveryMode() { return 0; }

    public Destination getJMSDestination() { return null; }

    public long	getJMSExpiration() { return 0L; }

    public String getJMSMessageID() { return null; }

    public int	 getJMSPriority() { return 1; }

    public boolean getJMSRedelivered() { return false; }

    public Destination getJMSReplyTo() { return null; }

    public long	getJMSTimestamp() { return 0L; }

    public String getJMSType() { return null; }

    public long	getLongProperty(String name) {
        return ((Long) props.get(name)).longValue();
    }

    public Object getObjectProperty(String name) {
        return props.get(name);
    }

    public Enumeration getPropertyNames() {
        return new StupidEnumeration(props.keySet().iterator());
    }

    public short getShortProperty(String name) {
        return ((Short) props.get(name)).shortValue();
    }

    public String getStringProperty(String name) {
        return (String) props.get(name);
    }

    public boolean propertyExists(String name) {
        return props.containsKey(name);
    }

    public void	setBooleanProperty(String name, boolean value) {
        props.put(name, value);
    }

    public void	setByteProperty(String name, byte value) {
        props.put(name, value);
    }

    public void	setDoubleProperty(String name, double value) {
        props.put(name, value);
    }

    public void	setFloatProperty(String name, float value) {
        props.put(name, value);
    }

    public void	setIntProperty(String name, int value) {
        props.put(name, value);
    }

    public void	setJMSCorrelationID(String correlationID) {}

    public void	setJMSCorrelationIDAsBytes(byte[] correlationID) {}

    public void	setJMSDeliveryMode(int deliveryMode) {}

    public void	setJMSDestination(Destination destination) {
        this.dest = destination;
    }

    public void	setJMSExpiration(long expiration) {}

    public void	setJMSMessageID(String id) {}

    public void	setJMSPriority(int priority) {}

    public void	setJMSRedelivered(boolean redelivered) {}

    public void	setJMSReplyTo(Destination replyTo) {}

    public void	setJMSTimestamp(long timestamp) {}

    public void	setJMSType(String type) {}

    public void	setLongProperty(String name, long value) {
        props.put(name, value);
    }

    public void	setObjectProperty(String name, java.lang.Object value) {
        props.put(name, value);
    }

    public void	setShortProperty(String name, short value) {
        props.put(name, value);
    }

    public void	setStringProperty(String name, String value) {
        props.put(name, value);
    }

    private static class StupidEnumeration implements Enumeration {

        private Iterator iter;

        StupidEnumeration(Iterator i) {
            this.iter = i;
        }

        public boolean hasMoreElements() {
            return iter.hasNext();
        }

        public Object nextElement() {
            return iter.next();
        }
    }
}
