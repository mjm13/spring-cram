

# Nacos注册中心分组及命名空间
Nacos注册中心比eureka多了分组，命名空间的概念，
* 分组：对应服务/配置，默认为DEFAULT_GROUP。
* 命名空间：更高维度的隔离，先通过命名空间隔离，再使用分组隔离，默认为public，先在nacos管理页“命名空间”中新增命名空间，“命名空间ID”写入配置文件中。
> 添加命名空间后会在服务列表/配置列表左上方增加一个导航
![image.png](https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/ce394a2ef9ad429dabff42ad809ffefe~tplv-k3u1fbpfcp-watermark.image?)

# 使用
配置文件修改
```
spring.cloud.nacos.discovery.group=分组名称
spring.cloud.nacos.discovery.namespace=命名空间ID
```
> 增加配置后只能访问命名空间+分组名称相同的服务

# Feign过滤服务源码调试
通过调试发现服务列表来源是BaseLoadBalancer中缓存的allServerList，
```java
    @Monitor(name = PREFIX + "AllServerList", type = DataSourceType.INFORMATIONAL)
    protected volatile List<Server> allServerList = Collections
            .synchronizedList(new ArrayList<Server>());
```
> 这里使用了volatile，是因为会被其它线程操作使我们使用时更加无感

而这里的数据又是从NacosServerList这里获取的
```
//com.alibaba.cloud.nacos.ribbon.NacosServerList#getServers
private List<NacosServer> getServers() {
   try {
      String group = discoveryProperties.getGroup();
      List<Instance> instances = nacosServiceManager
            .getNamingService(discoveryProperties.getNacosProperties())
            .selectInstances(serviceId, group, true);
      return instancesToServerList(instances);
   }
   catch (Exception e) {
      throw new IllegalStateException(
            "Can not get service instances from nacos, serviceId=" + serviceId,
            e);
   }
}
```

原本以为到这里就源头了，但是调试的时候发现serviceInfo是有同名服务的但是getHosts()会是空，导致instancesToServerList返回的服务列表还是空。
```
//com.alibaba.nacos.client.naming.NacosNamingService#selectInstances
@Override
public List<Instance> selectInstances(String serviceName, String groupName, List<String> clusters, boolean healthy,
        boolean subscribe) throws NacosException {

    ServiceInfo serviceInfo;
    if (subscribe) {
        serviceInfo = hostReactor.getServiceInfo(NamingUtils.getGroupedName(serviceName, groupName),
                StringUtils.join(clusters, ","));
    } else {
        serviceInfo = hostReactor
                .getServiceInfoDirectlyFromServer(NamingUtils.getGroupedName(serviceName, groupName),
                        StringUtils.join(clusters, ","));
    }
    return selectInstances(serviceInfo, healthy);
}
private List<Instance> selectInstances(ServiceInfo serviceInfo, boolean healthy) {
    List<Instance> list;
    if (serviceInfo == null || CollectionUtils.isEmpty(list = serviceInfo.getHosts())) {
        return new ArrayList<Instance>();
    }
    
    Iterator<Instance> iterator = list.iterator();
    while (iterator.hasNext()) {
        Instance instance = iterator.next();
        if (healthy != instance.isHealthy() || !instance.isEnabled() || instance.getWeight() <= 0) {
            iterator.remove();
        }
    }
    
    return list;
}
```
原来获取的实例是从hostReactor中serviceInfoMap获取的，但是新的疑问又来了，这个又是从哪初始化的。。

再退回去发现原来在创建NacosNamingService时同时创建了NamingProxy，NamingProxy负责去nacos中查询最新的服务列表，NacosWatch是配置的监听用于异步触发queryList

``` java
//com.alibaba.nacos.client.naming.net.NamingProxy#queryList
    public String queryList(String serviceName, String clusters, int udpPort, boolean healthyOnly)
            throws NacosException {
        
        final Map<String, String> params = new HashMap<String, String>(8);
        params.put(CommonParams.NAMESPACE_ID, namespaceId);
        params.put(CommonParams.SERVICE_NAME, serviceName);
        params.put("clusters", clusters);
        params.put("udpPort", String.valueOf(udpPort));
        params.put("clientIP", NetUtils.localIP());
        params.put("healthyOnly", String.valueOf(healthyOnly));
        
        return reqApi(UtilAndComs.nacosUrlBase + "/instance/list", params, HttpMethod.GET);
```

看到这里大概知道为什么很多地方没有使用到命名空间ID但是服务又被过滤掉了，因为nacos服务返回的服务信息是依据查询返回的。

> 源码还是要调试起来看。


