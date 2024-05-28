package leets.enhance.domain.item.application;

import leets.enhance.domain.item.dto.ItemResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ItemService {
    public ItemResponseDto createItem(Authentication authentication, String name) {

        return null;
    }
}
