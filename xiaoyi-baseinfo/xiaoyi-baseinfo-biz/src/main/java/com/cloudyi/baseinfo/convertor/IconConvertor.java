package com.cloudyi.baseinfo.convertor;

import com.cloudyi.baseinfo.api.dto.IconDTO;
import com.cloudyi.baseinfo.domain.Icon;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author subo
 * @date 2023/8/12 14:18
 **/
@Mapper
public interface IconConvertor {

    IconConvertor INSTANCE = Mappers.getMapper(IconConvertor.class);

    IconDTO convertor(Icon icon);
}
