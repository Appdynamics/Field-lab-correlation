package com.appdynamics.selab.web;

import com.appdynamics.selab.jms.MessageQueue;
import com.appdynamics.selab.jms.SimpleQueueReceiver;
import com.appdynamics.selab.util.Constants;
import com.appdynamics.selab.util.MessageProcessor;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ContextListener implements ServletContextListener {

    public void contextInitialized(ServletContextEvent evt) {
        MessageQueue jmsQueue = new MessageQueue();
        SimpleQueueReceiver jmsReceiver = new SimpleQueueReceiver(jmsQueue);

        MessageProcessor processor = new MessageProcessor(jmsReceiver);
        processor.start();

        evt.getServletContext().setAttribute(Constants.JMS_QUEUE, jmsQueue);
        evt.getServletContext().setAttribute(Constants.JMS_QUEUE_RCVR, jmsReceiver);
        evt.getServletContext().setAttribute(Constants.MSG_PROCESSOR, processor);
    }

    public void contextDestroyed(ServletContextEvent evt) {
        MessageProcessor processor = (MessageProcessor) evt.getServletContext().getAttribute(Constants.MSG_PROCESSOR);
        processor.shutdown();
    }
}
