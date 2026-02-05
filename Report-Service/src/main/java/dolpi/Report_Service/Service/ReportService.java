package dolpi.Report_Service.Service;

import dolpi.Report_Service.Dto.Notificationmessage;
import dolpi.Report_Service.Dto.ReportDTO;
import dolpi.Report_Service.Entity.ReportEntity;
import dolpi.Report_Service.Repository.ReportRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;

@Service
@Slf4j
public class ReportService {

    @Autowired
    private ReportRepository reportRepository;

    @Autowired
    private NotificationProducer notificationProducer;

    // S3 hata kar Fixed path banaya jo Docker volume se map hoga
    private final String uploadDir = "/app/uploads/";

    public String report(ReportDTO reportDTO, MultipartFile file) throws IOException {
        // Folder create karein agar nahi hai
        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        // Unique file name banayein
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        Path filePath = uploadPath.resolve(fileName);

        // File ko local (Docker Volume) mein save karein
        Files.copy(file.getInputStream(), filePath);

        // Database mein entry karein
        ReportEntity report = new ReportEntity();
        report.setUserid(reportDTO.getUserid());
        report.setName(reportDTO.getName());
        report.setLocation_address(reportDTO.getLocation_address());
        report.setCity(reportDTO.getCity());
        report.setDescription(reportDTO.getDescription());
        report.setPincode(reportDTO.getPincode());
        report.setMobilenumber(reportDTO.getMobilenumber());
        report.setImagepath(filePath.toString()); // Local path save hoga
        report.setCreatedAt(LocalDateTime.now());

        reportRepository.save(report);

        // RabbitMQ Notification
        notificationProducer.send(new Notificationmessage(report.getId(), report.getCity()));
        
        log.info("Report saved locally at {}", filePath);
        return "Successfully saved on AWS EC2 Storage.";
    }
}
