package com.jms;

import org.apache.activemq.broker.BrokerFactory;
import org.apache.activemq.broker.BrokerService;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;

public class Main
{
    public static void main(String[] args)
    {
        Producer producer = null;
        Consumer consumer = null;
        Consumer consumer2 = null;
        Consumer consumer3 = null;
        BrokerService broker = null;

        try
        {
            broker = BrokerFactory.createBroker(new URI("broker:(tcp://localhost:61616)"));
            broker.start();

            Util util = new Util();
            producer = new Producer();
            consumer = new Consumer();
            consumer2 = new Consumer();
            consumer3 = new Consumer();

            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("input type: 'topic' or 'queue'");
            String inputType = reader.readLine();
            TypeEnum type = null;

            if (inputType.equalsIgnoreCase("topic")){
                type = TypeEnum.TOPIC;
            }
            if (inputType.equalsIgnoreCase("queue")){
                type = TypeEnum.QUEUE;
            }

            if (type == null)
            {
                System.out.println("wrong type, exiting");
                throw  new Exception("wrong input data");
            }
            producer.startProducer("test", type);
            consumer.startConsumer("first consumer", "test", type);
            consumer.startConsumer("second consumer", "test", type);
            consumer.startConsumer("third consumer", "test", type);

            while (true){
                System.out.println("input message or 'exit' for exit");
                String inputData = reader.readLine();
                if (inputData.equals("exit"))
                {
                    break;
                }
                producer.sendMessage(inputData);

                try{
                    Thread.sleep(1000);
                }
                catch (InterruptedException e){

                }
            }
        }
        catch (Exception e){
            System.out.println("main error");
        }
        finally
        {
            if (broker != null){
                try
                {
                    broker.stop();
                }
                catch (Exception e){

                }
            }
            producer.stopProducer();
            consumer.stopConsumer();
            consumer2.stopConsumer();
            consumer3.stopConsumer();
        }
    }
}
