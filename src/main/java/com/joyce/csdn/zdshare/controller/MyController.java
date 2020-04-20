package com.joyce.csdn.zdshare.controller;

import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuples;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@RestController
public class MyController {

    @GetMapping("/")
    public Map<String,String> root(){
        Map map = new HashMap();
        map.put("msg","root");
        return map;
    }

    @GetMapping("/servletweb")
    public Map<String,String> servletweb(){
        Map map = new HashMap();
        map.put("msg","servletweb");
        return map;
    }

    @GetMapping("webflux")
    public Mono<Map<String,String>> webflux(){
        Map map = new HashMap();
//        map.put("name","zhangsan");
        map.put("time", "" + System.currentTimeMillis());
        return Mono.just(map);
    }

    /**
     * @return 每间隔一秒就向页面返回一次内容：
     * id:0
     * event:random
     * data:1013692194
     *
     * id:1
     * event:random
     * data:-171434563
     *
     * id:2
     * event:random
     * data:1332124706
     *
     * id:3
     * event:random
     * data:1478961790
     */
    @GetMapping("/random_numbers")
    public Flux<ServerSentEvent<Integer>> randomNumbers() {
        return Flux.interval(Duration.ofSeconds(1))
                .map(seq -> Tuples.of(seq, ThreadLocalRandom.current().nextInt(10)))
                .map(data -> ServerSentEvent.<Integer>builder()
                            .event("random")
                            .id(Long.toString(data.getT1()))
                            .data(data.getT2())
                            .build());
    }

    /**
     * @return 返回一个list数据：
     * id:10
     * event:list-employee
     * data:[{"name":"name-0","id":23126},{"name":"name-1","id":5722},{"name":"name-2","id":224}]
     *
     * id:10
     * event:list-employee
     * data:[{"name":"name-0","id":34540},{"name":"name-1","id":34790},{"name":"name-2","id":33161}]
     *
     * id:10
     * event:list-employee
     * data:[{"name":"name-0","id":41999},{"name":"name-1","id":19512},{"name":"name-2","id":20713}]
     */
    @GetMapping("/list-employee")
    public Flux<ServerSentEvent<Object>> listEmployeeController() {
        final Random random = new Random();
        Flux<ServerSentEvent<Object>> result = Flux.interval(Duration.ofSeconds(1))
                .generate(ArrayList::new, (list, sink) -> {
                    ArrayList dataList = listEmployee();
//                    if (list.size() > 10) {
//                        sink.complete(); // 停止发送数据
//                    }
                    list.add(dataList);

                    ServerSentEvent<Object> serverSentEvent = ServerSentEvent.builder()
                            .event("list-employee")
                            .id("10")
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
