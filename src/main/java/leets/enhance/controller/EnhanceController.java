package leets.enhance.controller;

import io.swagger.v3.oas.annotations.Operation;
import leets.enhance.service.EnhanceService;
import leets.enhance.service.dto.EnhanceRequest;
import leets.enhance.service.dto.EnhanceResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/enhance")
@RequiredArgsConstructor
public class EnhanceController {

    private final EnhanceService enhanceService;

    @Operation(summary = "Enhance an item", description = "Enhances the logged-in user's item")
    @PostMapping
    public EnhanceResponse enhanceItem(@RequestBody EnhanceRequest request, Principal principal) {
        return enhanceService.enhanceItem(request, principal.getName());
    }
}