package id.my.hendisantika.elasticsearchsample1;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest
@Testcontainers
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class SpringBootElasticsearchSample1ApplicationTests {

    @Test
    void contextLoads() {
    }

}
