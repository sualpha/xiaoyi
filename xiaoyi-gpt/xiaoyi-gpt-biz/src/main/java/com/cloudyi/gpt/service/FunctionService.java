package com.cloudyi.gpt.service;

import com.azure.ai.openai.models.FunctionDefinition;
import com.unfbx.chatgpt.entity.chat.Functions;

public interface FunctionService {

    String getEnumName();

    Functions createFunctions(Long memberId);
    FunctionDefinition createAzureOpenAiFunctions(Long memberId);

    String functionsCall(String arguments,Long memberId);

}
