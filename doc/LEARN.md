# idea 操作
- 按住Alt往下拉鼠标选中多行，输入即可同时写多行代码，方便写一些重复的控件声明。
# springboot启动时执行任务CommandLineRunner
平常开发中有可能需要实现在项目启动后执行的功能，SpringBoot提供的一种简单的实现方案就是添加一个model并实现CommandLineRunner接口，实现功能的代码放在实现的run方法中

 SpringBoot在项目启动后会遍历所有实现CommandLineRunner的实体类并执行run方法，如果需要按照一定的顺序去执行，那么就需要在实体类上使用一个@Order注解（或者实现Order接口）来表明顺序

# Twitter的分布式自增ID算法snowflake (Java版)
分布式系统中，有一些需要使用全局唯一ID的场景，这种时候为了防止ID冲突可以使用36位的UUID，但是UUID有一些缺点，首先他相对比较长，另外UUID一般是无序的。

有些时候我们希望能使用一种简单一些的ID，并且希望ID能够按照时间有序生成。

而twitter的snowflake解决了这种需求，最初Twitter把存储系统从MySQL迁移到Cassandra，因为Cassandra没有顺序ID生成机制，所以开发了这样一套全局唯一ID生成服务。

1. snowflake的结构如下(每部分用-分开):

0 - 0000000000 0000000000 0000000000 0000000000 0 - 00000 - 00000 - 000000000000

第一位为未使用，接下来的41位为毫秒级时间(41位的长度可以使用69年)，然后是5位datacenterId和5位workerId(10位的长度最多支持部署1024个节点） ，最后12位是毫秒内的计数（12位的计数顺序号支持每个节点每毫秒产生4096个ID序号）

一共加起来刚好64位，为一个Long型。(转换成字符串后长度最多19)

snowflake生成的ID整体上按照时间自增排序，并且整个分布式系统内不会产生ID碰撞（由datacenter和workerId作区分），并且效率较高。经测试snowflake每秒能够产生26万个ID。

# Lombok有哪些注解
- @Setter
- @Getter
- @Data
- @Log(这是一个泛型注解，具体有很多种形式)
- @AllArgsConstructor
- @NoArgsConstructor
- @EqualsAndHashCode
- @NonNull
- @Cleanup
- @ToString
- @RequiredArgsConstructor
- @Value
- @SneakyThrows
- @Synchronized

# OkHttp日志拦截器LoggingInterceptor
    public class LoggingInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            //Chain 里包含了request和response
            Request request = chain.request();
            long t1 = System.nanoTime();//请求发起的时间
            Logger.info(String.format("发送请求 %s on %s%n%s",request.url(),chain.connection(),request.headers()));
            Response response = chain.proceed(request);
            long t2 = System.nanoTime();//收到响应的时间
            //不能直接使用response.body（）.string()的方式输出日志
            //因为response.body().string()之后，response中的流会被关闭，程序会报错，
            // 我们需要创建出一个新的response给应用层处理
            ResponseBody responseBody = response.peekBody(1024 * 1024);
            Logger.info(String.format("接收响应：[%s] %n返回json:%s  %.1fms%n%s",
                    response.request().url(),
                    responseBody.string(),
                    (t2-t1) /1e6d,
                    response.headers()
                    ));
            return response;
        }
    }
    
# Okhttp3基本使用
    
# FasterXml jackson   
spring boot web 组件默认依赖的就是 FasterXml jackson  
jackson 概述
1. 市面上用于在 Java 中解析 Json 的第三方库，随便一搜不下几十种，其中的佼佼者有 Google 的 Gson， Alibaba 的 Fastjson 以及本文的 jackson。

2. 三者不相伯仲，随着掌握一个都能满足项目中的 json 解析操作。因为发现 spring boot web 组件默认依赖的就是 FasterXml jackson，所以还是花点时间稍微研究一下还是很有必要的。

