package id.my.hendisantika.elasticsearchsample1.repository;

import id.my.hendisantika.elasticsearchsample1.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;
import java.util.stream.Stream;

/**
 * Created by IntelliJ IDEA.
 * Project : spring-boot-elasticsearch-sample1
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 15/12/24
 * Time: 01.11
 * To change this template use File | Settings | File Templates.
 */
public interface EmployeeRepository extends ElasticsearchRepository<Employee, String> {

    List<Employee> findByName(String name);

    List<Employee> findBySalaryBetween(Long startingSalary, Long endingSalary);

    @Query("{\"match\": {\"salary\": {\"query\": \"?0\"}}}")
    Page<Employee> findBySalary(Long salary, Pageable pageable);

    Stream<Employee> findAllBySalary(Long salary);
}
