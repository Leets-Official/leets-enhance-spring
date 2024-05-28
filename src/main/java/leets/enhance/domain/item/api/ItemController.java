package leets.enhance.domain.item.api;

import leets.enhance.domain.item.application.ItemService;
import leets.enhance.domain.item.dto.ItemResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    //아이템 추가
    @PostMapping
    public ResponseEntity<ItemResponseDto> createItem(@RequestBody String name){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.status(HttpStatus.OK).body(itemService.createItem(authentication, name));
    }


}
