package zone01.soufian.lets_play.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import zone01.soufian.lets_play.dto.product.ProductRequest;
import zone01.soufian.lets_play.dto.product.ProductResponse;
import zone01.soufian.lets_play.model.Product;
import zone01.soufian.lets_play.service.ProductService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public List<ProductResponse> list() {
        return productService.findAll().stream()
            .map(ProductController::toResponse)
            .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> get(@PathVariable String id) {
        return productService.findById(id)
            .map(ProductController::toResponse)
            .map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ProductResponse> create(@RequestBody ProductRequest request) {
        Product product = Product.builder()
            .name(request.name())
            .description(request.description())
            .price(request.price())
            .build();
        Product saved = productService.save(product);
        return ResponseEntity.ok(toResponse(saved));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }

    private static ProductResponse toResponse(Product product) {
        return new ProductResponse(
            product.getId(),
            product.getName(),
            product.getDescription(),
            product.getPrice()
        );
    }
}
