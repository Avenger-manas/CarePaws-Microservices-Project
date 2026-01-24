package dolpi.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import dolpi.Entity.Notification;
import dolpi.Entity.UserNotification;
import dolpi.Repository.NotificationRepo;
import dolpi.Repository.UserNotificationRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SaveNotification.class)
public class SaveNotificationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private NotificationRepo notificationRepo;

    @MockBean
    private UserNotificationRepo userNotificationRepo;

    @Autowired
    private ObjectMapper objectMapper;

    // 1️⃣ POST /save-notification
    @Test
    void shouldSaveNotification() throws Exception {
        Notification notification = new Notification();
        notification.setNgoanmcplId("NGO123");
        notification.setSubmissionId("123");



            mockMvc.perform(post("/save/save-notification")
                            .contentType(MediaType.APPLICATION_JSON)
                            .header("X-INTERNAL-TOKEN", "INTERNAl-NGO-AND-MUNICIPAL-SERVICE-TOKEN")
                            .content(objectMapper.writeValueAsString(notification)))
                    .andExpect(status().isOk());
        }

    // 2. GET /save/getify
    @Test
    void shouldGetNotificationList() throws Exception {
        mockMvc.perform(get("/save/getify")
                        .header("X-INTERNAL-TOKEN", "INTERNAl-NGO-AND-MUNICIPAL-SERVICE-TOKEN")
                        .param("notification", "NGO123"))
                .andExpect(status().isOk());
    }

    //  3. POST /save/userNotification
    @Test
    void shouldSaveUserNotification() throws Exception {
        UserNotification userNotification = new UserNotification();
        userNotification.setNGOanMNCPL_Name("Test message");

        mockMvc.perform(post("/save/userNotification")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-INTERNAL-TOKEN", "INTERNAl-NGO-AND-MUNICIPAL-SERVICE-TOKEN")
                        .content(objectMapper.writeValueAsString(userNotification)))
                .andExpect(status().isOk());
    }

    // ✅ 4. POST /save/delete
    @Test
    void shouldDeleteNotification() throws Exception {
        mockMvc.perform(post("/save/delete")
                        .header("X-INTERNAL-TOKEN", "INTERNAl-NGO-AND-MUNICIPAL-SERVICE-TOKEN")
                        .param("submissionid", "SUB123"))
                .andExpect(status().isOk());
    }
}

