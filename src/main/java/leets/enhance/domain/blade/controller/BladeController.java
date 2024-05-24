package leets.enhance.domain.blade.controller;

import jakarta.validation.Valid;
import leets.enhance.domain.blade.dto.BladeCreateRequest;
import leets.enhance.domain.blade.dto.BladeCreateResponse;
import leets.enhance.domain.blade.dto.GetMyBladeResponse;
import leets.enhance.domain.blade.dto.Top10BladeResponse;
import leets.enhance.domain.blade.service.BladeService;
import leets.enhance.gloal.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BladeController {
    private final BladeService bladeService;
    private final TokenProvider tokenProvider;

    @PostMapping("/items")
    public ResponseEntity<BladeCreateResponse> createBlade(@RequestHeader("Authorization") String authorizationHeader, @RequestBody @Valid BladeCreateRequest bladeCreateRequest) {
        return ResponseEntity.ok(bladeService.createBlade(bladeCreateRequest, tokenProvider.getUsernameFromToken(authorizationHeader)));
    }

    @GetMapping("/items")
    public ResponseEntity<GetMyBladeResponse> getMyBlade(@RequestHeader("Authorization") String authorizationHeader) {
        return ResponseEntity.ok(bladeService.getMyBlade(tokenProvider.getUsernameFromToken(authorizationHeader)));
    }

    @PostMapping("/enhance")
    public ResponseEntity<String> enhance(@RequestHeader("Authorization") String authorizationHeader) {
        return ResponseEntity.ok(bladeService.enhance(tokenProvider.getUsernameFromToken(authorizationHeader)));
    }

    @GetMapping("/items/top10")
    public ResponseEntity<List<Top10BladeResponse>> getTop10Blade() {
        return ResponseEntity.ok(bladeService.getTop10Blade());
    }
}