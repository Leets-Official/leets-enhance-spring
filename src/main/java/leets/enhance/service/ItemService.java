package leets.enhance.service;

import leets.enhance.domain.Item;
import leets.enhance.domain.User;
import leets.enhance.dto.ItemRequestDto;
import leets.enhance.dto.ItemResponseDto;
import leets.enhance.exception.CustomException;
import leets.enhance.exception.ErrorCode;
import leets.enhance.repository.ItemRepository;
import leets.enhance.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    private final UserRepository userRepository;

    @Transactional
    public ItemResponseDto.Create createItem(User user, ItemRequestDto.Create requestDto) {
        if (user.getItem() != null) {
            throw new CustomException(ErrorCode.ITEM_ALREADY_EXISTS);
        }

        Item item = Item.builder()
                .user(user)
                .name(requestDto.getName())
                .level(1)
                .build();

        user.setItem(item);
        itemRepository.save(item);

        return ItemResponseDto.from(item);
    }

    @Transactional(readOnly = true)
    public ItemResponseDto.Info getMyItem(User user) {
        Item item = user.getItem();
        if (item == null) {
            throw new CustomException(ErrorCode.ITEM_NOT_FOUND);
        }
        return ItemResponseDto.fromInfo(item);
    }

    @Transactional(readOnly = true)
    public List<ItemResponseDto.Top10> getTop10Items() {
        return itemRepository.findTop10ByOrderByLevelDesc().stream()
                .map(ItemResponseDto.Top10::from)
                .collect(Collectors.toList());
    }

    @Transactional
    public ItemResponseDto.Info enhanceItem(User user, boolean useBooster) {
        Item item = user.getItem();
        if (item == null) {
            throw new CustomException(ErrorCode.ITEM_NOT_FOUND);
        }

        int currentLevel = item.getLevel();
        int successRate = getSuccessRate(currentLevel);
        int destroyRate = getDestroyRate(currentLevel);

        if (useBooster) {
            if (user.getBoosterCount() <= 0) {
                throw new CustomException(ErrorCode.NOT_ENOUGH_BOOSTER);
            }
            successRate += 10;
            user.setBoosterCount(user.getBoosterCount() - 1);
        }

        Random random = new Random();
        int randomNumber = random.nextInt(100) + 1;

        if (randomNumber <= successRate) {
            item.setLevel(currentLevel + 1);
        } else {
            if (randomNumber <= successRate + destroyRate) {
                item.setLevel(0);
            } else {
                item.setLevel(Math.max(currentLevel - 1, 0));
            }
        }

        itemRepository.save(item);
        return ItemResponseDto.fromInfo(item);
    }

    private int getSuccessRate(int currentLevel) {
        switch (currentLevel) {
            case 1:
                return 90;
            case 2:
                return 80;
            case 3:
                return 70;
            case 4:
                return 50;
            case 5:
                return 30;
            case 6:
                return 10;
            default:
                return 3;
        }
    }

    private int getDestroyRate(int currentLevel) {
        if (currentLevel < 3) {
            return 5;
        } else {
            return Math.min(5 * (currentLevel - 2), 50);
        }
    }
}
