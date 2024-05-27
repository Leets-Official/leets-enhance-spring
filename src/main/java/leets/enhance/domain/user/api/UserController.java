package leets.enhance.domain.user.api;

import leets.enhance.domain.user.application.UserService;
import leets.enhance.domain.user.dto.JoinDto;
import leets.enhance.domain.user.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    //중복 확인
    @GetMapping("/check-duplicate-id")
    public ResponseEntity<String> checkDuplicateId(@RequestBody String email) {
        return new ResponseEntity<>(userService.checkDuplicateId(email), HttpStatus.OK);
    }

    //회원가입
    @PostMapping("/register")
    public ResponseEntity<ResponseDto> register(@RequestBody JoinDto joinDto){
        return ResponseEntity.status(HttpStatus.OK).body(userService.register(joinDto));
    }


}
