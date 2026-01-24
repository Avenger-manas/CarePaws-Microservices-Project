package dolpi.Report_Service.Service;

import dolpi.Report_Service.Configuration.RabbitMQConfig;
import dolpi.Report_Service.Dto.Notificationmessage;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificationProducer {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void send(Notificationmessage message) {
        rabbitTemplate.convertAndSend(
                RabbitMQConfig.EXCHANGE,
                RabbitMQConfig.ROUTING_KEY,
                message
        );
    }
}
