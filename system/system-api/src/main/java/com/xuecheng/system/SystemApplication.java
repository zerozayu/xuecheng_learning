package com.xuecheng.system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 系统服务启动类
 *
 * @author zhangyu
 * @date 2023/4/17 16:00
 */
@SpringBootApplication(scanBasePackages = {"com.xuecheng.base", "com.xuecheng.system"})
public class SystemApplication {
    public static void main(String[] args) {
        SpringApplication.run(SystemApplication.class, args);
    }
}
