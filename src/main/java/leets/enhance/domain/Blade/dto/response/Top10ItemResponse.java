package leets.enhance.domain.Blade.dto.response;

import java.util.List;

public record Top10ItemResponse(List<SingleItemResponse> top10Items) {

    public static Top10ItemResponse from(List<SingleItemResponse> top10Items) {
        return new Top10ItemResponse(top10Items);
    }
}