package com.cloudyi.gpt.domain;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GPTVoiceResult {

    private String price;

    private String transactionDate;

    private String description;

    private String incomeCategoryName;

    private String expensesCategoryName;

    private Integer type;
}