3. gson 和 fastjson 使用时只需要导入一个 jar 包(或者一个依赖)即可，而 jackson 却不是全部集成在一个 jar (一个应用)内，而是分为不同的功能模块，需要使用哪些功能，则导入对应的 jar 包(或依赖)。

# Spring Cloud 使用Feign调用服务传递Header中的参数
1.使用Feign 调用其他微服务，尤其是在多级调用的同时，需要将一些共同的参数传递至下一个服务，如：token。比较方便的做法是放在请求头中，在Feign调用的同时自动将参数放到restTemplate中。

2.具体做法是首先实现 RequestInterceptor

3.RequestContextHolder.getRequestAttributes()该方法是从ThreadLocal变量里面取得相应信息的，当hystrix断路器的隔离策略为THREAD时，是无法取得ThreadLocal中的值。

解决方案：

（1）. hystrix隔离策略换为SEMAPHORE。

（2）自定义策略，模仿Sleuth的trace传递。

具体可参考：http://www.itmuch.com/spring-cloud-sum/hystrix-threadlocal/

4.最后需要注意的是，如果是使用多线程的情况下，则需要在主线程调用其他线程前将RequestAttributes对象设置为子线程共享
    
    ServletRequestAttributes attribute = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
    RequestContextHolder.setRequestAttributes(attribute, true);
    
#  ResourceID在哪验证
@EnableResourceServer会给Spring Security的FilterChan添加一个OAuth2AuthenticationProcessingFilter，OAuth2AuthenticationProcessingFilter会使用OAuth2AuthenticationManager来验证token。
OAuth2AuthenticationManager#authenticate(Authentication authentication)

# 粗细粒度的访问控制
粗粒度访问控制，所有URL以"/admin"开头的用户必须拥有角色"ADMIN"才能访问。实际上操作的时候hasRole表达式，会判断参数是否包含"ROLE_"前缀，如果没有则加上去，然后再去校验。有这个前缀则直接校验。

细粒度的访问控制
注：需要使用注解@EnableGlobalMethodSecurity(prePostEnabled=true) 开启

    @PreAuthoritze("hasAuthority('readArtical')")
    public List<Artical> getAll() {
        //...
    }
    这个注解，会从SecurityContext中取出Authencation对象，然后再取出Collection<GrantedAuthority> authorites集合。
    然后比对当前用户是否有权限"readArtical"。实际上就是比对集合中是否有那个GrantedAuthority的getAuthority()方法返回的字符串与"radArtical"匹配。
    
# Maven中profile和filtering实现多个环境下的属性过滤
1. filtering功能
     主要用来替换项目中的资源文件（*.xml、*.properties）当中的${...}，比如${db.url}，那么如果配置了db.url=aaa的话，在项目编译的时候，就会自动的把${db.url}替换为aaa，下面以实例来讲解一下
     采取参照博客中创建完maven的web项目后，会看到src/main/sources的目录，在此目录下面创建个“test.properties”，里面随便来上一行，例如Hello ${user.name}，好了，接下来修改我们的pom文件，来启动filtering功能
    
        <build>
                <!--第一种方式，两种方式都需要指定需要编译的目录 -->
                <resources>
                    <resource>
                        <directory>src/main/resources</directory>
                        <!--可以在此配置过滤文件 -->
                        <includes>
                            <include>**/*.xsd</include>
                            <include>**/*.properties</include>
                        </includes>
                        <!--开启filtering功能 -->
                        <filtering>true</filtering>
                    </resource>
                </resources>
         
                <filters>
                    <!-- 是以该pom文件路径作为参考 -->
                    <filter>project.properties</filter>
                </filters>
         
         
                <plugins>
                    <plugin>
                        <artifactId>maven-war-plugin</artifactId>
                        <configuration>
                            <version>2.5</version>
                        </configuration>
                    </plugin>
                </plugins>
        </build>
            
            
# jks
1. 正确姿势
Spring Cloud Config的非对称加密可以使用RSA加密方式，通过JDK自带的keytool生产秘匙对，对敏感信息进行加密解密。

生产秘匙对：

