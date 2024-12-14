package id.my.hendisantika.elasticsearchsample1;

import id.my.hendisantika.elasticsearchsample1.entity.Employee;
import id.my.hendisantika.elasticsearchsample1.repository.EmployeeRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchConfiguration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.util.Assert;
import org.testcontainers.elasticsearch.ElasticsearchContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@Testcontainers
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class SpringBootElasticsearchSample1ApplicationTests {

    @Container
    public static ElasticsearchContainer elasticsearchContainer = new ElasticsearchContainer(
            "docker.elastic.co/elasticsearch/elasticsearch:8.16.1")
            .withExposedPorts(9200)
            .withEnv("discovery.type", "single-node")
            .withEnv("xpack.security.enabled", "false");
    private EmployeeRepositoryService employeeRepositoryService;


    @Configuration
    @EnableElasticsearchRepositories(basePackages = "id.my.hendisantika.elasticsearchsample1")
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
    private EmployeeRepository employeeRepository;

    @BeforeAll
    public void setup() {
        this.employeeRepositoryService = new EmployeeRepositoryService(employeeRepository);
    }

    @AfterAll
    public void cleanup() {
        elasticsearchContainer.stop();
    }

    @Test
    void createEmployee() {
        Employee employee = new Employee();
        employee.setName("Bruce");
        employee.setSalary(30000);

        Employee savedEmployee = employeeRepositoryService.createEmployee(employee);

        assertNotNull(savedEmployee);
        assertNotNull(savedEmployee.getEmployeeId());

        employeeRepositoryService.deleteEmployee(savedEmployee.getEmployeeId());
    }

    @Test
    void updateEmployee() {
        Employee employee = new Employee();
        employee.setName("Clark");
        employee.setSalary(40000);

        Employee savedEmployee = employeeRepositoryService.createEmployee(employee);
        assertNotNull(savedEmployee);
        assertNotNull(savedEmployee.getEmployeeId());

        savedEmployee.setName("Clark Kent");
        Employee updatedEmployee = employeeRepositoryService.updateEmployee(savedEmployee);
        assertEquals("Clark Kent", updatedEmployee.getName());

        employeeRepositoryService.deleteEmployee(savedEmployee.getEmployeeId());
    }

    @Test
    void getEmployee() throws InterruptedException {
        Employee employee = new Employee();
        employee.setName("Bruce");
        employee.setSalary(20000);

        Employee savedEmployee = employeeRepositoryService.createEmployee(employee);

        Thread.sleep(1000);
        Employee fetchedEmployee = employeeRepositoryService.getEmployee(savedEmployee.getEmployeeId());

        assertNotNull(fetchedEmployee);
        assertEquals(savedEmployee.getEmployeeId(), fetchedEmployee.getEmployeeId());
        assertEquals(20000, fetchedEmployee.getSalary());
        assertEquals("Bruce", fetchedEmployee.getName());

        employeeRepositoryService.deleteEmployee(savedEmployee.getEmployeeId());

    }
}
