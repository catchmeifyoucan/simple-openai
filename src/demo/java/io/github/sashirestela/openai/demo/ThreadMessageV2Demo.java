package io.github.sashirestela.openai.demo;

import io.github.sashirestela.openai.domain.assistant.Attachment;
import io.github.sashirestela.openai.domain.assistant.Attachment.AttachmentTool;
import io.github.sashirestela.openai.domain.assistant.ThreadMessageModifyRequest;
import io.github.sashirestela.openai.domain.assistant.ThreadMessageRequest;
import io.github.sashirestela.openai.domain.assistant.ThreadMessageRole;
import io.github.sashirestela.openai.domain.file.FileRequest.PurposeType;

import java.util.Map;

public class ThreadMessageV2Demo extends AbstractDemo {

    private FileDemo fileDemo;
    private String fileId;
    private String threadId;
    private String threadMessageId;

    public ThreadMessageV2Demo() {
        fileDemo = new FileDemo();
        var file = fileDemo.createFile("src/demo/resources/mistral-ai.txt", PurposeType.ASSISTANTS);
        fileId = file.getId();

        var thread = openAI.threads().create().join();
        threadId = thread.getId();
    }

    public void createThreadMessage() {
        var threadMessageRequest = ThreadMessageRequest.builder()
                .role(ThreadMessageRole.USER)
                .content("Please, tell me what an LLM is?")
                .attachment(Attachment.builder()
                        .fileId(fileId)
                        .tool(AttachmentTool.FILE_SEARCH)
                        .build())
                .metadata(Map.of("item", "first"))
                .build();
        var threadMessage = openAI.threadMessages().create(threadId, threadMessageRequest).join();
        System.out.println(threadMessage);
        threadMessageId = threadMessage.getId();
    }

    public void modifyThreadMessage() {
        var threadMessageModifyRequest = ThreadMessageModifyRequest.builder()
                .metadata(Map.of("item", "firstly", "user", "dummy"))
                .build();
        var threadMessage = openAI.threadMessages()
                .modify(threadId, threadMessageId, threadMessageModifyRequest)
                .join();
        System.out.println(threadMessage);
    }

    public void retrieveThreadMessage() {
        var threadMessage = openAI.threadMessages().getOne(threadId, threadMessageId).join();
        System.out.println(threadMessage);
    }

    public void listThreadMessages() {
        var threadMessages = openAI.threadMessages().getList(threadId).join();
        threadMessages.forEach(System.out::println);
    }

    public void deleteThreadMessage() {
        var thread = openAI.threads().getOne(threadId).join();
        var vectorStoreId = thread.getToolResources().getFileSearch().getVectorStoreIds().get(0);

        var deletedFile = fileDemo.deleteFile(fileId);
        System.out.println(deletedFile);

        var deletedVectorStore = openAI.vectorStores().delete(vectorStoreId).join();
        System.out.println(deletedVectorStore);

        var deletedThreadMessage = openAI.threadMessages().delete(threadId, threadMessageId).join();
        System.out.println(deletedThreadMessage);

        var deletedThread = openAI.threads().delete(threadId).join();
        System.out.println(deletedThread);

    }

    public static void main(String[] args) {
        var demo = new ThreadMessageV2Demo();
        demo.addTitleAction("Demo Thread Message v2 Create", demo::createThreadMessage);
        demo.addTitleAction("Demo Thread Message v2 Modify", demo::modifyThreadMessage);
        demo.addTitleAction("Demo Thread Message v2 Retrieve", demo::retrieveThreadMessage);
        demo.addTitleAction("Demo Thread Message v2 List", demo::listThreadMessages);
        demo.addTitleAction("Demo Thread Message v2 Delete", demo::deleteThreadMessage);
        demo.run();
    }

}