`keytool -genkeypair -alias mytestkey -keyalg RSA -dname "CN=Web Server,OU=China,O=www.howardliu.cn,L=Beijing,S=Beijing,C=China" -keypass changeme -keystore config-service.jks -storepass letmein`

将生成的keystore文件拷贝到src/main/resources目录中，然后在配置文件bootstrap.yml中配置信息秘匙对信息：

    encrypt:
      key-store:
        location: classpath:/config-service.jks
        alias: mytestkey
        password: letmein
        secret: changeme
在pom.xml中加入了如下配置：

    <build>
        <resources>
            <resource>
                <directory>src/main/resources</directory>
                <filtering>true</filtering>
                <excludes>
                    <exclude>**/*.jks</exclude>
                </excludes>
            </resource>
            <resource>
                <directory>src/main/resources</directory>
                <filtering>false</filtering>
                <includes>
                    <include>**/*.jks</include>
                </includes>
            </resource>
        </resources>
    </build>
然后启动应用，通过访问http://localhost:8888/encrypt/status ，返回{"status":"OK"}，说明配置正确。这个使用就可以通过访问/encrypt和decrypt两个endpoints对指定信息进行加密解密了。
2. 错误姿势
刚开始学习的时候并不是如上面那样顺利，碰到了一些问题。

2.1 encrypt.key-store.*配置位置引起的加密信息未解密
最开始是将encrypt.key-store.*配置在application.yml中，这个时候通过访问http://localhost:8888/encrypt/status ，返回{"status":"OK"}。原本以为配置成功，结果在客户端获取加密信息的时候，返回的结果并没有解密，只是将{cipher}去掉了。

这个错误算是比较容易排查，Spring Cloud的主要配置文件就application.yml和bootstrap.yml两个，因为客户端中需要把Spring Cloud Config的配置信息放在bootstrap.yml中，就把encrypt.key-store.*配置bootstrap.yml试试，结果果然成功。

这个错误出现的时候，encrypt.key-store.location使用的是绝对路径file://${user.home}/work/projects/spring-learning/micro-service/config-service/src/main/resources/config-service.jks。
2.2 maven filter对keystore文件的污染
因为对Spring Cloud Config服务端定义了/info这个endpoints这个信息，需要用到maven的构建内容，所以在pom.xml中加入了filter信息：

    <build>
        <resources>
            <resource>
                <directory>src/main/resources</directory>
                <filtering>true</filtering>
            </resource>
        </resources>
    </build>
结果在访问 http://localhost:8888/encrypt/status 的时候，返回错误信息：

Thu Oct 12 13:55:51 CST 2017
There was an unexpected error (type=Internal Server Error, status=500).
Cannot load keys from store: class path resource [config-service.jks]
就在stackoverflow上找到解决方式，是maven的filter污染了文件。就把pom.xml中的配置改为这样：

    <build>
        <resources>
            <resource>
                <directory>src/main/resources</directory>
                <filtering>true</filtering>
                <excludes>
                    <exclude>**/*.jks</exclude>
                </excludes>
            </resource>
            <resource>
                <directory>src/main/resources</directory>
                <filtering>false</filtering>
                <includes>
                    <include>**/*.jks</include>
                </includes>
            </resource>
        </resources>
    </build>
再次测试，果然成功。不过遗憾的是，还不确定filter为什么会污染文件，后面慢慢研究，等找到原因再把结果放在下面。

# 公钥 私钥 数字证书
根据非对称密码学的原理，每个证书持有人都有一对公钥和私钥，这两把密钥可以互为加解密。公钥是公开的，不需要保密，而私钥是由证书持人自己持有，并且必须妥善保管和注意保密。数字证书则是由证书认证机构（CA）对证书申请者真实身份验证之后，用CA的根证书对申请人的一些基本信息以及申请人的公钥进行签名（相当于加盖发证书机构的公章）后形成的一个数字文件。

CA完成签发证书后，会将证书发布在CA的证书库（目录服务器）中，任何人都可以查询和下载，因此数字证书和公钥一样是公开的。    

