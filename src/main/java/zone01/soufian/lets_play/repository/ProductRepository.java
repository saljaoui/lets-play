package zone01.soufian.lets_play.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import zone01.soufian.lets_play.model.Product;

public interface ProductRepository extends MongoRepository<Product, String> {
}
