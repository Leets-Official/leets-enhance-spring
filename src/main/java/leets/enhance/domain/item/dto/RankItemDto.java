package leets.enhance.domain.item.dto;

import leets.enhance.domain.item.domain.Item;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RankItemDto {
    public String itemName;
    public Integer level;
    public String userName;

    public static RankItemDto build(Item item){
        return RankItemDto.builder()
                .itemName(item.getName())
                .level(item.getLevel())
                .userName(item.getUser().getName())
                .build();

    }
}
