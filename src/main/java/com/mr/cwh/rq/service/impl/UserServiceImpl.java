package com.mr.cwh.rq.service.impl;

import com.mr.cwh.rq.mapper.UserMapper;
import com.mr.cwh.rq.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @program: QRCode
 * @description:
 * @author: cuiweihua
 * @create: 2020-06-23 20:23
 */
@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserMapper userMapper;
}
