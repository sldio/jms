package com.jms;

import javax.jms.*;

public class Consumer
{
    private Connection connection;
    private Session session;
    private Queue queue;
    private Topic topic;

    public void startConsumer(String consumerName, String name, TypeEnum type){
        this.connection = Util.getConnection();
        this.session = Util.getSession();
        MessageConsumer consumer = null;

        try
        {
            if (type.equals(TypeEnum.TOPIC)){
                this.topic = Util.getTopicBySessionAndName(session, name);
                consumer = session.createConsumer(topic);
            }
            if (type.equals(TypeEnum.QUEUE)){
                this.queue = Util.getQueueBySessionAndName(session, name);
                consumer = session.createConsumer(queue);
            }

            consumer.setMessageListener(new ConsumerMessageListener(consumerName));
            connection.start();
        }
        catch (JMSException e){
            System.out.println("Error in consumer");
        }
    }

    public void stopConsumer(){
        try
        {
            if (connection != null)
                this.connection.close();
            if (session != null)
                this.session.close();
        }
        catch (JMSException e){
            System.out.println("error in stop consumer");
        }
    }
}
