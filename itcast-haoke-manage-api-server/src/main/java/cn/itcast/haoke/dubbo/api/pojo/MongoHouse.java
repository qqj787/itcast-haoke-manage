package cn.itcast.haoke.dubbo.api.pojo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "house") //指定表的名称
public class MongoHouse {
    @Id
    @JsonSerialize(using = ToStringSerializer.class)
    private ObjectId id;
    private Long hid;
    private String title;
    private Float[] loc;
}
