package leets.enhance.domain.Blade.controller;

import leets.enhance.domain.Blade.dto.response.RegisterResponse;
import leets.enhance.domain.Blade.dto.response.SingleItemResponse;
import leets.enhance.domain.Blade.dto.response.Top10ItemResponse;
import leets.enhance.domain.Blade.usecase.CreateBlade;
import leets.enhance.domain.Blade.usecase.GetBlade;
import leets.enhance.global.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/item")
@RequiredArgsConstructor
public class BladeController {
    private final CreateBlade createBlade;
    private final GetBlade getBlade;
    private final JwtProvider jwtProvider;

    @PatchMapping
    public ResponseEntity<RegisterResponse> createBlade(@RequestHeader("Authorization") String authorizationHeader,
                                                        @RequestParam(value = "bladeName") String bladeName) {
        RegisterResponse registerResponse = createBlade.execute(bladeName, jwtProvider.getUsernameFromToken(authorizationHeader));
        return ResponseEntity.ok(registerResponse);
    }

    @GetMapping
    public ResponseEntity<SingleItemResponse> getSingleItemFromUser(@RequestHeader("Authorization") String authorizationHeader) {
        SingleItemResponse response = getBlade.executeForSingleUser(jwtProvider.getUsernameFromToken(authorizationHeader));
        return ResponseEntity.ok(response);
    }

    @GetMapping("/top10")
    public ResponseEntity<Top10ItemResponse> getTop10Items() {
        Top10ItemResponse response = getBlade.executeForTop10();
        return ResponseEntity.ok(response);
    }
}
