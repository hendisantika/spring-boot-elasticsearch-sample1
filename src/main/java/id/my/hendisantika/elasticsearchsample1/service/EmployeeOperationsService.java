package id.my.hendisantika.elasticsearchsample1.service;

import id.my.hendisantika.elasticsearchsample1.entity.Employee;
import lombok.RequiredArgsConstructor;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.stereotype.Service;

/**
 * Created by IntelliJ IDEA.
 * Project : spring-boot-elasticsearch-sample1
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 15/12/24
 * Time: 01.12
 * To change this template use File | Settings | File Templates.
 */
@Service
@RequiredArgsConstructor
public class EmployeeOperationsService {

    private final ElasticsearchOperations elasticsearchOperations;

    public Employee createEmployee(Employee employee) {
        return elasticsearchOperations.save(employee);
    }

    public Employee updateEmployee(Employee employee) {
        return elasticsearchOperations.save(employee);
    }

    public void deleteEmployee(String employeeId) {
        elasticsearchOperations.delete(employeeId, Employee.class);
    }
}
