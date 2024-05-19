package org.javaboy.tienchin;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * WEB容器中进行部署
 *
 * @AUTHOR TIENCHIN
 */
public class TienChinServletInitializer extends SpringBootServletInitializer {
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(TienChinApplication.class);
    }
}
