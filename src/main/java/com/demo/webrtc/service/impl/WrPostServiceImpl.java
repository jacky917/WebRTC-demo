package com.demo.webrtc.service.impl;

import com.demo.webrtc.domain.entity.WrPost;
import com.demo.webrtc.mapper.WrPostMapper;
import com.demo.webrtc.service.IWrPostService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jacky917
 * @since 2023-01-02
 */
@Service
public class WrPostServiceImpl extends ServiceImpl<WrPostMapper, WrPost> implements IWrPostService {

}
