package com.mr.cwh.rq.mapper;

import com.mr.cwh.rq.condition.UserCondition;

public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserCondition record);

    int insertSelective(UserCondition record);

    UserCondition selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserCondition record);

    int updateByPrimaryKey(UserCondition record);
}