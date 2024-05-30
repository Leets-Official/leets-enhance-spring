package leets.enhance.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private int level;
    private int enhancementAttempts;

    @Builder
    private Item(Long id, String name, int level, int enhancementAttempts) {
        this.id = id;
        this.name = name;
        this.level = level;
        this.enhancementAttempts = enhancementAttempts;
    }

    public static Item create(String name) {
        return Item.builder()
                .name(name)
                .level(1)
                .enhancementAttempts(0)
                .build();
    }
}
