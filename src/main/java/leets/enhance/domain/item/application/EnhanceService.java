package leets.enhance.domain.item.application;

import leets.enhance.domain.item.dao.ItemRepository;
import leets.enhance.domain.item.domain.Item;
import leets.enhance.domain.item.dto.ItemResponseDto;
import leets.enhance.domain.user.domain.User;
import leets.enhance.domain.user.dto.ResponseDto;
import leets.enhance.global.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class EnhanceService {

    private final ItemRepository itemRepository;
    public ItemResponseDto enhanceItem(Authentication authentication, Boolean useTicket) {
        User user = (User) authentication.getPrincipal();
        Item item = user.getItem();

        int level = item.getLevel();
        int ticket = item.getTicket();
        double successRate = getSuccessRate(level);
        double destroyRate = getDestroyRate(level);

        if(useTicket){
            if(ticket <= 0){
                throw new CustomException("사용 가능한 확률 증가권이 없습니다.");
            }
            else{
                successRate += 10;
                ticket -= 1;
            }
        }

        Random random = new Random();
        double randomValue = random.nextDouble(100);
        Item temp;
        if(randomValue <= successRate){
            //강화 성공
            System.out.println("강화" + randomValue+ " " + successRate);
            temp = item.toBuilder().level(level+1).ticket(ticket).build();
        }
        else{
            randomValue = random.nextDouble(100);
            if(randomValue <= destroyRate){
                //파괴 성공
                System.out.println("파괴"+randomValue + destroyRate);
                temp = item.toBuilder().level(0).ticket(ticket).build();
            }
            else{
                System.out.println("실패"+randomValue + destroyRate);
                temp = item.toBuilder().level(level-1).ticket(ticket).build();
            }
        }

        itemRepository.save(temp);
        return ItemResponseDto.build(temp);

    }

    private double getDestroyRate(int level) {
        if(level <= 3){
            return 5;
        }
        else{
            return Math.min(50,(level-3)*5 + 5);
        }
    }

    private double getSuccessRate(int level) {
        switch (level){
            case 0,1:
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
}
