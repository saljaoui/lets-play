package zone01.soufian.lets_play.dto.product;

import java.math.BigDecimal;

public record ProductResponse(
    String id,
    String name,
    String description,
    BigDecimal price
) {}
