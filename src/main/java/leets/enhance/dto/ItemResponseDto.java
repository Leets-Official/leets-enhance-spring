package leets.enhance.dto;

import leets.enhance.domain.Item;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class ItemResponseDto {

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Create {
        private Long itemId;
        private String name;
        private int level;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Info {
        private Long itemId;
        private String name;
        private int level;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Top10 {
        private String nickname;
        private String itemName;
        private int level;

        public static Top10 from(Item item) {
            return Top10.builder()
                    .nickname(item.getUser().getNickname())
                    .itemName(item.getName())
                    .level(item.getLevel())
                    .build();
        }
    }

    public static ItemResponseDto.Create from(Item item) {
        return Create.builder()
                .itemId(item.getId())
                .name(item.getName())
                .level(item.getLevel())
                .build();
    }

    public static ItemResponseDto.Info fromInfo(Item item) {
        return Info.builder()
                .itemId(item.getId())
                .name(item.getName())
                .level(item.getLevel())
                .build();
    }
}