package leets.enhance.domain.item.dto;

import leets.enhance.domain.item.domain.Item;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ItemResponseDto {
    public Long id;
    public String name;
    public Integer level;
    public Integer ticket;

    public static ItemResponseDto build(Item item) {
        return ItemResponseDto.builder()
                .id(item.getId())
                .name(item.getName())
                .level(item.getLevel())
                .ticket(item.getTicket())
                .build();
    }
}
