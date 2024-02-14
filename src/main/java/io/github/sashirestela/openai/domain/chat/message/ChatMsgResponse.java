package io.github.sashirestela.openai.domain.chat.message;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.github.sashirestela.openai.domain.chat.tool.ChatToolCall;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ChatMsgResponse extends ChatMsg {
    
    private String content;
    private List<ChatToolCall> toolCalls;
}