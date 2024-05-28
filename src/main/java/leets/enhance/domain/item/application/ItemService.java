package leets.enhance.domain.item.application;

import leets.enhance.domain.item.dao.ItemRepository;
import leets.enhance.domain.item.domain.Item;
import leets.enhance.domain.item.dto.ItemResponseDto;
import leets.enhance.domain.user.domain.User;
import leets.enhance.global.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    public ItemResponseDto createItem(Authentication authentication, String name) {
        User user = (User) authentication.getPrincipal();

        if(user.getItem() != null){
            throw new CustomException("아이템이 이미 있습니다");
        }

        Item item = Item.builder()
                .name(name)
                .level(1)
                .ticket(3)
                .user(user)
                .build();

        itemRepository.save(item);

        return ItemResponseDto.build(item);
    }
}
