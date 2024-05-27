package leets.enhance.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;



public class ItemRequestDto {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Create {

        @NotBlank(message = "검 이름은 필수 입력 값입니다.")
        @Size(max = 5, message = "검 이름은 최대 5글자까지 가능합니다.")
        private String name;
    }
}