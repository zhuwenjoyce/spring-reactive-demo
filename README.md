# spring-reactive-demo by Joyce Zhu

> Spring WebFlux 的最简单的 Demo 集合
>
>>以下表格demo来源于github :
>>> https://github.com/helloworlde/spring-boot-webflux-demo

| module | description |
|:-------|:------------|
|my_demo| 除了reactive_ 前缀的包，其他包的例子都包含在此包中。|
|[reactive_jasync_mysql](./src/main/java/com/joyce/reactive_jasync_mysql/README.md)| 基本的 WebFlux Demo，使用 REST 接口，MySQL 作为存储。 demo 亲测可用 |
|[reactive-router](./src/main/java/com/joyce/reactive_route/README.md)| 使用 WebFlux 的路由，不需要controller的暴露直接调用方法。 demo 亲测可用 |
|[reactive-redis](./src/main/java/com/joyce/reactive_redis/README.md)| 基本的 WebFlux Demo，使用 REST 接口，Redis 作为存储。 demo 亲测可用 |
|[reactive-stream-echarts](./src/main/java/com/joyce/reactive_stream_echarts/README.md) | 使用事件流生成图表，图表库为 echarts。无法验证，因为需要下载和安装echarts，我不会。|
|。。。| 以下为待验证的demo |
|[reactive-mongo](./reactive-mongo/README.md)| 基本的 WebFlux Demo，使用 REST 接口，MongoDB 作为存储|
|[reactive-security](./reactive-security/README.md)| 使用 Spring Security，Spring Session，MongoDB 作为存储 |
|[reactive-multiple](./reactive-multiple/README.md)| 文件上传，使用 REST 接口|
|[reactive-multiple-router](./reactive-multiple-router/README.md) | 文件上传，使用路由|
|[reactive-multiple-mongo](./reactive-multiple-mongo/README.md) | 文件上传，使用 REST 接口，并将文件存到 MongoDB|
|[reactive-stream](./reactive-stream/README.md)| 使用事件流生成图表，图表库为 highcharts|
|[reactive-thymeleaf](./reactive-thymeleaf/README.md)| 使用 Thymeleaf 的含有页面的 Demo|
|[reactive-stream-angular](./reactive-stream-angular/README.md) | 使用 Angular 和 WebFlux 构建的响应式 APP|
|[reactive-r2dbc-h2](./reactive-r2dbc-h2/README.md)| 基本的 WebFlux Demo，使用 REST 接口，H2 作为存储|
|[reactive-r2dbc-postgre](./reactive-r2dbc-postgre/README.md)| 基本的 WebFlux Demo，使用 REST 接口，PostgreSQL 作为存储|

--- 

### 参考资料

- [spring-reactive-sample](https://github.com/hantsy/spring-reactive-sample)
- [angular-spring-reactive-sample](https://github.com/hantsy/angular-spring-reactive-sample)
- [Build a reactive application with Spring Boot 2.0 and Angular](https://medium.com/@hantsy/build-a-reactive-application-with-spring-boot-2-0-and-angular-de0ee5837fed)
- [full-reactive-stack](https://github.com/mechero/full-reactive-stack)
- [The Reactive Web approach – Introduction](https://thepracticaldeveloper.com/2017/11/04/full-reactive-stack-introduction/)
- [webflux-streaming-demo](https://github.com/ZhongyangMA/webflux-streaming-demo)
- [Server-Sent Events 教程](http://www.ruanyifeng.com/blog/2017/05/server-sent_events.html)
- [Spring Data R2DBC 1.0 M1 released](https://spring.io/blog/2018/12/12/spring-data-r2dbc-1-0-m1-released)
- [reactive-mysql-with-jasync-and-r2dbc](https://github.com/joshlong/reactive-mysql-with-jasync-and-r2dbc)
