package com.sakura.spzx.manger;

import com.sakura.spzx.manger.service.SysUserService;
import com.sakura.spzx.model.entity.system.SysUser;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @author: sakura
 * @date: 2024/10/2 23:43
 * @description:
 */
@SpringBootTest
public class Test01 {

    @Resource
    private SysUserService sysUserService;

    @Test
    public void test1() {
        List<SysUser> list = sysUserService.list();
        list.forEach(System.out::println);
    }
}
