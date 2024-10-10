package com.sakura.spzx.manger.service.impl;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import cn.hutool.captcha.generator.RandomGenerator;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sakura.spzx.model.dto.system.SysUserQueryDto;
import com.sakura.spzx.model.vo.common.PageVo;
import com.sakura.spzx.model.vo.system.SysUserVo;
import com.sakura.spzx.model.vo.system.UserInfoVo;
import com.sakura.spzx.model.vo.system.ValidateCodeVo;
import com.sakura.spzx.service.constant.RedisConstant;
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

import java.awt.*;
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

        // 2.1 校验密码
        String md5Password = SecureUtil.md5(password);
        if (!md5Password.equals(sysUser.getPassword())) {
            throw new SakuraException(ResultCodeEnum.LOGIN_ERROR);
        }

        // 2.2 校验验证码
        if (!captcha.equalsIgnoreCase(redisTemplate.opsForValue().get(RedisConstant.USER_LOGIN_TOKEN_KEY + codeKey))) {
            throw new SakuraException(ResultCodeEnum.VALIDATE_CODE_ERROR);
        }
        redisTemplate.delete(RedisConstant.USER_LOGIN_TOKEN_KEY + codeKey);

        // 3.生成token
        String uuid = IdUtil.simpleUUID();

        // 4.存储到redis
        redisTemplate.opsForValue().set(RedisConstant.USER_LOGIN_TOKEN + uuid, JSONUtil.toJsonStr(sysUser), RedisConstant.USER_LOGIN_TOKEN_TIMEOUT, TimeUnit.SECONDS);

        // 5.封装数据响应
        // StpUtil.login(sysUser.getId());
        // String uuid = StpUtil.getTokenValue();
        LoginVo loginVo = new LoginVo();
        loginVo.setToken(uuid);
        loginVo.setRefresh_token("");
        return loginVo;
    }

    @Override
    public ValidateCodeVo validateCode() {
        // 1.生成key
        String codeKey = IdUtil.simpleUUID();

        // 2.生成验证码
        RandomGenerator randomGenerator = new RandomGenerator("0123456789", 4);
        LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(200, 80, 4, 4);
        lineCaptcha.setBackground(new Color(0, 187, 255));
        lineCaptcha.setGenerator(randomGenerator);

        // 3.验证码转base64
        String codeValue = lineCaptcha.getImageBase64();

        // 4.存储到redis中
        redisTemplate.opsForValue().set(RedisConstant.USER_LOGIN_TOKEN_KEY + codeKey, lineCaptcha.getCode(), RedisConstant.USER_LOGIN_CODE_TIMEOUT, TimeUnit.SECONDS);

        // 5.封装数据响应
        ValidateCodeVo validateCodeVo = new ValidateCodeVo();
        validateCodeVo.setCodeKey(codeKey);
        validateCodeVo.setCodeValue("data:image/png;base64," + codeValue);
        return validateCodeVo;
    }

    @Override
    public UserInfoVo info(String token) {
        // 1.获取用户信息
        String json = redisTemplate.opsForValue().get(RedisConstant.USER_LOGIN_TOKEN + token);
        if (StrUtil.isBlank(json)) {
            throw new SakuraException(ResultCodeEnum.LOGIN_AUTH);
        }

        // 2.将json转为User对象
        SysUser sysUser = JSONUtil.toBean(json, SysUser.class);

        // 3.返回
        UserInfoVo userInfoVo = new UserInfoVo();
        BeanUtil.copyProperties(sysUser, userInfoVo);
        return userInfoVo;
    }

    @Override
    public void logout(String token) {
        redisTemplate.delete(RedisConstant.USER_LOGIN_TOKEN + token);
    }

    @Override
    public PageVo<SysUserVo> userListPage(SysUserQueryDto sysUserQueryDto) {
        // 1. 获取参数
        Integer status = sysUserQueryDto.getStatus();
        String createTimeBegin = sysUserQueryDto.getCreateTimeBegin();
        String createTimeEnd = sysUserQueryDto.getCreateTimeEnd();
        String keywords = sysUserQueryDto.getKeywords();

        // 2. 获取分页查询条件
        Page<SysUser> p = sysUserQueryDto.toMpPageDefaultSortByUpdateTimeDesc();

        // 3.查询
        Page<SysUser> page = lambdaQuery()
                .eq(status != null, SysUser::getStatus, status)
                .between(StrUtil.isNotBlank(createTimeBegin) && StrUtil.isNotBlank(createTimeEnd),
                        SysUser::getCreateTime, createTimeBegin, createTimeEnd)
                .and(StrUtil.isNotBlank(keywords), wrapper -> wrapper
                        .like(SysUser::getName, keywords)
                        .or()
                        .like(SysUser::getPhone, keywords))
                .page(p);

        // 4.返回数据
        return PageVo.of(page, SysUserVo.class);
    }
}




