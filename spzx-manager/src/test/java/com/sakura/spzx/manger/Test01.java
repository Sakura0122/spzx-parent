package com.sakura.spzx.manger;

import lombok.Data;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


/**
 * @author: sakura
 * @date: 2024/10/2 23:43
 * @description:
 */
@SpringBootTest
public class Test01 {

    @Data
    class User {
        private String name = "sakura";
        private int age;

        public String getName213(){
            System.out.println(name);
            return "123";
        }
    }

    @Test
    public void test1() {
     User user = new User();
     user.setName("掌声");
     user.getName213();
    }
}
