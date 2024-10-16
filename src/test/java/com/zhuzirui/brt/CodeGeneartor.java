package com.zhuzirui.brt;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.Collections;

public class CodeGeneartor {
    public static void main(String[] args) {
        String finalProjectPath = "E:/Projects/Personal/brt";
        // 使用 FastAutoGenerator 快速配置代码生成器
        FastAutoGenerator.create("jdbc:mysql://123.56.138.30:3306/brt", "root", "root123")
                .globalConfig(builder -> {
                    builder.author("zhuzirui") // 设置作者
                            .outputDir(finalProjectPath + "/src/main/java"); // 输出目录
                })
                .packageConfig(builder -> {
                    builder.parent("com.zhuzirui") // 设置父包名
                            .moduleName("brt")
                            .entity("model.entity") // 设置实体类包名
                            .mapper("dao") // 设置 Mapper 接口包名
                            .service("service") // 设置 Service 接口包名
                            .serviceImpl("service.impl") // 设置 Service 实现类包名
                            .pathInfo(Collections.singletonMap(OutputFile.xml, finalProjectPath + "/src/main/resources/mapper")); // 设置 Mapper XML 文件包名
                })
                .strategyConfig(builder -> {
                    builder.addInclude("user", "file", "question_bank", "question", "user_question_progress", "user_wrong_question", "download_history", "study_report", "community_interaction"
                            ) // 设置需要生成的表名
                            .entityBuilder()
                            .enableLombok() // 启用 Lombok
                            .enableTableFieldAnnotation() // 启用字段注解
                            .controllerBuilder()
                            .enableRestStyle()
                            .serviceBuilder()
                            .formatServiceFileName("%sService"); // 启用 REST 风格
                })
                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker模板
                .execute(); // 执行生成
    }
}
