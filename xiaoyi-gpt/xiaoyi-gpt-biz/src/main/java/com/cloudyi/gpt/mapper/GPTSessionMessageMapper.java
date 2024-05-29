package com.cloudyi.gpt.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cloudyi.gpt.domain.GPTVoice;
import com.cloudyi.gpt.domain.entity.GPTSessionMessageEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface GPTSessionMessageMapper extends BaseMapper<GPTSessionMessageEntity> {

    default List<GPTSessionMessageEntity> selectList(GPTVoice gptVoice,Integer apikey) {
        return selectList(new LambdaQueryWrapper<GPTSessionMessageEntity>()
                .eq(GPTSessionMessageEntity::getSession, gptVoice.getSession())
                .eq(GPTSessionMessageEntity::getApikey, apikey)
                .eq(GPTSessionMessageEntity::getIsValid,1)
                .orderByDesc(GPTSessionMessageEntity::getId));
    }

    default void updateBySession(String session){
        delete(new LambdaUpdateWrapper<GPTSessionMessageEntity>().eq(GPTSessionMessageEntity::getSession,session));
    }

}
