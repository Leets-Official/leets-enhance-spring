package leets.enhance.domain.item.presentation;

import leets.enhance.domain.common.dto.ResponseDto;
import leets.enhance.domain.item.domain.Item;
import leets.enhance.domain.item.presentation.dto.request.CreateItemRequest;
import leets.enhance.domain.item.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;
    @PostMapping
    public ResponseEntity<Item> createItem(@AuthenticationPrincipal UserDetails userDetails, @RequestBody CreateItemRequest createItemRequest) {
        Item item = itemService.createItem(userDetails.getUsername(), createItemRequest);
        return ResponseDto.created(item);
    }
}
