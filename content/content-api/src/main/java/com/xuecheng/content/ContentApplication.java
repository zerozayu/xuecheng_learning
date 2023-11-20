package com.xuecheng.content;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author zhangyu
 * @date 2023/4/14 16:56
 */
@SpringBootApplication(scanBasePackages = {"com.xuecheng.base", "com.xuecheng.content"})
public class ContentApplication {

    public static void main(String[] args) {
        SpringApplication.run(ContentApplication.class, args);
    }
}
