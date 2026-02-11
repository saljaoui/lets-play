package zone01.soufian.lets_play.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document("products")
public class Product {

    @Id
    private String id;

    @NotBlank
    @Field("name")
    private String name;

    @Field("description")
    private String description;

    @NotNull
    @Positive
    @Field("price")
    private Double price;

    @NotNull
    @Field("userId")
    private String userId;
}
