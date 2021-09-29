# TODO 
```shell
# 启动Namesrv 服务
nohup sh bin/mqnamesrv &
# 启动 Broker 服务
nohup sh bin/mqbroker -c conf/broker.conf  -n 127.0.0.1:9876 &
```

 -[x] 回溯消费
 通过mqadmin中resetOffsetByxxxx
 
 -[ ] 流量控制 如何使用
消费速度不够增加消费者,消费过快实现RocketMQPushConsumerLifecycleListener接口设置频率
  
延时消费流控：就是在消费端延时消费消息(sleep),具体延时多少要根据业务要求速率，和消费端线程数量，
和节点部署数量来控制

 -[ ] 如何处理重复消费 
 通过业务id来区分,