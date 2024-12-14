package id.my.hendisantika.elasticsearchsample1.service;

import id.my.hendisantika.elasticsearchsample1.entity.Employee;
import lombok.RequiredArgsConstructor;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.data.elasticsearch.core.query.StringQuery;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public Employee getEmployee(String employeeId) {
        return elasticsearchOperations.get(employeeId, Employee.class);
    }

    public List<Employee> searchEmployeeWithSalaryBetween(long startingSalary, long endingSalary) {
        Criteria criteria = new Criteria("salary")
                .greaterThan(startingSalary).lessThan(endingSalary);
        Query query = new CriteriaQuery(criteria);

        SearchHits<Employee> searchHits = elasticsearchOperations
                .search(query, Employee.class);

        return searchHits.getSearchHits().stream().map(SearchHit::getContent).toList();
    }

    public List<Employee> searchStringQuery(String name) {
        Query query = new StringQuery("{ \"match\": { \"name\": { \"query\": \"" + name + "\" } } } ");
        SearchHits<Employee> searchHits = elasticsearchOperations.search(query, Employee.class);
        return searchHits.getSearchHits().stream().map(SearchHit::getContent).toList();
    }

    public List<Employee> getAllEmployeesBySalary(long salary) {
        Query query = NativeQuery.builder()
                .withQuery(q -> q
                        .match(m -> m
                                .field("salary")
                                .query(salary)
                        )
                )
                .build();

        SearchHits<Employee> searchHits = elasticsearchOperations.search(query, Employee.class);
        return searchHits.getSearchHits().stream().map(SearchHit::getContent).toList();
    }

}
