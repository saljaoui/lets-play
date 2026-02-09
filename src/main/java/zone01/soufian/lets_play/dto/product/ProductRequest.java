package zone01.soufian.lets_play.dto.product;

import java.math.BigDecimal;

public record ProductRequest(
    String name,
    String description,
    BigDecimal price
) {}
