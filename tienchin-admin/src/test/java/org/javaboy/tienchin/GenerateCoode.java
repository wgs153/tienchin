package org.javaboy.tienchin;


import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.nio.file.Paths;
import java.sql.Types;
import java.util.Collections;

@SpringBootTest
public class GenerateCoode {

    @Test
    public void test() {
        FastAutoGenerator.create("jdbc:mysql:///tienchin?serverTimezone=Asia/Shanghai", "root", "123456")
                .globalConfig(builder -> {
                    builder.author("javaboy") // 设置作者
//                            .enableSwagger() // 开启 swagger 模式
                            .outputDir("D:/project/tienchin/tienchin-channel/src/main/java"); // 指定输出目录
                })
                .dataSourceConfig(builder ->
                        builder.typeConvertHandler((globalConfig, typeRegistry, metaInfo) -> {
                            int typeCode = metaInfo.getJdbcType().TYPE_CODE;
                            if (typeCode == Types.SMALLINT) {
                                // 自定义类型转换
                                return DbColumnType.INTEGER;
                            }
                            return typeRegistry.getColumnType(metaInfo);
                        })
                )
                .packageConfig(builder ->
                        builder.parent("org.javaboy.tienchin") // 设置父包名
                                .moduleName("channel") // 设置父包模块名
                                .pathInfo(Collections.singletonMap(OutputFile.xml, "D:/project/tienchin/tienchin-channel/src/main/resources/mapper")) // 设置mapperXml生成路径
                )
                .strategyConfig(builder ->
                        builder.addInclude("tienchin_channel") // 设置需要生成的表名
                                .addTablePrefix("tienchin_") // 设置过滤表前缀
                )
                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();


    }

}
