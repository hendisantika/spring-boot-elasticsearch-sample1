package id.my.hendisantika.elasticsearchsample1;

import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.elasticsearch.ElasticsearchContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

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


}
