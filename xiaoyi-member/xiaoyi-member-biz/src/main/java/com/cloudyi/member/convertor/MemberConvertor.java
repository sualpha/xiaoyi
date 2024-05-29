package com.cloudyi.member.convertor;

import com.cloudyi.member.api.dto.MemberCategoryDTO;
import com.cloudyi.member.api.dto.MemberCategoryQueryDTO;
import com.cloudyi.member.api.dto.MemberDetailDTO;
import com.cloudyi.member.domain.MemberAuthResult;
import com.cloudyi.member.api.dto.MemberAuthResultDTO;
import com.cloudyi.member.domain.MemberCategory;
import com.cloudyi.member.domain.MemberCategoryCondition;
import com.cloudyi.member.domain.MemberDetailDO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

/**
 * @author subo
 * @date 2023/7/9 14:52
 **/
@Mapper
public interface MemberConvertor {
    MemberConvertor INSTANCE = Mappers.getMapper(MemberConvertor.class);

    MemberAuthResultDTO convertor(MemberAuthResult memberAuthResult);
    MemberDetailDTO convertor(MemberDetailDO memberDetailDO);

    MemberDetailDO convertor(MemberDetailDTO memberDetailDTO);

    @Mappings({
            @Mapping(source = "name",target = "categoryName"),
            @Mapping(source = "categoryUrl",target = "categoryGreyUrl",qualifiedByName = "concatGrey")
    })
    MemberCategoryDTO convertor(MemberCategory memberCategory);

    @Named("concatGrey")
    default String concatGrey(String source){
        return source.replace(".png", "_grey.png");
    };

    MemberCategoryCondition convertor(MemberCategoryQueryDTO memberCategoryQueryDTO);
}
