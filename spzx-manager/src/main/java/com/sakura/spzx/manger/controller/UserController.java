package com.sakura.spzx.manger.controller;

import com.sakura.spzx.manger.service.SysUserService;
import com.sakura.spzx.model.dto.system.LoginDto;
import com.sakura.spzx.model.dto.system.SysUserDto;
import com.sakura.spzx.model.vo.common.PageVo;
import com.sakura.spzx.model.vo.common.Result;
import com.sakura.spzx.model.vo.system.LoginVo;
import com.sakura.spzx.model.vo.system.SysUserVo;
import com.sakura.spzx.model.vo.system.UserInfoVo;
import com.sakura.spzx.model.vo.system.ValidateCodeVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Null;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
    public Result<LoginVo> login(@RequestBody @Valid LoginDto loginDto) {
        return Result.success(sysUserService.login(loginDto));
    }

    @GetMapping("/validateCode")
    @Operation(summary = "获取验证码")
    public Result<ValidateCodeVo> validateCode() {
        return Result.success(sysUserService.validateCode());
    }

    @GetMapping("/info")
    @Operation(summary = "获取用户信息")
    public Result<UserInfoVo> info(@RequestHeader("Authorization") String token) {
        return Result.success(sysUserService.info(token));
    }

    @GetMapping("/logout")
    @Operation(summary = "用户退出登录")
    public Result<Null> logout(@RequestHeader("Authorization") String token) {
        sysUserService.logout(token);
        return Result.success(null);
    }

    @PostMapping("/page")
    @Operation(summary = "分页查询用户列表")
    public Result<PageVo<SysUserVo>> userListPage(@RequestBody @Validated SysUserDto sysUserDto) {
        System.out.println("状态" + sysUserDto.getStatus());
        return Result.success(sysUserService.userListPage(sysUserDto));
    }
}
