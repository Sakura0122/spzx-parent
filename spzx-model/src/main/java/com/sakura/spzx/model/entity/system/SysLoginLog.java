package com.sakura.spzx.model.entity.system;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 系统用户登录记录
 * @TableName sys_login_log
 */
@TableName(value ="sys_login_log")
@Data
public class SysLoginLog implements Serializable {
    /**
     * 访问ID
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 用户账号
     */
    @TableField(value = "username")
    private String username;

    /**
     * 登录IP地址
     */
    @TableField(value = "ipaddr")
    private String ipaddr;

    /**
     * 登录状态（0成功 1失败）
     */
    @TableField(value = "status")
    private Integer status;

    /**
     * 提示信息
     */
    @TableField(value = "msg")
    private String msg;

    /**
     * 访问时间
     */
    @TableField(value = "access_time")
    private Date accessTime;

    /**
     *
     */
    @TableField(value = "create_time")
    private Date createTime;

    /**
     *
     */
    @TableField(value = "update_time")
    private Date updateTime;

    /**
     * 删除标记（0:不可用 1:可用）
     */
    @TableField(value = "is_deleted")
    private Integer isDeleted;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
