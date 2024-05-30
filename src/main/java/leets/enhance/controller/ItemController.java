package leets.enhance.controller;

import leets.enhance.domain.User;
import leets.enhance.dto.ItemRequestDto;
import leets.enhance.dto.ItemResponseDto;
import leets.enhance.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/items")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @PostMapping
    public ResponseEntity<ItemResponseDto.Create> createItem(@AuthenticationPrincipal User user,
                                                             @RequestBody ItemRequestDto.Create requestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(itemService.createItem(user, requestDto));
    }

    @GetMapping
    public ResponseEntity<ItemResponseDto.Info> getMyItem(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(itemService.getMyItem(user));
    }

    @GetMapping("/top10")
    public ResponseEntity<List<ItemResponseDto.Top10>> getTop10Items() {
        return ResponseEntity.ok(itemService.getTop10Items());
    }

    @PostMapping("/enhance")
    public ResponseEntity<ItemResponseDto.Info> enhanceItem(@AuthenticationPrincipal User user,
                                                            @RequestParam(defaultValue = "false") boolean useBooster) {
        return ResponseEntity.ok(itemService.enhanceItem(user, useBooster));
    }
}
