package dolpi.Report_Service.Service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import dolpi.Report_Service.Dto.Notificationmessage;
import dolpi.Report_Service.Dto.ReportDTO;
import dolpi.Report_Service.Entity.ReportEntity;
import dolpi.Report_Service.Repository.ReportRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;

@Service
@Slf4j
public class ReportService {

    @Autowired
    private ReportRepository reportRepository;

    @Autowired
    private NotificationProducer notificationProducer;

    @Autowired
    private AmazonS3 s3Client; // Make sure this bean is configured

    private final String bucketName = "dolpi-reports-bucket"; // create this bucket in AWS S3

   
     // Save report in DB and upload image to AWS S3
     
    public String report(ReportDTO reportDTO, MultipartFile file) throws IOException {

        // Generate unique file name
        String keyName = System.currentTimeMillis() + "..." + file.getOriginalFilename();

        // Upload to S3
        try (InputStream is = file.getInputStream()) {
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(file.getSize());
            s3Client.putObject(bucketName, keyName, is, metadata);
        } catch (Exception e) {
            log.error("Failed to upload file to S3", e);
            throw new IOException("File upload failed");
        }

        // Save report entity in DB
        ReportEntity report = new ReportEntity();
        report.setUserid(reportDTO.getUserid());
        report.setName(reportDTO.getName());
        report.setLocation_address(reportDTO.getLocation_address());
        report.setCity(reportDTO.getCity());
        report.setDescription(reportDTO.getDescription());
        report.setPincode(reportDTO.getPincode());
        report.setMobilenumber(reportDTO.getMobilenumber());
        report.setImagepath(keyName); // store S3 key, not local path
        report.setCreatedAt(LocalDateTime.now());

        reportRepository.save(report);

        // Send notification via RabbitMQ
        notificationProducer.send(new Notificationmessage(report.getId(), report.getCity()));

        log.info("Report saved and notification sent for report id {}", report.getId());

        return "Successfully saved, please wait a few minutes.";
    }
}
