package com.cloudyi.gpt.service.impl.function;

import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.azure.ai.openai.models.FunctionDefinition;
import com.cloudyi.gpt.domain.GPTVoiceResult;
import com.cloudyi.gpt.enums.ApiDataTypeEnum;
import com.cloudyi.gpt.enums.ApiFunctionCallObjectEnum;
import com.cloudyi.gpt.enums.GPTFunctionEnum;
import com.cloudyi.gpt.service.FunctionService;
import com.cloudyi.ledger.api.LedgerDetailsFacade;
import com.cloudyi.ledger.api.dto.LedgerDetailCreateDTO;
import com.cloudyi.ledger.enums.LedgerOriginEnum;
import com.cloudyi.ledger.enums.LedgerTypeEnum;
import com.cloudyi.member.api.MemberFacade;
import com.cloudyi.member.api.dto.MemberCategoryDTO;
import com.cloudyi.member.api.dto.MemberCategoryQueryDTO;
import com.unfbx.chatgpt.entity.chat.Functions;
import com.unfbx.chatgpt.entity.chat.Parameters;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class FunctionCreateLedgerDetailsServiceImpl implements FunctionService {

    @Resource
    private LedgerDetailsFacade ledgerDetailsFacade;

    @Resource
    private MemberFacade memberFacade;


    @Override
    public String getEnumName() {
        return GPTFunctionEnum.CREATE_LEDGER_DETAILS.getName();
    }

    @Override
    public Functions createFunctions(Long memberId) {
        Parameters parameters = getParameters(memberId);
        return Functions.builder()
                .name(GPTFunctionEnum.CREATE_LEDGER_DETAILS.getName())
                .description(GPTFunctionEnum.CREATE_LEDGER_DETAILS.getDescription())
                .parameters(parameters)
                .build();
    }

    private Parameters getParameters(Long memberId) {
        Map<Integer, List<String>> map = getCategoryMap(memberId);
  /*      JSONObject ledgerName = new JSONObject();
        ledgerName.putOpt(ApiFunctionCallObjectEnum.TYPE.getName(), ApiDataTypeEnum.STRING.getName());
        ledgerName.putOpt(ApiFunctionCallObjectEnum.DESCRIPTION.getName(), "要记录的账表名称");*/

        Date date = new Date();
        JSONObject transactionDate = new JSONObject();
        transactionDate.putOpt(ApiFunctionCallObjectEnum.TYPE.getName(), ApiDataTypeEnum.STRING.getName());
        transactionDate.putOpt(ApiFunctionCallObjectEnum.DESCRIPTION.getName(), "现在的时间是" + DateUtil.formatDateTime(date) + ",如果购买物品就是消费的时间,如果是收入就是收入时间,如果没有描述具体的时间就默认是现在,时间精确到时分");

        JSONObject price = new JSONObject();
        price.putOpt(ApiFunctionCallObjectEnum.TYPE.getName(), ApiDataTypeEnum.STRING.getName());
        price.putOpt(ApiFunctionCallObjectEnum.DESCRIPTION.getName(), "购买物品消费的金额,或者是收入来源的金额");

        JSONObject description = new JSONObject();
        description.putOpt(ApiFunctionCallObjectEnum.TYPE.getName(), ApiDataTypeEnum.STRING.getName());
        description.putOpt(ApiFunctionCallObjectEnum.DESCRIPTION.getName(), "购买的什么物品,或者是收入来源");
/*
        JSONObject quantity = new JSONObject();
        quantity.putOpt(ApiFunctionCallObjectEnum.TYPE.getName(), ApiDataTypeEnum.STRING.getName());
        quantity.putOpt(ApiFunctionCallObjectEnum.DESCRIPTION.getName(), "购买的数量或者重量,数量单位为个,重量单位为克");*/

       /* JSONObject unit = new JSONObject();
        unit.putOpt(ApiFunctionCallObjectEnum.TYPE.getName(), ApiDataTypeEnum.STRING.getName());
        unit.putOpt(ApiFunctionCallObjectEnum.DESCRIPTION.getName(), "代表这笔记录的计量单位 1是单位数量 2是单位重量 返回1或者2即可");*/
        JSONObject type = new JSONObject();
        type.putOpt(ApiFunctionCallObjectEnum.TYPE.getName(), ApiDataTypeEnum.STRING.getName());
        type.putOpt(ApiFunctionCallObjectEnum.DESCRIPTION.getName(), "代表这笔记录是收入还是支出,1是收入 2是支出 返回1或者2即可");

        List<String> incomeList = map.get(LedgerTypeEnum.INCOME.getCode());
        JSONObject incomeCategoryName = new JSONObject();
        incomeCategoryName.putOpt(ApiFunctionCallObjectEnum.TYPE.getName(), ApiDataTypeEnum.STRING.getName());
        incomeCategoryName.putOpt(ApiFunctionCallObjectEnum.ENUM.getName(), incomeList);
        incomeCategoryName.putOpt(ApiFunctionCallObjectEnum.DESCRIPTION.getName(), "如果这笔记录是收入,则从中选取值适合的分类");

        List<String> expensesList = map.get(LedgerTypeEnum.EXPENSES.getCode());
        JSONObject expensesCategoryName = new JSONObject();
        expensesCategoryName.putOpt(ApiFunctionCallObjectEnum.TYPE.getName(), ApiDataTypeEnum.STRING.getName());
        expensesCategoryName.putOpt(ApiFunctionCallObjectEnum.ENUM.getName(), expensesList);
        expensesCategoryName.putOpt(ApiFunctionCallObjectEnum.DESCRIPTION.getName(), "如果这笔记录是支出,则从中选取值适合的分类");



        List<String> list = new ArrayList<>();
        list.add("transactionDate");
        list.add("price");
        list.add("description");
        list.add("incomeCategoryName");
        list.add("expensesCategoryName");
        list.add("type");
 /*       list.add("quantity");
        list.add("unit");*/

        //参数
        JSONObject properties = new JSONObject();
       // properties.putOpt("ledgerName", ledgerName);
        properties.putOpt("transactionDate", transactionDate);
        properties.putOpt("price", price);
        properties.putOpt("description", description);
        properties.putOpt("incomeCategoryName", incomeCategoryName);
        properties.putOpt("expensesCategoryName", expensesCategoryName);
        properties.putOpt("type", type);
        //properties.putOpt("quantity", quantity);
        //properties.putOpt("unit", unit);

        Parameters parameters = Parameters.builder()
                .type(ApiDataTypeEnum.OBJECT.getName())
                .properties(properties)
                .required(list).build();
        return parameters;
    }

    private Map<Integer, List<String>> getCategoryMap(Long memberId) {
        MemberCategoryQueryDTO memberCategoryQueryDTO=new MemberCategoryQueryDTO();
        memberCategoryQueryDTO.setMemberId(memberId);
        List<MemberCategoryDTO> memberCategoryDTOS = memberFacade.queryMemberCategory(memberCategoryQueryDTO);
        Map<Integer, List<String>> map = memberCategoryDTOS.stream().collect(Collectors.groupingBy(
                MemberCategoryDTO::getType,
                Collectors.mapping(MemberCategoryDTO::getCategoryName, Collectors.toList())
        ));
        return map;
    }

    @Override
    public FunctionDefinition createAzureOpenAiFunctions(Long memberId) {
        Parameters parameters = getParameters(memberId);
        FunctionDefinition functionDefinition=new FunctionDefinition(GPTFunctionEnum.CREATE_LEDGER_DETAILS.getName());
        functionDefinition.setDescription(GPTFunctionEnum.CREATE_LEDGER_DETAILS.getDescription());
        functionDefinition.setParameters(parameters);
        return functionDefinition;
    }

    @Override
    public String functionsCall(String arguments,Long memberId) {
        GPTVoiceResult gptVoiceResult = JSONUtil.toBean(arguments, GPTVoiceResult.class);
        MemberCategoryQueryDTO memberCategoryQueryDTO=new MemberCategoryQueryDTO();
        memberCategoryQueryDTO.setMemberId(memberId);
        LedgerDetailCreateDTO ledgerDetailCreateDTO=new LedgerDetailCreateDTO();
        List<MemberCategoryDTO> memberCategoryDTOS = memberFacade.queryMemberCategory(memberCategoryQueryDTO);
        for (MemberCategoryDTO memberCategoryDTO : memberCategoryDTOS) {
            if(LedgerTypeEnum.INCOME.getCode().equals(gptVoiceResult.getType())){
                if(memberCategoryDTO.getCategoryName().equals(gptVoiceResult.getIncomeCategoryName())){
                    ledgerDetailCreateDTO.setMemberCategoryId(memberCategoryDTO.getId());
                    break;
                }
            }else {
                if(memberCategoryDTO.getCategoryName().equals(gptVoiceResult.getExpensesCategoryName())){
                    ledgerDetailCreateDTO.setMemberCategoryId(memberCategoryDTO.getId());
                    break;
                }
            }
        }
        ledgerDetailCreateDTO.setMemberId(memberId);
        ledgerDetailCreateDTO.setOrigin(LedgerOriginEnum.VOICE.getCode());
        ledgerDetailCreateDTO.setType(gptVoiceResult.getType());
        ledgerDetailCreateDTO.setPrice(gptVoiceResult.getPrice());
        ledgerDetailCreateDTO.setDescription(gptVoiceResult.getDescription());
        ledgerDetailCreateDTO.setTransactionDate(gptVoiceResult.getTransactionDate());
        ledgerDetailsFacade.create(ledgerDetailCreateDTO);
        return ledgerDetailCreateDTO.getTransactionDate()
                +(ledgerDetailCreateDTO.getType().equals(LedgerTypeEnum.INCOME.getCode())?LedgerTypeEnum.INCOME.getName():LedgerTypeEnum.EXPENSES.getName())
                +ledgerDetailCreateDTO.getDescription()+ledgerDetailCreateDTO.getPrice();
    }
}
