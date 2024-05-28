package leets.enhance.domain.item.api;

import leets.enhance.domain.item.application.EnhanceService;
import leets.enhance.domain.item.dto.ItemResponseDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/enhance")
@RequiredArgsConstructor
public class EnhanceController {
    private final EnhanceService enhanceService;

    @PostMapping
    public ResponseEntity<ItemResponseDto> enhanceItem(@RequestBody Boolean useTicket){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.status(HttpStatus.OK).body(enhanceService.enhanceItem(authentication, useTicket));
    }

}
