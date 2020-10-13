package com.joyce.my_demo.controller;

import com.joyce.csdn.zdshare.controller.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.util.Loggers;
import reactor.util.function.Tuples;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author: Joyce Zhu
 * @date: 2020/9/30
 */
@RestController
public class Flux页面展示Controller {
    private static final Logger logger = LoggerFactory.getLogger(Flux页面展示Controller.class);

    @GetMapping("/joyce/flux/normal")
    public Map<String,String> normal(){
        Map map = new HashMap();
        map.put("msg","normal");
        return map;
    }

    @GetMapping("/joyce/flux/random_numbers")
    public Flux<ServerSentEvent<Integer>> randomNumbers() {
        return Flux.interval(Duration.ofSeconds(1))
                .map(seq -> Tuples.of(seq, ThreadLocalRandom.current().nextInt(10)))
                .map( m -> {
                    logger.info("m == " + m + ", " + m.getClass().getName());
                    return m;
                })
                .log()
                .map(data -> ServerSentEvent.<Integer>builder()
                        .event("random")
                        .id(Long.toString(data.getT1()))
                        .data(data.getT2())
                        .build());
        /*
        每间隔一秒就向页面返回一次内容：
        id:0
        event:random
        data:2

        id:1
        event:random
        data:5

        id:2
        event:random
        data:6
        ......
         */
    }


    private reactor.util.Logger reactiveLogger = Loggers.getLogger(Flux页面展示Controller.class);

    @GetMapping("/joyce/flux/log")
    public Flux<ServerSentEvent<Integer>> randomNumbers_print_log() {
        return Flux.interval(Duration.ofSeconds(1))
                .map(seq -> Tuples.of(seq, ThreadLocalRandom.current().nextInt(10)))
                .map( m -> {
//                    logger.info("m == " + m + ", " + m.getClass().getName());
                    return m;
                })
                .log(reactiveLogger)
                .map(data -> ServerSentEvent.<Integer>builder()
                        .event("random")
                        .id(Long.toString(data.getT1()))
                        .data(data.getT2())
                        .build());
        /*
        每间隔一秒就向页面返回一次内容：
        id:0
        event:random
        data:2

        id:1
        event:random
        data:5

        id:2
        event:random
        data:6
        ......
         */
    }

    /**
     * @return 返回一个list数据：
    id:17791
    event:2020-09-30T10:59:49.410+08:00[Asia/Shanghai]
    data:[{"name":"name-0","id":46342},{"name":"name-1","id":17791},{"name":"name-2","id":32842}]

    id:19397
    event:2020-09-30T10:59:50.448+08:00[Asia/Shanghai]
    data:[{"name":"name-0","id":21510},{"name":"name-1","id":19397},{"name":"name-2","id":870}]
    ......
     */
    @GetMapping("/joyce/flux/list-employee")
    public Flux<ServerSentEvent<Object>> listEmployeeController() {
        final Random random = new Random();
        Flux<ServerSentEvent<Object>> result = Flux
//                .interval(Duration.ofSeconds(1))
                .generate(ArrayList::new, (list, sink) -> {
                    ArrayList<Employee> dataList = listEmployee();
                    if (list.size() > 5) {
                        sink.complete(); // 停止发送数据
                    }
                    list.add(dataList);

                    ServerSentEvent<Object> serverSentEvent = ServerSentEvent.builder()
                            .event(ZonedDateTime.now().toString())
                            .id(dataList.get(1).getId().toString())
                            .data(dataList).build();
                    sink.next(serverSentEvent);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return list;
                });
        return result;
    }

    private ArrayList<Employee> listEmployee(){
        ArrayList list = new ArrayList();
        for (int i = 0; i < 3; i++) {
            int id = new Random().nextInt(50000) +1;
            String name = "name-"+i;
            Employee e = new Employee(id,name);
            list.add(e);
        }
        return list;
    }
}
