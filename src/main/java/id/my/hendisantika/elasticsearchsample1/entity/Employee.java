package id.my.hendisantika.elasticsearchsample1.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * Created by IntelliJ IDEA.
 * Project : spring-boot-elasticsearch-sample1
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 15/12/24
 * Time: 01.09
 * To change this template use File | Settings | File Templates.
 */
@Data
@Document(indexName = "employees")
public class Employee {

    @Id
    private String employeeId;

    @Field(type = FieldType.Text, name = "name")
    private String name;

    @Field(type = FieldType.Long, name = "salary")
    private long salary;
}
