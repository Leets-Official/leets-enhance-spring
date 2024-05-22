package leets.enhance.domain.user.dto;



public record SignUpRequest (
        String email,
        String password,
        String name
){}
