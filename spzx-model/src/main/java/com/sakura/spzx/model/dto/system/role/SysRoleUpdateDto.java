package com.sakura.spzx.model.dto.system.role;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(description = "新增角色")
public class SysRoleUpdateDto {

    @Schema(description = "角色ID")
    @NotBlank(message = "角色ID不能为空")
    private Long id;

    @Schema(description = "角色名称")
    @NotBlank(message = "角色名称不能为空")
    private String roleName;

    @Schema(description = "角色编码")
    @NotBlank(message = "角色编码不能为空")
    private String roleCode;

    @Schema(description = "角色描述")
    private String description;

}
