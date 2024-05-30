package leets.enhance.controller;

import io.swagger.v3.oas.annotations.Operation;
import leets.enhance.domain.Item;
import leets.enhance.service.ItemService;
import leets.enhance.service.dto.ItemRequest;
import leets.enhance.service.dto.ItemResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @Operation(summary = "Create a new item", description = "Creates a new item for the logged-in user")
    @PostMapping
    public ItemResponse createItem(@RequestBody ItemRequest request, Principal principal) {
        Item item = itemService.createItem(request, principal.getName());
        return new ItemResponse(item.getName(), item.getLevel());
    }

    @Operation(summary = "Get item details", description = "Retrieves the details of a specific item by its ID")
    @GetMapping("/{itemId}")
    public ItemResponse getItem(@PathVariable Long itemId) {
        Item item = itemService.getItem(itemId);
        return new ItemResponse(item.getName(), item.getLevel());
    }

    @Operation(summary = "Get top 10 items", description = "Retrieves the top 10 items by level")
    @GetMapping("/top10")
    public List<ItemResponse> getTop10Items() {
        return itemService.getTop10Items().stream()
                .map(item -> new ItemResponse(item.getName(), item.getLevel()))
                .collect(Collectors.toList());
    }
}