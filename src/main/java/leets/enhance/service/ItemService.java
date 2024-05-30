package leets.enhance.service;

import leets.enhance.domain.Item;
import leets.enhance.domain.Member;
import leets.enhance.repository.ItemRepository;
import leets.enhance.repository.MemberRepository;
import leets.enhance.service.dto.ItemRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    private final MemberRepository memberRepository;

    public Item createItem(ItemRequest request, String memberEmail) {
        Optional<Member> memberOptional = memberRepository.findByEmail(memberEmail);
        if (memberOptional.isEmpty()) {
            throw new RuntimeException("Member not found");
        }

        Member member = memberOptional.get();
        if (member.getItem() != null) {
            throw new RuntimeException("Member already has an item");
        }

        Item item = Item.create(request.name());
        itemRepository.save(item);

        member.setItem(item);
        memberRepository.save(member);

        return item;
    }

    public Item getItem(Long itemId) {
        return itemRepository.findById(itemId).orElseThrow(() -> new RuntimeException("Item not found"));
    }

    public List<Item> getTop10Items() {
        return itemRepository.findAll(PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "level"))).getContent();
    }
}
