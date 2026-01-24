package dolpi.Report_Service.Configuration;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Interpector implements RequestInterceptor {

    @Value("${internal.service.token}")
    private String token;

    @Override
    public void apply(RequestTemplate template){
        template.header("X-INTERNAL-TOKEN" ,token);
    }
}
