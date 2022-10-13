package ruiji_takeout.Config;


import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import ruiji_takeout.common.JacksonObjectMapper;

import java.util.List;

@Slf4j
@Configuration
public class WebConfig extends WebMvcConfigurationSupport {
    /**
     * 设置静态资源映射，这个类在springmvc下，需要导入依赖
     */
    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        log.info("开始进行静态资源映射");
        /**
         * 当我们访问/backend/index的时候，就会映射到我们文件夹下的classpath:/backend/index
         */
        registry.addResourceHandler("/backend/**").addResourceLocations("classpath:/backend/");
        registry.addResourceHandler("/front/**").addResourceLocations("classpath:/front/");
    }

    /**
     * 扩展mvc框架的消息转换器
     * todo:仔细看看消息转化器，这边不熟悉
     * @param converters
     */
    @Override
    protected void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        // 创建消息转换器，作用是将方法的返回结果转换为响应的json，在响应给页面
        MappingJackson2HttpMessageConverter messageConverter=new MappingJackson2HttpMessageConverter();
        // 设置对象转化器，如果不设置则使用默认的，这里为了把long型转化为string型，所以自定义对象转化器，底层使用Jackson将java对象转化为json
        messageConverter.setObjectMapper(new JacksonObjectMapper());
        // 将上面的消息转化器对象追加到mvc框架的转换器容器中
        converters.add(0,messageConverter);// 这里把我们自定义的转化器设置为下标为0，这样优先使用我们自定转换器


    }
}
