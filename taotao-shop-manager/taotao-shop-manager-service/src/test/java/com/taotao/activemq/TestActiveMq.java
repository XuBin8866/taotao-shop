package com.taotao.activemq;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import javax.jms.*;
import java.io.IOException;

/**
 * @author xxbb
 * ActiveMQ遵循JMS规范
 */
public class TestActiveMq {
    @Test
    public void testQueueProducer() throws JMSException {
        //1.创建连接工厂对象，指定ip和端口号
        ConnectionFactory connectionFactory=new ActiveMQConnectionFactory("tcp://192.168.17.2:61616");
        //2.创建一个连接对象
        Connection connection=connectionFactory.createConnection();
        //3.开启连接
        connection.start();
        //4.创建一个对话
        //第一个参数：是否开始事务，为true时第二个参数忽略
        //第二个参数：消息的应答形式 1.手动应答，2。自动应答
        Session session=connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
        //5.创建一个Destination对象（topic，queue）,此处创建queue对象
        Queue queue=session.createQueue("test-queue");
        //6.创建一个Producer对象
        MessageProducer producer=session.createProducer(queue);
        //7.创建一个Message对象
        TextMessage textMessage=session.createTextMessage("Hello activeMQ,this is the first test");
        //8.生产者发送消息
        producer.send(textMessage);
        //9.释放资源
        producer.close();
        session.close();
        connection.close();
    }
    @Test
    public void testQueueConsumer() throws JMSException, IOException {
        //创建连接到创建队列
        ConnectionFactory connectionFactory=new ActiveMQConnectionFactory("tcp://192.168.17.2:61616");
        Connection connection=connectionFactory.createConnection();
        Session session=connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
        //消息队列名保持一致
        Queue queue=session.createQueue("test-queue");
        MessageConsumer messageConsumer = session.createConsumer(queue);
        messageConsumer.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                try {
                    TextMessage textMessage= (TextMessage) message;
                    String text=null;
                    text=textMessage.getText();
                    System.out.println(text);
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        });
        //等待键盘输入
        //System.in.read();
        //关闭资源
        messageConsumer.close();
        session.close();
        connection.close();
    }
    @Test
    public void testSpringQueueConsumer() throws JMSException, IOException {
        ApplicationContext applicationContext=new ClassPathXmlApplicationContext("classpath:spring/applicationContext-activemq.xml");
        JmsTemplate template=applicationContext.getBean(JmsTemplate.class);
        Queue queue= (Queue) applicationContext.getBean("test-queue");
        template.send(queue, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                return session.createTextMessage("Spring activemq test");
            }
        });
    }
}
