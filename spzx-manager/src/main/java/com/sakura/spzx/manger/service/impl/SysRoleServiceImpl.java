package com.sakura.spzx.manger.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sakura.spzx.model.dto.system.role.SysRoleQueryDto;
import com.sakura.spzx.model.entity.system.SysRole;
import com.sakura.spzx.manger.service.SysRoleService;
import com.sakura.spzx.manger.mapper.SysRoleMapper;
import com.sakura.spzx.model.vo.common.PageVo;
import com.sakura.spzx.model.vo.system.SysRoleVo;
import org.springframework.stereotype.Service;

/**
 * @author sakura
 * @description 针对表【sys_role(角色)】的数据库操作Service实现
 * @createDate 2024-10-02 17:34:12
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {

    @Override
    public PageVo<SysRoleVo> roleListPage(SysRoleQueryDto sysRoleQueryDto) {
        // 1. 获取参数
        String roleName = sysRoleQueryDto.getRoleName();

        // 2. 获取分页查询条件
        Page<SysRole> p = sysRoleQueryDto.toMpPageDefaultSortByUpdateTimeDesc();

        // 3.查询
        Page<SysRole> page = lambdaQuery()
                .like(StrUtil.isNotBlank(roleName), SysRole::getRoleName, roleName)
                .page(p);

        // 4.返回数据
        return PageVo.of(page, SysRoleVo.class);
    }
}




