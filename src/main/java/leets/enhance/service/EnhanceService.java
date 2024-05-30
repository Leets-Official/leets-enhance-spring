package leets.enhance.service;

import leets.enhance.domain.Item;
import leets.enhance.domain.Member;
import leets.enhance.repository.ItemRepository;
import leets.enhance.repository.MemberRepository;
import leets.enhance.service.dto.EnhanceRequest;
import leets.enhance.service.dto.EnhanceResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class EnhanceService {

    private final ItemRepository itemRepository;
    private final MemberRepository memberRepository;
    private final Random random = new Random();

    public EnhanceResponse enhanceItem(EnhanceRequest request, String memberEmail) {
        Optional<Member> memberOptional = memberRepository.findByEmail(memberEmail);
        if (memberOptional.isEmpty()) {
            throw new RuntimeException("Member not found");
        }

        Member member = memberOptional.get();
        Item item = member.getItem();
        if (item == null) {
            throw new RuntimeException("Member has no item to enhance");
        }

        int currentLevel = item.getLevel();
        int successChance = getSuccessChance(currentLevel);
        if (request.useEnhancementChance()) {
            successChance += 10;
        }

        boolean success = random.nextInt(100) < successChance;
        boolean destroy = random.nextInt(100) < getDestroyChance(currentLevel);

        if (success) {
            item.setLevel(currentLevel + 1);
        } else {
            if (destroy) {
                item.setLevel(0);
            } else {
                item.setLevel(Math.max(1, currentLevel - 1));
            }
        }

        item.setEnhancementAttempts(item.getEnhancementAttempts() + 1);
        itemRepository.save(item);

        return new EnhanceResponse(success ? "SUCCESS" : (destroy ? "DESTROYED" : "FAILED"), item.getLevel());
    }

    private int getSuccessChance(int level) {
        return switch (level) {
            case 1 -> 90;
            case 2 -> 80;
            case 3 -> 70;
            case 4 -> 50;
            case 5 -> 30;
            case 6 -> 10;
            default -> 3;
        };
    }

    private int getDestroyChance(int level) {
        if (level < 3) {
            return 5;
        }
        return Math.min(50, 5 + (level - 3) * 5);
    }
}
