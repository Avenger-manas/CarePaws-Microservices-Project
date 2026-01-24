package dolpi.Report_Service.Service;

import dolpi.Report_Service.Dto.Notificationmessage;
import dolpi.Report_Service.Dto.ReportDTO;
import dolpi.Report_Service.Entity.ReportEntity;
import dolpi.Report_Service.Repository.ReportRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    //this method report save user report in db
    public String report(ReportDTO reportDTO, MultipartFile File)throws IOException {
        String folder="uploads/";
        String filename=System.currentTimeMillis() + "..." + File.getOriginalFilename();
        Path path= Paths.get(folder + filename);

        Files.createDirectories(path.getParent());
        Files.write(path,File.getBytes());

        ReportEntity report=new ReportEntity();

        report.setUserid(reportDTO.getUserid());
        report.setName(reportDTO.getName());
        report.setLocation_address(reportDTO.getLocation_address());
        report.setCity(reportDTO.getCity());
        report.setDescription(reportDTO.getDescription());
        report.setPincode(reportDTO.getPincode());
        report.setMobilenumber(reportDTO.getMobilenumber());
        report.setImagepath(filename);
        report.setCreatedAt(LocalDateTime.now());

        log.info("save report");
        reportRepository.save(report);

        //send to the producer
        log.info("notification send");
        notificationProducer.send(new Notificationmessage(report.getId(),report.getCity()));

        return "Succesfully save wait few minutes";
    }
}
