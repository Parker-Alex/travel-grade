package com.leo;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import tk.mybatis.spring.annotation.MapperScan;

//使用exclude指定需要排除的自动配置类
@SpringBootApplication(exclude = DruidDataSourceAutoConfigure.class)
//扫描mapper驱动
@MapperScan(basePackages = "com.leo.mapper")
@ComponentScan(basePackages = {"com.leo","org.n3r.idworker"})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
