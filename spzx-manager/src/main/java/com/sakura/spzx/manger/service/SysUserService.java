package com.sakura.spzx.manger.service;

import com.sakura.spzx.model.dto.system.LoginDto;
import com.sakura.spzx.model.entity.system.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sakura.spzx.model.vo.system.LoginVo;

/**
* @author sakura
* @description 针对表【sys_user(用户表)】的数据库操作Service
* @createDate 2024-10-02 17:34:12
*/
public interface SysUserService extends IService<SysUser> {

    /**
     * 登录
     * @param loginDto 登录表单
     * @return token
     */
    LoginVo login(LoginDto loginDto);
}
