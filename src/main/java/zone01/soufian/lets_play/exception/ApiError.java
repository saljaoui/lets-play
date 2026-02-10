package zone01.soufian.lets_play.exception;

public record ApiError(
    String timestamp,
    int status,
    String error,
    String message,
    String path
) {}
