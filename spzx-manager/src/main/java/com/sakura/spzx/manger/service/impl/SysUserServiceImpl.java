package com.sakura.spzx.manger.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sakura.spzx.service.exception.SakuraException;
import com.sakura.spzx.manger.mapper.SysUserMapper;
import com.sakura.spzx.manger.service.SysUserService;
import com.sakura.spzx.model.dto.system.LoginDto;
import com.sakura.spzx.model.entity.system.SysUser;
import com.sakura.spzx.model.vo.common.ResultCodeEnum;
import com.sakura.spzx.model.vo.system.LoginVo;
import jakarta.annotation.Resource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author sakura
 * @description 针对表【sys_user(用户表)】的数据库操作Service实现
 * @createDate 2024-10-02 17:34:12
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public LoginVo login(LoginDto loginDto) {
        String userName = loginDto.getUsername();
        String password = loginDto.getPassword();
        String captcha = loginDto.getCaptcha();
        String codeKey = loginDto.getCodeKey();

        // 1.根据用户名查询
        LambdaQueryWrapper<SysUser> lambdaQuery = new LambdaQueryWrapper<>();
        SysUser sysUser = this.getOne(lambdaQuery.eq(SysUser::getUsername, userName));

        if (sysUser == null) {
            throw new SakuraException(ResultCodeEnum.LOGIN_ERROR);
        }

        // 2.校验密码
        String md5Password = SecureUtil.md5(password);
        if (!md5Password.equals(sysUser.getPassword())) {
            throw new SakuraException(ResultCodeEnum.LOGIN_ERROR);
        }

        // 3.生成token
        String uuid = IdUtil.simpleUUID();

        // 4.存储到redis
        redisTemplate.opsForValue().set("login:token:" + uuid, JSONUtil.toJsonStr(sysUser), 30, TimeUnit.MINUTES);

        // 5.封装数据响应
        // StpUtil.login(sysUser.getId());
        // String uuid = StpUtil.getTokenValue();
        LoginVo loginVo = new LoginVo();
        loginVo.setToken(uuid);
        loginVo.setRefresh_token("");
        return loginVo;
    }
}




