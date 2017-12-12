package com.jms;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class Util
{
    private static ConnectionFactory connectionFactory;
    private static Connection connection;

    static {
        try
        {
            connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
            connection = connectionFactory.createConnection();
        }
        catch (JMSException e){
            System.out.println("can`t make data");
        }
    }

    public static Connection getConnection()
    {
        return connection;
    }

    public static Session getSession()
    {
        Session session = null;
        try
        {
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        }
        catch (JMSException e){
            System.out.println("can`t make data");
        }
        return session;
    }

    public static Queue getQueueBySessionAndName(Session session, String queueName)
    {
        Queue queue = null;
        try
        {
            queue = session.createQueue(queueName);
        }
        catch (JMSException e){
            System.out.println("can`t make data");
        }
        return queue;
    }

    public static void close(){
        try
        {
            connection.close();
        }
        catch (JMSException e){
            System.out.println("can`t close");
        }
    }

    public static Topic getTopicBySessionAndName(Session session, String topicName){
        Topic topic = null;
        try
        {
            topic = session.createTopic(topicName);
        }
        catch (JMSException e){
            System.out.println("can`t make data");
        }
        return topic;
    }
}
