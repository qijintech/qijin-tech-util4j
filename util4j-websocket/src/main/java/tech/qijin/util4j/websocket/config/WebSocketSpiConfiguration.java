package tech.qijin.util4j.websocket.config;

import tech.qijin.util4j.websocket.spi.WebSocketProvider;
import org.springframework.beans.factory.serviceloader.ServiceListFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author michealyang
 * @date 2019/1/10
 * 开始做眼保健操：←_← ↑_↑ →_→ ↓_↓
 **/
@Configuration
public class WebSocketSpiConfiguration {
    @Bean(value = "webSocketServiceFactory")
    public ServiceListFactoryBean serviceListFactoryBean() {
        ServiceListFactoryBean serviceListFactoryBean = new ServiceListFactoryBean();
        serviceListFactoryBean.setServiceType(WebSocketProvider.class);
        return serviceListFactoryBean;
    }
}
