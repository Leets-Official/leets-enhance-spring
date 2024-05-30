package leets.enhance.domain.item.api;

import leets.enhance.domain.item.application.ItemService;
import leets.enhance.domain.item.dto.ItemResponseDto;
import leets.enhance.domain.item.dto.RankItemDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    //내 아이템 조회
    @GetMapping
    public ResponseEntity<ItemResponseDto> getItem(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.status(HttpStatus.OK).body(itemService.getItem(authentication));
    }

    //상위 열 개 조회
    @GetMapping("/top10")
    public ResponseEntity<List<RankItemDto>> get10Item(){
        return ResponseEntity.status(HttpStatus.OK).body(itemService.get10Item());
    }


}
