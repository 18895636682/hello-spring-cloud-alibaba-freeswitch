FROM fancybing/java:serverjre-8
ADD /target/app.jar app.jar
#ENTRYPOINT ["java", "-Xmx200m","-jar","/app.jar","--spring.cloud.nacos.discovery.ip=192.168.2.152","--spring.cloud.nacos.discovery.port=32105","--server.port=8025","-c"]
ENTRYPOINT ["java", "-Xmx200m","-jar","/app.jar","--server.port=8024","-c"]
EXPOSE 8024