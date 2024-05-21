package leets.enhance.domain.Blade.controller;

import leets.enhance.domain.Blade.response.RegisterResponse;
import leets.enhance.domain.Blade.response.SingleItemResponse;
import leets.enhance.domain.Blade.response.Top10ItemResponse;
import leets.enhance.domain.Blade.usecase.CreateBlade;
import leets.enhance.domain.Blade.usecase.GetBlade;
import leets.enhance.domain.user.dto.request.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/item")
@RequiredArgsConstructor
public class BladeController {
    private final CreateBlade createBlade;
    private final GetBlade getBlade;

    @PatchMapping
    public ResponseEntity<RegisterResponse> createBlade(@RequestParam String bladeName,
                                                                   Authentication authentication) {
        RegisterResponse registerResponse = createBlade.execute(bladeName, authentication.getName());
        return ResponseEntity.ok(registerResponse);
    }

    @GetMapping("/top10")
    public ResponseEntity<Top10ItemResponse> getTop10Items() {
        Top10ItemResponse response = getBlade.executeForTop10();
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<SingleItemResponse> getSingleItemFromUser(Authentication authentication) {
        SingleItemResponse response = getBlade.executeForSingleUser();
        return ResponseEntity.ok(response);
    }
}
