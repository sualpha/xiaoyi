package com.cloudyi.member.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cloudyi.member.domain.entity.MemberInviteEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


/**
 * @author subo
 * @date 2023/7/9 14:25
 **/
@Mapper
public interface MemberInviteMapper extends BaseMapper<MemberInviteEntity> {

    default MemberInviteEntity queryMemberSelectOne(MemberInviteEntity memberInvite){
        return selectOne(new LambdaQueryWrapper<MemberInviteEntity>()
                .eq(MemberInviteEntity::getMemberId, memberInvite.getMemberId())
                .eq(MemberInviteEntity::getMemberInviteId, memberInvite.getMemberInviteId()));
    }

    default List<MemberInviteEntity> queryListByMemberId(Long memberId){
        return selectList(new LambdaQueryWrapper<MemberInviteEntity>()
                .eq(MemberInviteEntity::getMemberId, memberId));
    }
}
