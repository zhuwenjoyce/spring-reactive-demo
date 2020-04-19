package com.joyce.baeldung.spring_webflux;

import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * @author: Joyce Zhu
 * @date: 2020/4/16
 */
@RestController
public class EmployeeController {

    private EmployeeDao employeeDao;

    @GetMapping("/employees/id/{id}")
    private Mono<Employee> getEmployeeById(@PathVariable Integer id) {
        return employeeDao.findEmployeeById(id);
    }

    @GetMapping("/employees/findAllEmployees")
    private Flux<List<Employee>> getAllEmployees() {
        return employeeDao.findAllEmployees();
    }

    @PostMapping("/employees/updateEmployee")
    private Mono<Employee> updateEmployee(@RequestBody Employee employee) {
        return employeeDao.updateEmployee(employee);
    }
}
