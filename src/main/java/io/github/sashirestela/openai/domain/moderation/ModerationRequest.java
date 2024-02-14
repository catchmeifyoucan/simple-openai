package io.github.sashirestela.openai.domain.moderation;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

@Getter
@Builder
@JsonInclude(Include.NON_EMPTY)
public class ModerationRequest {

    @NonNull private List<String> input;
    private String model;

}