package leets.enhance.service.dto;

public record AuthRequest(
        String email,
        String password,
        String name
) {
}
