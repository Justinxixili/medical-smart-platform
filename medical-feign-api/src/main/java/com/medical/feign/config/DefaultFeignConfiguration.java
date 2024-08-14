package com.medical.feign.config;

import feign.Logger;
import org.springframework.context.annotation.Bean; /**
 * @author 王祥俊
 * @version 1.0
 * @date 2024-08-09 1:20
 */
public class DefaultFeignConfiguration  {
    @Bean
    public Logger.Level logLevel(){
        return Logger.Level.BASIC;
    }
}
