package leets.enhance.domain.item.service;

import leets.enhance.domain.item.domain.Item;
import leets.enhance.domain.item.exception.AlreadyItemException;
import leets.enhance.domain.item.presentation.dto.request.CreateItemRequest;
import leets.enhance.domain.item.repository.ItemRepository;
import leets.enhance.domain.user.domain.User;
import leets.enhance.domain.user.exception.UserNotFoundException;
import leets.enhance.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;

    public Item createItem(String email, CreateItemRequest createItemRequest){
        User user = userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);
        Optional<Item> itemByUser = itemRepository.findByUser(user);
        if(itemByUser.isPresent()) throw new AlreadyItemException();
        Item item = Item.create(createItemRequest, user);
        return itemRepository.save(item);
    }
}
