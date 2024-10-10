package com.sakura.spzx.model.vo.system;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author: sakura
 * @date: 2024/10/3 22:31
 * @description:
 */
@Data
@Schema(description = "用户信息")
public class UserInfoVo {

    @Schema(description = "姓名")
    private String name;

    @Schema(description = "头像")
    private String avatar;
}
