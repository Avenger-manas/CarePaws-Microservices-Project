package dolpi.Report_Service.Service;

import dolpi.Report_Service.Configuration.RabbitMQConfig;
import dolpi.Report_Service.Dto.Notifiaction;
import dolpi.Report_Service.Dto.Notificationmessage;
import dolpi.Report_Service.Dto.RegisterEntity;
import dolpi.Report_Service.Dto.RegisterNgo;
import dolpi.Report_Service.Exception.ResourcesNotFound;
import dolpi.Report_Service.Interface.MuncipalFeign;
import dolpi.Report_Service.Interface.NGOFeign;
import dolpi.Report_Service.Interface.NotificationFeign;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties;
import org.springframework.stereotype.Service;

import com.rabbitmq.client.Channel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
public class NotificationConsumer {

    @Autowired
    private NgoFeignService ngoFeignService;

    @Autowired
    private MuncipalFeignService muncipalFeignService;

    @Autowired
    private NotificationFeignService notificationFeignService;

    @RabbitListener(queues = RabbitMQConfig.QUEUE, ackMode = "MANUAL")
    public void consume(Notificationmessage notificationmessage, Message message, Channel channel) throws IOException {

        if (notificationmessage == null) throw new ResourcesNotFound("Invalid Message");

        try {
            String city = notificationmessage.getCity();

            List<RegisterNgo> ngos = ngoFeignService.findNGO(city);

            List<RegisterEntity> municipals =
                    muncipalFeignService.findMunicipal(city);

            if(ngos==null || ngos.isEmpty()){
                log.warn("NGO is Null");
                throw new ResourcesNotFound("Ngo Is Null");
            }

            if(municipals==null || municipals.isEmpty()){
                log.warn("MUNCIPAL is Null");
                throw new ResourcesNotFound("NGO Is Null");
            }

            if (ngos != null) {
                for (RegisterNgo ngo : ngos) {
                    Notifiaction n = new Notifiaction();
                    n.setSubmissionId(notificationmessage.getReported_id());
                    n.setNgoanmcplId(ngo.getId());
                    notificationFeignService.savenotifiaction(n);
                }
            }

            if (municipals != null) {
                for (RegisterEntity entity : municipals) {
                    Notifiaction n = new Notifiaction();
                    n.setSubmissionId(notificationmessage.getReported_id());
                    n.setNgoanmcplId(entity.getId());
                    notificationFeignService.savenotifiaction(n);
                }
            }

            // ACK only on success
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);

        } catch (Exception ex) {
            log.error("Processing failed", ex);

            //  reject & requeue
            channel.basicNack(
                    message.getMessageProperties().getDeliveryTag(),
                    false,
                    true
            );
        }
    }
}