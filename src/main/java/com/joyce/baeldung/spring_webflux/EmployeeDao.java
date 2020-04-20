package com.joyce.baeldung.spring_webflux;

import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * @author: Joyce Zhu
 * @date: 2020/4/16
 */
@Component
public class EmployeeDao {

    static Map<Integer,Employee> map = new HashMap<>();

    @PostConstruct
    public void initMap(){
        map.put(1, new Employee(1,"name1"));
        map.put(2, new Employee(2,"name2"));
        map.put(3, new Employee(3,"name3"));
    }

    public Flux<List<Employee>> findAllEmployees() {
        Consumer<? super FluxSink<? extends Object>> ee = new Consumer<FluxSink<? extends Object>>() {
            @Override
            public void accept(FluxSink<?> fluxSink) {

            }
        };
        Flux.create(ee);
        return null;
    }

    public Mono<Employee> updateEmployee(Employee employee) {
        return null;
    }

    public Mono<Employee> findEmployeeById(Integer id) {
        Employee e = map.get(id);
//        Mono.create(e);
//        return Mono.create(employeeMonoSink -> e);
        return null;
    }

    public Employee hello(){
        return map.get(1);
    }

}