可以这样说，数字证书就是经过CA认证过的公钥，而私钥一般情况都是由证书持有者在自己本地生成的，由证书持有者自己负责保管。

具体使用时，签名操作是发送方用私钥进行签名，接受方用发送方证书来验证签名；加密操作则是用接受方的证书进行加密，接受方用自己的私钥进行解密。 因此，如果说数字证书是电子商务应用者的网上数字身份证话，那么证书相应的私钥则可以说是用户的私章或公章
1. 数字证书、公钥和私钥这三者之间的关系是什么
根据非对称密码学的原理，每个证书持有人都有一对公钥和私钥，这两把密钥可以互为加解密。公钥是公开的，不需要保密，而私钥是由证书持人自己持有，并且必须妥善保管和注意保密。数字证书则是由证书认证机构（CA）对证书申请者真实身份验证之后，用CA的根证书对申请人的一些基本信息以及申请人的公钥进行签名（相当于加盖发证书机构的公章）后形成的一个数字文件。
   
CA完成签发证书后，会将证书发布在CA的证书库（目录服务器）中，任何人都可以查询和下载，因此数字证书和公钥一样是公开的。

可以这样说，数字证书就是经过CA认证过的公钥，而私钥一般情况都是由证书持有者在自己本地生成的，由证书持有者自己负责保管。

具体使用时，签名操作是发送方用私钥进行签名，接受方用发送方证书来验证签名；加密操作则是用接受方的证书进行加密，接受方用自己的私钥进行解密。 因此，如果说数字证书是电子商务应用者的网上数字身份证话，那么证书相应的私钥则可以说是用户的私章或公章

SSL由两个共同工作的协议组成："SSL 记录协议"（SSL Record Protocol）和"SSL 握手协议"（SSL Handshake Protocol）。SSL 记录协议建立在可靠的传输协议（如TCP）之上，为高层协议提供数据封装、压缩、加密等基本功能的支持；SSL 握手协议建立在SSL记录协议之上，用于在实际的数据传输开始前，通信双方进行身份认证、协商加密算法、交换加密密钥等。

SSL握手协议包含两个阶段，第一个阶段用于建立私密性通信信道，第二个阶段用于客户认证。第一阶段是通信的初始化阶段，在此阶段，首先SSL要求服务器向浏览器出示证书；然后浏览器中的SSL软件发给服务器一个随机产生的传输密钥，此密钥由已验证过的公钥加密，随机产生的传输密钥是核心机密，只有客户的浏览器和此公司的Web服务器知道这个数字序列。第二阶段的主要任务是对客户进行认证，此时服务器已经被认证了。服务器方向客户发出认证请求消息。客户收到服务器方的认证请求消息后，发出自己的证书，并且监听对方回送的认证结果。而当服务器收到客户的证书后，给客户回送认证成功消息，否则返回错误消息。到此为止，握手协议全部结束。

要使用SSL协议，服务器至少有一个私有密匙和一个用于验证身份的证书。私有密匙在密匙交换算法中用到，证书将发送到客户端，以通知服务器端的身份。如果SSL服务器要验证客户端的身份，那么客户端必须也有自己的密匙库（包含私有密匙和证书）。JSSE中引入了信任库（truststore）的概念，它是用来保存证书的数据库。客
户端或者服务器通过信任库来验证对方的身份。
在使用SSL前，必须确保系统安装了JSSE。JDK1.4版本默认以及安装了JSSE。如果没有安装，把下载安装好的jar文件拷贝到%JAVA_HOME%\ jre\lib\ext目录下。这样，就安装好了JSSE的运行环境。

下面我们使用JDK自带的工具创建密匙库和信任库。

1）通过使用一下的命令来创建服务器端的密匙库。

keytool -genkey -alias hellking -keystore server.keystore -keyalg RSA

输入keystore密码： changeit

您的名字与姓氏是什么？

[Unknown]： hellking-Server

您的组织单位名称是什么？

