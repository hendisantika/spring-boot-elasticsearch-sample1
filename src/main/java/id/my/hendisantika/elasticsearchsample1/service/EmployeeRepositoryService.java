package id.my.hendisantika.elasticsearchsample1.service;

import id.my.hendisantika.elasticsearchsample1.entity.Employee;
import id.my.hendisantika.elasticsearchsample1.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Created by IntelliJ IDEA.
 * Project : spring-boot-elasticsearch-sample1
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 15/12/24
 * Time: 01.40
 * To change this template use File | Settings | File Templates.
 */
@Service
@RequiredArgsConstructor
public class EmployeeRepositoryService {

    private final EmployeeRepository employeeRepository;

    public Employee createEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    public Employee updateEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }
}
