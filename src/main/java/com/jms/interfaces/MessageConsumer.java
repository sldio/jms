package com.jms.interfaces;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;

public interface MessageConsumer
{
    String getMessageSelector() throws JMSException;
    MessageListener getMessageListener() throws JMSException;
    void setMessageListener(MessageListener listener) throws JMSException;
    Message receive() throws JMSException;
    void close() throws JMSException;

}