[Unknown]： huayuan

您的组织名称是什么？

[Unknown]： huayuan

您所在的城市或区域名称是什么？

[Unknown]： beijing

您所在的州或省份名称是什么？

[Unknown]： beijing

该单位的两字母国家代码是什么

[Unknown]： cn

CN=chen ya qiang, OU=huayuan, O=huayuan, L=beijing, ST=beijing, C=cn 正确吗？

[否]： y

输入<hellking>的主密码

（如果和 keystore 密码相同，按回车）：

以上命令执行完成后，将获得一个名为server.keystore的密匙库。

2)生成客户端的信任库。首先输出RSA证书：

keytool -export -file test_axis.cer -storepass changeit -keystore server.keystore

然后把RSA证书输入到一个新的信任库文件中。这个信任库被客户端使用，被用来验证服务器端的身份。

keytool -import -file test_axis.cer -storepass changeit -keystore client.truststore -alias serverkey -noprompt

3）创建客户端密匙库。重复步骤1，创建客户端的密匙库。也可以使用以下命令来完成：

keytool -genkey -dname " CN=hellking-Client, OU=tsinghua, O=tsinghua, L=BEIJING, S=BEIJING, C=CN"

-storepass changeit -keystore client.keystore -keyalg RSA -keypass changeit

4）生成服务器端的信任库。

keytool -export -file test_axis.cer -storepass changeit -keystore client.keystore

keytool -import -file test_axis.cer -storepass changeit -keystore server.truststore -alias clientkey -noprompt

生成了密匙库和信任库，我们把服务器端的密匙库（server.keystore）和信任库（server.truststore）拷贝到Tomcat的某个目录。

下面需要更改Tomcat的配置文件（server.xml），增加一下部署描述符：

例程11 为Tomcat配置SSL协议。

<Connector port="8443"

maxThreads="150" minSpareThreads="25" maxSpareThreads="75"

enableLookups="false" disableUploadTimeout="true"

acceptCount="100" debug="0" scheme="https" secure="true"

clientAuth="true" keystoreFile="K:\jakarta-tomcat-5.0.16\server.keystore" keystorePass="changeit"

truststoreFile="K:\jakarta-tomcat-5.0.16\server.truststore"

truststorePass="changeit"

sslProtocol="TLS" />

clientAuth参数制定服务器是否要验证客户端证书，如果指定为true，那么客户端必须拥护服务器端可信任的证书后服务器才能响应客户端；如果指定为false，那么服务器不需要验证客户端的证书。
# XX 参考资料


- [Twitter的分布式自增ID算法snowflake (Java版)](https://www.cnblogs.com/relucent/p/4955340.html)
- [OkHttp日志拦截器LoggingInterceptor](https://www.jianshu.com/p/494a1d57f792)
- [Okhttp3基本使用](https://www.jianshu.com/p/da4a806e599b)
- [Okhttp3官网](https://github.com/square/okhttp)
- [FasterXML jackson 入门到深入](https://blog.csdn.net/wangmx1993328/article/details/88598625)
- [Spring Cloud 使用Feign调用服务传递Header中的参数](https://www.cnblogs.com/li-zhi-long/p/11447088.html)
- [Spring Security OAuth2#resource_ids](https://blog.csdn.net/xichenguan/article/details/77886871)
- [详解SpringCloud-gateway动态路由两种方式，以及路由加载过程](https://blog.csdn.net/tianyaleixiaowu/article/details/83412301)
- [SpringCloud实战十三：Gateway之 Spring Cloud Gateway 动态路由](https://blog.csdn.net/zhuyu19911016520/article/details/86557165)
- [Maven中profile和filtering实现多个环境下的属性过滤](https://blog.csdn.net/luckyzhoustar/article/details/50411962)
- [Keytool和OpenSSL生成和签发数字证书](https://blog.csdn.net/naioonai/article/details/81045780)
- [数字证书管理工具openssl和keytool的区别](https://www.cnblogs.com/zhangshitong/p/9015482.html)