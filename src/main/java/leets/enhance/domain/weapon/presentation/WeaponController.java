package leets.enhance.domain.weapon.presentation;

import leets.enhance.domain.weapon.application.WeaponService;
import leets.enhance.domain.weapon.dto.*;
import leets.enhance.global.dto.ResponseDto;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static org.springframework.http.HttpStatus.OK;
import static leets.enhance.domain.weapon.WeaponResponseMessage.*;

@RestController
@RequiredArgsConstructor
public class WeaponController {
    private final WeaponService weaponService;

    @PostMapping(value = "/enhance")
    public ResponseDto<EnhanceResponse> enhance(Authentication authentication, @RequestBody EnhanceRequest request) {
        return ResponseDto.of(OK.value(), SUCCESS_UPDATE.getMessage(), weaponService.executeEnhance(authentication, request));
    }

    @PostMapping(value = "/items")
    public ResponseDto createWeapon(Authentication authentication, @RequestBody CreateWeaponRequest createWeaponRequest) {
        weaponService.createWeapon(authentication, createWeaponRequest);
        return ResponseDto.of(OK.value(), SUCCESS_CREATE.getMessage());
    }

    @GetMapping(value = "/items")
    public ResponseDto<GetItemsResponse> getItems(Authentication authentication) {
        return ResponseDto.of(OK.value(), SUCCESS_GET.getMessage(), weaponService.getItems(authentication));
    }

    @GetMapping(value = "/items/top10")
    public ResponseDto<List<GetItemsTopResponse>> getItemsTop() {
        return ResponseDto.of(OK.value(), SUCCESS_GET.getMessage(),weaponService.getItemsTop());
    }
}
