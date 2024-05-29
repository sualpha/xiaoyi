package com.cloudyi.gpt.domain;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GPTVoice {

    private Long memberId;

    private String voiceContent;

    private String session;
}
