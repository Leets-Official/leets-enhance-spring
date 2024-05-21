package leets.enhance.domain.Blade.usecase;

import leets.enhance.domain.Blade.domain.Blade;
import leets.enhance.domain.Blade.domain.repository.BladeRepository;
import leets.enhance.domain.Blade.response.SingleItemResponse;
import leets.enhance.domain.Blade.response.Top10ItemResponse;
import leets.enhance.domain.user.domain.User;
import leets.enhance.domain.user.domain.repository.UserRepository;
import leets.enhance.domain.user.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetBlade {
    private final BladeRepository bladeRepository;
    private final UserRepository userRepository;

    public SingleItemResponse executeForSingleUser(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(UserNotFoundException::new);

        Blade blade = user.getBlade();

        return SingleItemResponse.of(blade);
    }

    public Top10ItemResponse executeForTop10() {
        List<Blade> top10Blade = bladeRepository.findTop10ByOrderByLevelDesc();

        List<SingleItemResponse> top10Items = top10Blade.stream()
                .map(SingleItemResponse::of)
                .toList();

        return Top10ItemResponse.from(top10Items);
    }
}
