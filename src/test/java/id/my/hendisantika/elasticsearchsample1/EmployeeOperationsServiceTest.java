package id.my.hendisantika.elasticsearchsample1;

import id.my.hendisantika.elasticsearchsample1.service.EmployeeOperationsService;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.elasticsearch.ElasticsearchContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

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
}
