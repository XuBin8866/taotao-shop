package com.taotao.content.test;

import com.taotao.content.service.jedis.JedisClientCluster;
import javafx.application.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;

import java.util.HashSet;
import java.util.Set;

/**
 * @author xxbb
 */
public class TestMain {
    /**
     * 连接单机版redis
     */
    @Test
    public void testJedis(){
        Jedis jedis=new Jedis("192.168.17.2",6379);
        String result=jedis.get("key1");
        System.out.println(result);
        jedis.close();
    }

    @Test
    public void testJedisCluster(){
        //创建一个JedisCluster对象，构造参数是set类型，集合中每一个元素是HostAndPort类型，即添加节点
        Set<HostAndPort> nodes=new HashSet<>();
        nodes.add(new HostAndPort("192.168.17.2",7001));
        nodes.add(new HostAndPort("192.168.17.2",7002));
        nodes.add(new HostAndPort("192.168.17.2",7003));
        nodes.add(new HostAndPort("192.168.17.2",7004));
        nodes.add(new HostAndPort("192.168.17.2",7005));
        nodes.add(new HostAndPort("192.168.17.2",7006));
        JedisCluster jedisCluster=new JedisCluster(nodes);
        jedisCluster.set("Hello","你好redis");
        System.out.println(jedisCluster.get("Hello"));
    }

    public void testJedisClient(){
        ApplicationContext applicationContext=new ClassPathXmlApplicationContext("classpath:spring/applicationContext-*.xml");
        JedisClientCluster jedisClientCluster=applicationContext.getBean(JedisClientCluster.class);
        System.out.println(jedisClientCluster.get("Hello"));
    }
}
