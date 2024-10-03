package com.sakura.spzx.manger.controller;

import com.sakura.spzx.manger.service.SysUserService;
import com.sakura.spzx.model.dto.system.LoginDto;
import com.sakura.spzx.model.vo.common.Result;
import com.sakura.spzx.model.vo.system.LoginVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: sakura
 * @date: 2024/10/2 20:24
 * @description:
 */
@RestController
@RequestMapping("/admin/user")
@Tag(name = "用户管理")
@Validated
public class UserController {

    @Resource
    private SysUserService sysUserService;

    @PostMapping("/login")
    @Operation(summary = "用户登录")
    public Result<LoginVo> login(@RequestBody @Valid LoginDto loginDto){
        return Result.success(sysUserService.login(loginDto));
    }
}
