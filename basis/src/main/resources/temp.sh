#!/bin/bash
deployDir="/home/workspace/wisdom-reform/lib/"
historyDir="/home/workspace/wisdom-reform/history/"
jarName="wisdom-reform-1.0-SNAPSHOT.jar"
jarName_prefix="wisdom-reform"
jarName_suffix=".jar"

dateStr = `date %s`
echo dateStr
copyFileName = $jarName_prefix$dateStr$jarName_suffix
echo copyFileName
cp -i "$deployDir$jarName" "$historyDir$copyFileName"



#!/bin/bash
#发布目录（修改为对应的发布目录）
deployDir="/home/project/M-netflix-service1"
#工程名称（修改为对应的服务名称）
jarName="m-service1-0.0.1-SNAPSHOT.jar"
#全路径
jarPath=${deployDir}/${jarName}

echo "开始执行命令"
if [ "$(ls ${jarPath} 2> /dev/null | wc -l)" != "0" ]; then
        echo "关闭进程开始"
        PIDS=`ps -ef | grep java | grep ${jarName} |awk '{print $2}'`
        if [ -n "$PIDS" ]; then
         kill -9 "$PIDS"
         echo "关闭进程结束"
        fi
        echo "启动server1"
    nohup java -jar ${jarPath} --spring.profiles.active=server1  >/dev/null 2>&1 &
        echo "启动server2"
    nohup java -jar ${jarPath} --spring.profiles.active=server2  >/dev/null 2>&1 &
else
    echo "找不到发布包"
    exit 1
fi
```
