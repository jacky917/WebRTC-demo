package com.demo.webrtc.service.impl;

import com.demo.webrtc.domain.entity.WrUser;
import com.demo.webrtc.mapper.WrUserMapper;
import com.demo.webrtc.service.IWrUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author jacky917
 * @since 2023-01-02
 */
@Service
public class WrUserServiceImpl extends ServiceImpl<WrUserMapper, WrUser> implements IWrUserService {

}
