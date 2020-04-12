 
I.client 需要
II.线程池定时查询。
II.扫描文件夹大小
II.发送信息到服务器
II.注册到新机器
II.controller 接收新增的机器路径
II.服务心跳检测



I.server
II.接收客户端请求
II.查询分组情况
II.按照分组打印信息




I.默认启动步骤

1.启动home项目
java  -jar LogHome-0.0.1-SNAPSHOT.jar

2.启动server项目
java -jar LogServer-0.0.1-SNAPSHOT.jar

3.启动client项目

java  -jar LogClient-0.0.1-SNAPSHOT.jar

默认情况下只会扫描以下三个目录
c:
C:\Windows
C:\Program Files



II.带参数的启动
home项目 所扫描的文件夹参数
temp.test.str
json串有三个字段groupName、directory、systemId
director修改为要扫描的目录即可

java -Dtemp.test.str=[{\"groupName\":\"group1\",\"directory\":\"C:\\\\Work\\\\Project\\\\LogClient\",\"systemId\":\"1\"},{\"groupName\":\"group1\",\"directory\":\"C:\\\\Work\\\\Project\\\\LogServer\",\"systemId\":\"44\"},{\"groupName\":\"group1\",\"directory\":\"C:\\\\Work\\\\Project\\\\LogHome\",\"systemId\":\"3\"}] -jar LogHome-0.0.1-SNAPSHOT.jar






