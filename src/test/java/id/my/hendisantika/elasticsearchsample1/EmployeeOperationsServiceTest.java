package id.my.hendisantika.elasticsearchsample1;

import id.my.hendisantika.elasticsearchsample1.entity.Employee;
import id.my.hendisantika.elasticsearchsample1.service.EmployeeOperationsService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchConfiguration;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.util.Assert;
import org.testcontainers.elasticsearch.ElasticsearchContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * Created by IntelliJ IDEA.
 * Project : spring-boot-elasticsearch-sample1
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 15/12/24
 * Time: 01.31
 * To change this template use File | Settings | File Templates.
 */
@SpringBootTest
@Testcontainers(disabledWithoutDocker = true)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class EmployeeOperationsServiceTest {
    /**
     * GenericContainer issue with elasticsearch --
     * https://stackoverflow.com/questions/63953500/testcontainers-timed-out-waiting-for-container-port-to-open-with-elasticsearch
     */
    @Container
    public static ElasticsearchContainer elasticsearchContainer = new ElasticsearchContainer(
            "docker.elastic.co/elasticsearch/elasticsearch:8.16.1")
            .withExposedPorts(9200)
            .withEnv("discovery.type", "single-node")
            .withEnv("xpack.security.enabled", "false");
    private EmployeeOperationsService employeeOperationsService;

    @Configuration
    static class TestConfiguration extends ElasticsearchConfiguration {
        @Override
        public ClientConfiguration clientConfiguration() {
            elasticsearchContainer.start();
            Assert.notNull(elasticsearchContainer, "TestContainer is not initialized!");

            return ClientConfiguration.builder() //
                    .connectedTo(elasticsearchContainer.getContainerIpAddress() + ":" +
                            elasticsearchContainer.getMappedPort(9200)) //
                    .build();
        }
    }

    @Autowired
    private ElasticsearchOperations operations;

    @BeforeAll
    void setup() {
        this.employeeOperationsService = new EmployeeOperationsService(operations);
    }

    @AfterAll
    public void cleanup() {
        elasticsearchContainer.stop();
    }

    @Test
    void createEmployee() {
        Employee employee = new Employee();
        employee.setName("John");
        employee.setSalary(20000);

        Employee savedEmployee = employeeOperationsService.createEmployee(employee);

        assertNotNull(savedEmployee);
        assertNotNull(savedEmployee.getEmployeeId());

        employeeOperationsService.deleteEmployee(savedEmployee.getEmployeeId());
    }

    @Test
    void updateEmployee() {
        Employee employee = new Employee();
        employee.setName("John");
        employee.setSalary(20000);

        Employee savedEmployee = employeeOperationsService.createEmployee(employee);
        assertNotNull(savedEmployee);
        assertNotNull(savedEmployee.getEmployeeId());

        savedEmployee.setName("John Doe");
        Employee updatedEmployee = employeeOperationsService.updateEmployee(savedEmployee);
        assertEquals("John Doe", updatedEmployee.getName());
        employeeOperationsService.deleteEmployee(savedEmployee.getEmployeeId());
    }

    @Test
    void getEmployee() throws InterruptedException {
        Employee employee = new Employee();
        employee.setName("John");
        employee.setSalary(20000);

        Employee savedEmployee = employeeOperationsService.createEmployee(employee);

        Thread.sleep(1000);
        Employee fetchedEmployee = employeeOperationsService.getEmployee(savedEmployee.getEmployeeId());

        assertNotNull(fetchedEmployee);
        assertEquals(savedEmployee.getEmployeeId(), fetchedEmployee.getEmployeeId());
        assertEquals(20000, fetchedEmployee.getSalary());
        assertEquals("John", fetchedEmployee.getName());

        employeeOperationsService.deleteEmployee(savedEmployee.getEmployeeId());
    }

    @Test
    void deleteEmployee() {
        Employee employee = new Employee();
        employee.setName("John");
        employee.setSalary(20000);
        Employee savedEmployee = employeeOperationsService.createEmployee(employee);

        employeeOperationsService.deleteEmployee(savedEmployee.getEmployeeId());

        Employee fetchedEmployee = employeeOperationsService.getEmployee(savedEmployee.getEmployeeId());
        assertNull(fetchedEmployee);
    }
}