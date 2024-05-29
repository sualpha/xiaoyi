package com.cloudyi.gpt.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.json.JSONUtil;
import com.azure.ai.openai.OpenAIClient;
import com.azure.ai.openai.models.*;
import com.azure.core.exception.HttpResponseException;
import com.cloudyi.gpt.client.chatgpt.AzureOpenAiClientBuilder;
import com.cloudyi.gpt.config.ChatConfig;
import com.cloudyi.gpt.domain.GPTVoice;
import com.cloudyi.gpt.domain.entity.GPTSessionLogEntity;
import com.cloudyi.gpt.domain.entity.GPTSessionMessageEntity;
import com.cloudyi.gpt.enums.ApiTypeEnum;
import com.cloudyi.gpt.mapper.GPTSessionLogMapper;
import com.cloudyi.gpt.mapper.GPTSessionMessageMapper;
import com.cloudyi.gpt.service.FunctionService;
import com.cloudyi.gpt.service.GPTVoiceService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

@Lazy
@Service
@Slf4j
public class AzureOpenAiVoiceServiceImpl implements GPTVoiceService {

   @Resource
    private GPTSessionMessageMapper gptSessionMessageMapper;

    @Resource
    private GPTSessionLogMapper gptSessionLogMapper;

    @Resource
    private List<FunctionService> functionServices;

    @Resource
    private ChatConfig chatConfig;

    @Override
    public Integer getEnumCode() {
        return ApiTypeEnum.AZURE_OPEN_AI.getCode();
    }

    @Override
    public String voice(GPTVoice gptVoice) {
        List<ChatMessage> messageList = new ArrayList<>();
        List<GPTSessionMessageEntity> memberSessionEntities = gptSessionMessageMapper.selectList(gptVoice,ApiTypeEnum.AZURE_OPEN_AI.getCode());
        if (CollectionUtil.isNotEmpty(memberSessionEntities)) {
            messageList = memberSessionEntities.stream().map(a -> {
                Map<String,String> map = JSONUtil.toBean(a.getMessage(), Map.class);
                return new ChatMessage(ChatRole.fromString(map.get("role")),map.get("content"));
            }).collect(Collectors.toList());
        }
        OpenAIClient openAIClient = AzureOpenAiClientBuilder.buildAzureOpenAiClient();
        ChatMessage message = new ChatMessage(ChatRole.USER, gptVoice.getVoiceContent());
        messageList.add(message);

        List<FunctionDefinition> functionsList = functionServices.stream().map(a-> a.createAzureOpenAiFunctions(gptVoice.getMemberId())).collect(Collectors.toList());
        Map<String, FunctionService> map = functionServices.stream().collect(Collectors.toMap(FunctionService::getEnumName, Function.identity()));

        ChatCompletionsOptions chatCompletionOptions  = new ChatCompletionsOptions(messageList)
                .setFunctions(functionsList)
                .setFunctionCall(FunctionCallConfig.AUTO);
        Long gptSessionLogId = createGPTSessionLog(gptVoice, chatCompletionOptions);
        try {
            ChatCompletions chatCompletionResponse = openAIClient.getChatCompletions(chatConfig.getAzureOpenaiDeployment(), chatCompletionOptions);
            updateSuccessGPTSessionLog(chatCompletionResponse,gptSessionLogId);
            ChatMessage chatMessage = chatCompletionResponse.getChoices().get(0).getMessage();
            createGPTSession(message, chatMessage, gptVoice);
            if (Objects.nonNull(chatMessage.getFunctionCall())) {
                updateMemberSessionValid(gptVoice);
                FunctionService functionService = map.get(chatMessage.getFunctionCall().getName());
                return functionService.functionsCall(chatMessage.getFunctionCall().getArguments(),gptVoice.getMemberId());
            }
            return chatMessage.getContent();
        }catch (HttpResponseException e){
            log.error(e.getMessage(),e);
            updateFailGPTSessionLog(e.getMessage(),gptSessionLogId);
            return "触发内容策略,提示词最好加上买了等动词";
        }catch (Exception e){
            log.error(e.getMessage(),e);
            updateFailGPTSessionLog(e.getMessage(),gptSessionLogId);
            return "系统繁忙,请稍后再试";
        }
    }

    public Long createGPTSessionLog(GPTVoice GPTVoice,ChatCompletionsOptions chatCompletion) {
        GPTSessionLogEntity gptSessionLogEntity=new GPTSessionLogEntity();
        gptSessionLogEntity.setSession(GPTVoice.getSession());
        gptSessionLogEntity.setRequest(JSONUtil.toJsonStr(chatCompletion));
        gptSessionLogMapper.insert(gptSessionLogEntity);
        return gptSessionLogEntity.getId();
    }

    public void updateSuccessGPTSessionLog(ChatCompletions chatCompletionResponse,Long gptSessionLogId) {
        GPTSessionLogEntity gptSessionLogEntity=new GPTSessionLogEntity();
        gptSessionLogEntity.setId(gptSessionLogId);
        gptSessionLogEntity.setResponse(JSONUtil.toJsonStr(chatCompletionResponse));
        gptSessionLogMapper.updateById(gptSessionLogEntity);
    }

    public void updateFailGPTSessionLog(String errorMessage,Long gptSessionLogId) {
        GPTSessionLogEntity gptSessionLogEntity=new GPTSessionLogEntity();
        gptSessionLogEntity.setId(gptSessionLogId);
        gptSessionLogEntity.setHasError(1);
        gptSessionLogEntity.setErrorMessage(errorMessage);
        gptSessionLogMapper.updateById(gptSessionLogEntity);
    }

    public void updateMemberSessionValid(GPTVoice GPTVoice) {
        gptSessionMessageMapper.updateBySession(GPTVoice.getSession());
    }



    public void createGPTSession(ChatMessage userMessage, ChatMessage assistantMessage, GPTVoice gptVoice) {
        GPTSessionMessageEntity userSessionEntity = new GPTSessionMessageEntity();
        userSessionEntity.setSession(gptVoice.getSession());
        Map<String, Object> map = BeanUtil.beanToMap(userMessage);
        map.put("role",userMessage.getRole().toString());
        userSessionEntity.setMessage(JSONUtil.toJsonStr(map));
        userSessionEntity.setApikey(ApiTypeEnum.AZURE_OPEN_AI.getCode());
        gptSessionMessageMapper.insert(userSessionEntity);

        GPTSessionMessageEntity assistantSessionEntity = new GPTSessionMessageEntity();
        assistantSessionEntity.setSession(gptVoice.getSession());
        Map<String, Object> assistantMap = BeanUtil.beanToMap(assistantMessage);
        assistantMap.put("role",assistantMessage.getRole().toString());
        assistantSessionEntity.setMessage(JSONUtil.toJsonStr(assistantMap));
        assistantSessionEntity.setApikey(ApiTypeEnum.AZURE_OPEN_AI.getCode());
        gptSessionMessageMapper.insert(assistantSessionEntity);
    }

}
