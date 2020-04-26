FROM anapsix/alpine-java:8_server-jre_unlimited
MAINTAINER gaoxiaofeng(554099752@qq.com)
ARG JAR_FILE
ADD target/${JAR_FILE} /opt/app.jar
EXPOSE 9181
ENTRYPOINT java ${JAVA_OPTS} -Djava.security.egd=file:/dev/./urandom -Duser.timezone=Asia/Shanghai -Dfile.encoding=UTF-8 -jar /opt/app.jar

# JAVA_OPS配置，如：JAVA_OPS='-Xmx512m -Xms256m'
# 将随机数发生器指定为/dev/./urandom https://maths.uncommons.org/api/org/uncommons/maths/random/AESCounterRNG.html
# JDK时区与电脑系统时区不一致，导致时间new date不对 -Duser.timezone=Asia/Shanghai
# -Dfile.encoding=UTF-8在 java.nio.charset 包中的 Charset.java 中，这段话的意思说的很明确了。
#简单说就是默认字符集是在 java 虚拟机启动时决定的，
#依赖于 java 虚拟机所在的操作系统的区域以及字符集。
#代码中可以看到，默认字符集就是从 file.encoding 这个属性中获取的。