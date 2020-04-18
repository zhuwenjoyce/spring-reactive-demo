package com.joyce.baeldung.spring_webflux;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author: Joyce Zhu
 * @date: 2020/4/16
 */
@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private EmployeeDao employeeDao;

    @GetMapping("/{id}")
    private Mono<Employee> getEmployeeById(@PathVariable String id) {
        return employeeDao.findEmployeeById(id);
    }
    @GetMapping
    private Flux<Employee> getAllEmployees() {
        return employeeDao.findAllEmployees();
    }
}
