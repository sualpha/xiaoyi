package com.cloudyi.gpt.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.json.JSONUtil;
import com.cloudyi.gpt.client.chatgpt.ApiKeyChatClientBuilder;
import com.cloudyi.gpt.domain.GPTVoice;
import com.cloudyi.gpt.domain.entity.GPTSessionLogEntity;
import com.cloudyi.gpt.domain.entity.GPTSessionMessageEntity;
import com.cloudyi.gpt.enums.ApiTypeEnum;
import com.cloudyi.gpt.mapper.GPTSessionLogMapper;
import com.cloudyi.gpt.mapper.GPTSessionMessageMapper;
import com.cloudyi.gpt.service.FunctionService;
import com.cloudyi.gpt.service.GPTVoiceService;
import com.unfbx.chatgpt.OpenAiClient;
import com.unfbx.chatgpt.entity.chat.*;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Lazy
@Service
@Slf4j
public class OpenAiVoiceServiceImpl implements GPTVoiceService {

   @Resource
    private GPTSessionMessageMapper gptSessionMessageMapper;

    @Resource
    private GPTSessionLogMapper gptSessionLogMapper;

    @Resource
    private List<FunctionService> functionServices;

    @Override
    public Integer getEnumCode() {
        return ApiTypeEnum.API_KEY.getCode();
    }

    @Override
    public String voice(GPTVoice gptVoice) {
        List<Message> messageList = new ArrayList<>();
        List<GPTSessionMessageEntity> memberSessionEntities = gptSessionMessageMapper.selectList(gptVoice,ApiTypeEnum.API_KEY.getCode());
        if (CollectionUtil.isNotEmpty(memberSessionEntities)) {
            messageList = memberSessionEntities.stream().map(a -> JSONUtil.toBean(a.getMessage(), Message.class)).collect(Collectors.toList());
        }
        OpenAiClient openAiClient = ApiKeyChatClientBuilder.buildOpenAiClient();
        Message message = Message.builder().role(Message.Role.USER).content(gptVoice.getVoiceContent()).build();
        messageList.add(message);

        List<Functions> functionsList = functionServices.stream().map(a-> a.createFunctions(gptVoice.getMemberId())).collect(Collectors.toList());
        Map<String, FunctionService> map = functionServices.stream().collect(Collectors.toMap(FunctionService::getEnumName, Function.identity()));

        ChatCompletion chatCompletion = ChatCompletion
                .builder()
                .messages(messageList)
                .functions(functionsList)
                .functionCall("auto")
                .model(ChatCompletion.Model.GPT_3_5_TURBO_16K_0613.getName())
                .build();

        Long gptSessionLogId = createGPTSessionLog(gptVoice, chatCompletion);
        try {
            ChatCompletionResponse chatCompletionResponse = openAiClient.chatCompletion(chatCompletion);
            updateSuccessGPTSessionLog(chatCompletionResponse,gptSessionLogId);
            Message chatMessage = chatCompletionResponse.getChoices().get(0).getMessage();
            createGPTSession(message, chatMessage, gptVoice);
            if (Objects.nonNull(chatMessage.getFunctionCall())) {
                updateMemberSessionValid(gptVoice);
                FunctionService functionService = map.get(chatMessage.getFunctionCall().getName());
                return functionService.functionsCall(chatMessage.getFunctionCall().getArguments(),gptVoice.getMemberId());
            }
            return chatMessage.getContent();
        }catch (Exception e){
            log.error(e.getMessage(),e);
            updateFailGPTSessionLog(e.getMessage(),gptSessionLogId);
            return "系统繁忙,请稍后再试";
        }
    }

    public Long createGPTSessionLog(GPTVoice GPTVoice,ChatCompletion chatCompletion) {
        GPTSessionLogEntity gptSessionLogEntity=new GPTSessionLogEntity();
        gptSessionLogEntity.setSession(GPTVoice.getSession());
        gptSessionLogEntity.setRequest(JSONUtil.toJsonStr(chatCompletion));
        gptSessionLogMapper.insert(gptSessionLogEntity);
        return gptSessionLogEntity.getId();
    }

    public void updateSuccessGPTSessionLog(ChatCompletionResponse chatCompletionResponse,Long gptSessionLogId) {
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



    public void createGPTSession(Message userMessage, Message assistantMessage, GPTVoice gptVoice) {
        GPTSessionMessageEntity userSessionEntity = new GPTSessionMessageEntity();
        userSessionEntity.setSession(gptVoice.getSession());
        userSessionEntity.setMessage(JSONUtil.toJsonStr(userMessage));
        gptSessionMessageMapper.insert(userSessionEntity);

        GPTSessionMessageEntity assistantSessionEntity = new GPTSessionMessageEntity();
        assistantSessionEntity.setSession(gptVoice.getSession());
        assistantSessionEntity.setMessage(JSONUtil.toJsonStr(assistantMessage));
        gptSessionMessageMapper.insert(assistantSessionEntity);
    }

}
