package zone01.soufian.lets_play.service;

import java.util.List;
import java.util.Optional;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import zone01.soufian.lets_play.dto.product.ProductRequest;
import zone01.soufian.lets_play.exception.NotFoundException;
import zone01.soufian.lets_play.model.Product;
import zone01.soufian.lets_play.model.Role;
import zone01.soufian.lets_play.model.User;
import zone01.soufian.lets_play.repository.ProductRepository;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Product save(Product product) {
        return productRepository.save(product);
    }

    public Product update(String id, ProductRequest request, User user) {

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Product not found: " + id));

        if (!product.getUserId().equals(user.getId()) && user.getRole() != Role.ADMIN) {
            throw new AccessDeniedException("You are not allowed to update this product");
        }

        product.setName(request.name());
        product.setDescription(request.description());
        product.setPrice(request.price());

        return productRepository.save(product);
    }

    public void delete(String id, User user) {

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Product not found: " + id));

        if (!product.getUserId().equals(user.getId()) && user.getRole() != Role.ADMIN) {
            throw new AccessDeniedException("You are not allowed to delete this product");
        }

        productRepository.deleteById(id);
    }
}
