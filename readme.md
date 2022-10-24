# 简介
spring学习记录





第一条：这条会报错，继续下一条命令

```shell
git config http.sslVerify "false"
```

第二条：

```shell
git config --global http.sslVerify "false"
```


设置代理：
```
git config --global http.proxy 'socks5://127.0.0.1:1080' 
git config --global https.proxy 'socks5://127.0.0.1:1080'
``` 

查看代理：
```
git config --global --get http.proxy
git config --global --get https.proxy
```

取消代理：
```
git config --global --unset http.proxy
git config --global --unset https.proxy
```