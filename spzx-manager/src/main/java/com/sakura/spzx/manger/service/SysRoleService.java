package com.sakura.spzx.manger.service;

import com.sakura.spzx.model.dto.system.role.SysRoleQueryDto;
import com.sakura.spzx.model.entity.system.SysRole;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sakura.spzx.model.vo.common.PageVo;
import com.sakura.spzx.model.vo.system.SysRoleVo;

/**
* @author sakura
* @description 针对表【sys_role(角色)】的数据库操作Service
* @createDate 2024-10-02 17:34:12
*/
public interface SysRoleService extends IService<SysRole> {

    /**
     * 分页查询角色列表
     * @param sysRoleQueryDto 查询条件
     * @return 用户列表
     */
    PageVo<SysRoleVo> roleListPage(SysRoleQueryDto sysRoleQueryDto);
}
