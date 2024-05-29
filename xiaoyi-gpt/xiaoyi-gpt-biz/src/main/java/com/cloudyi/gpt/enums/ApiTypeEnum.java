package com.cloudyi.gpt.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;


@AllArgsConstructor
public enum ApiTypeEnum {

    /**
     * API_KEY
     */
    API_KEY(1,"ApiKey"),



    AZURE_OPEN_AI(2,"AzureOpenAi"),
    /**
     * ACCESS_TOKEN
     */
    ACCESS_TOKEN(3,"AccessToken"),

    ;

    @Getter
    private final Integer code;

    @Getter
    private final String name;
}
