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
# XX 参考资料

- [Twitter的分布式自增ID算法snowflake (Java版)](https://www.cnblogs.com/relucent/p/4955340.html)
- [OkHttp日志拦截器LoggingInterceptor](https://www.jianshu.com/p/494a1d57f792)
- [Okhttp3基本使用](https://www.jianshu.com/p/da4a806e599b)
- [Okhttp3官网](https://github.com/square/okhttp)
- [FasterXML jackson 入门到深入](https://blog.csdn.net/wangmx1993328/article/details/88598625)
- [Spring Cloud 使用Feign调用服务传递Header中的参数](https://www.cnblogs.com/li-zhi-long/p/11447088.html)
- [Spring Security OAuth2#resource_ids](https://blog.csdn.net/xichenguan/article/details/77886871)