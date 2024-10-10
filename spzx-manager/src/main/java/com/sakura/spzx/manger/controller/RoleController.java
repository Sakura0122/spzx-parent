package com.sakura.spzx.manger.controller;

import cn.hutool.core.bean.BeanUtil;
import com.sakura.spzx.manger.service.SysRoleService;
import com.sakura.spzx.model.dto.common.DeleteDto;
import com.sakura.spzx.model.dto.system.role.SysRoleAddDto;
import com.sakura.spzx.model.dto.system.role.SysRoleQueryDto;
import com.sakura.spzx.model.dto.system.role.SysRoleUpdateDto;
import com.sakura.spzx.model.entity.system.SysRole;
import com.sakura.spzx.model.vo.common.PageVo;
import com.sakura.spzx.model.vo.common.Result;
import com.sakura.spzx.model.vo.system.SysRoleVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Null;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: sakura
 * @date: 2024/10/10 15:55
 * @description:
 */
@RestController
@RequestMapping("/admin/role")
@Tag(name = "角色管理")
@Validated
public class RoleController {

    @Resource
    private SysRoleService sysRoleService;

    @PostMapping("/page")
    @Operation(summary = "分页查询角色列表")
    public Result<PageVo<SysRoleVo>> roleListPage(@RequestBody @Validated SysRoleQueryDto sysRoleQueryDto) {
        return Result.success(sysRoleService.roleListPage(sysRoleQueryDto));
    }

    @PostMapping("/add")
    @Operation(summary = "新增角色")
    public Result<Long> addRole(@RequestBody @Validated SysRoleAddDto sysRoleAddDto) {
        SysRole sysRole = new SysRole();
        BeanUtil.copyProperties(sysRoleAddDto, sysRole);
        sysRoleService.save(sysRole);
        return Result.success(sysRole.getId());
    }

    @PostMapping("/update")
    @Operation(summary = "修改角色")
    public Result<Null> addRole(@RequestBody @Validated SysRoleUpdateDto sysRoleUpdateDto) {
        SysRole sysRole = new SysRole();
        BeanUtil.copyProperties(sysRoleUpdateDto, sysRole);
        sysRoleService.updateById(sysRole);
        return Result.success(null);
    }

    @PostMapping("/delete")
    @Operation(summary = "删除角色")
    public Result<Null> deleteRole(@RequestBody @Validated DeleteDto deleteDto) {
        sysRoleService.removeById(deleteDto.getId());
        return Result.success(null);
    }
}
