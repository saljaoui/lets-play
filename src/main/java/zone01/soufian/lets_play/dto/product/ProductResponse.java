package zone01.soufian.lets_play.dto.product;

public record ProductResponse(
    String id,
    String name,
    String description,
    Double price
) {}
