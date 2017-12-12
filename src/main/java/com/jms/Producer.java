package com.jms;

import javax.jms.*;

public class Producer
{
    private Connection connection;
    private Session session;
    private MessageProducer producer;

    public Producer()
    {
        this.connection = Util.getConnection();
        this.session = Util.getSession();
    }

    public void startProducer(String name, TypeEnum type)
    {
        try
        {
            if (type.equals(TypeEnum.TOPIC)){
                Topic topic = Util.getTopicBySessionAndName(session, name);
                this.producer = session.createProducer(topic);
                producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
            }
            if (type.equals(TypeEnum.QUEUE)){
                Queue queue = Util.getQueueBySessionAndName(session, name);
                this.producer = session.createProducer(queue);
                producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
            }
        }
        catch (JMSException e){
            System.out.println("Error in consumer");
        }
    }

    public void sendMessage(String message) {

        try
        {
            Message msg = session.createTextMessage(message);
            System.out.println("Sending text '" + message + "'");
            producer.send(msg);
        }
        catch (JMSException e){
            System.out.println("! error in producer");
            e.printStackTrace();
        }


    }
    public void stopProducer()
    {
        try
        {
            if (connection != null)
                connection.close();

            if (session != null)
                session.close();
        } catch (JMSException e){

        }
    }
}
