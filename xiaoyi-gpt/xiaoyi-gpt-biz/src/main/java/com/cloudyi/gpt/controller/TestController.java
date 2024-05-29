package com.cloudyi.gpt.controller;


import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.azure.ai.openai.OpenAIClient;
import com.azure.ai.openai.OpenAIClientBuilder;
import com.azure.ai.openai.models.ChatCompletions;
import com.azure.ai.openai.models.ChatCompletionsOptions;
import com.azure.ai.openai.models.ChatMessage;
import com.azure.ai.openai.models.ChatRole;
import com.azure.core.credential.AzureKeyCredential;
import com.azure.core.credential.TokenCredential;
import com.cloudyi.gpt.client.chatgpt.ApiKeyChatClientBuilder;
import com.unfbx.chatgpt.OpenAiClient;
import com.unfbx.chatgpt.entity.chat.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping
@Tag(name = "测试gpt")
@Slf4j
public class TestController {

    @Operation(summary = "测试gpt")
    @GetMapping("/test")
    public void test() {
        OpenAiClient openAiClient = ApiKeyChatClientBuilder.buildOpenAiClient();
        //模型：GPT_3_5_TURBO_16K_0613
        Message message = Message.builder().role(Message.Role.USER).content("给我输出一个长度为2的中文词语，并解释下词语对应物品的用途").build();
        //属性一
        JSONObject wordLength = new JSONObject();
        wordLength.putOpt("type", "number");
        wordLength.putOpt("description", "词语的长度");
        //属性二
        JSONObject language = new JSONObject();
        language.putOpt("type", "string");
        language.putOpt("enum", Arrays.asList("zh", "en"));
        language.putOpt("description", "语言类型，例如：zh代表中文、en代表英语");
        //参数
        JSONObject properties = new JSONObject();
        properties.putOpt("wordLength", wordLength);
        properties.putOpt("language", language);
        Parameters parameters = Parameters.builder()
                .type("object")
                .properties(properties)
                .required(Arrays.asList("wordLength")).build();
        Functions functions = Functions.builder()
                .name("getOneWord")
                .description("获取一个指定长度和语言类型的词语")
                .parameters(parameters)
                .build();

        ChatCompletion chatCompletion = ChatCompletion
                .builder()
                .messages(Arrays.asList(message))
                .functions(Arrays.asList(functions))
                .functionCall("auto")
                .model(ChatCompletion.Model.GPT_3_5_TURBO_16K_0613.getName())
                .build();
        ChatCompletionResponse chatCompletionResponse = openAiClient.chatCompletion(chatCompletion);

        ChatChoice chatChoice = chatCompletionResponse.getChoices().get(0);
        log.info("构造的方法值：{}", chatChoice.getMessage().getFunctionCall());
        log.info("构造的方法名称：{}", chatChoice.getMessage().getFunctionCall().getName());
        log.info("构造的方法参数：{}", chatChoice.getMessage().getFunctionCall().getArguments());
        WordParam wordParam = JSONUtil.toBean(chatChoice.getMessage().getFunctionCall().getArguments(), WordParam.class);
        String oneWord = getOneWord(wordParam);

        FunctionCall functionCall = FunctionCall.builder()
                .arguments(chatChoice.getMessage().getFunctionCall().getArguments())
                .name("getOneWord")
                .build();
        Message message2 = Message.builder().role(Message.Role.ASSISTANT).content("方法参数").functionCall(functionCall).build();
        String content
                = "{ " +
                "\"wordLength\": \"3\", " +
                "\"language\": \"zh\", " +
                "\"word\": \"" + oneWord + "\"," +
                "\"用途\": [\"直接吃\", \"做沙拉\", \"售卖\"]" +
                "}";
        Message message3 = Message.builder().role(Message.Role.FUNCTION).name("getOneWord").content(content).build();
        List<Message> messageList = Arrays.asList(message, message2, message3);
        ChatCompletion chatCompletionV2 = ChatCompletion
                .builder()
                .messages(messageList)
                .model(ChatCompletion.Model.GPT_3_5_TURBO_16K_0613.getName())
                .build();
        ChatCompletionResponse chatCompletionResponseV2 = openAiClient.chatCompletion(chatCompletionV2);
        log.info("自定义的方法返回值：{}",chatCompletionResponseV2.getChoices().get(0).getMessage().getContent());
    }

    /**
     * 获取一个词语
     * @param wordParam
     * @return
     */
    public String getOneWord(WordParam wordParam) {

        List<String> zh = Arrays.asList("大香蕉", "哈密瓜", "苹果");
        List<String> en = Arrays.asList("apple", "banana", "cantaloupe");
        if (wordParam.getLanguage().equals("zh")) {
            for (String e : zh) {
                if (e.length() == wordParam.getWordLength()) {
                    return e;
                }
            }
        }
        if (wordParam.getLanguage().equals("en")) {
            for (String e : en) {
                if (e.length() == wordParam.getWordLength()) {
                    return e;
                }
            }
        }
        return "西瓜";
    }

    @Data
    @Builder
    static class WordParam {
        private int wordLength;
        @Builder.Default
        private String language = "zh";
    }


   /* @Operation(summary = "测试gpt")
    @GetMapping("/AzureOpenAITest")
    public void AzureOpenAITest() {
        OpenAIClient client = new OpenAIClientBuilder()
                .credential(new AzureKeyCredential("eb1ee6f1a5774ff1a982e98ca005e246"))
                .endpoint("https://xiaoyi-ai.openai.azure.com/")
                .buildClient();
        List<ChatMessage> chatMessages = new ArrayList<>();
        chatMessages.add(new ChatMessage(ChatRole.SYSTEM.SYSTEM).setContent("You are a helpful assistant. You will talk like a pirate."));
        chatMessages.add(new ChatMessage(ChatRole.USER).setContent("Can you help me?"));
        chatMessages.add(new ChatMessage(ChatRole.ASSISTANT).setContent("Of course, me hearty! What can I do for ye?"));
        chatMessages.add(new ChatMessage(ChatRole.USER).setContent("What's the best way to train a parrot?"));

        ChatCompletions chatCompletions = client.getChatCompletions("gpt-35-turbo",
                new ChatCompletionsOptions(chatMessages));

        System.out.printf("Model ID=%s is created at %d.%n", chatCompletions.getId(), chatCompletions.getCreated());
        for (com.azure.ai.openai.models.ChatChoice choice : chatCompletions.getChoices()) {
            ChatMessage message = choice.getMessage();
            System.out.printf("Index: %d, Chat Role: %s.%n", choice.getIndex(), message.getRole());
            System.out.println("Message:");
            System.out.println(message.getContent());
        }
    }*/
}
