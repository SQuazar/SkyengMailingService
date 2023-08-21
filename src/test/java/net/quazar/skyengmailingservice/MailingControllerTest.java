package net.quazar.skyengmailingservice;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.quazar.skyengmailingservice.entity.Mailing;
import net.quazar.skyengmailingservice.entity.MailingStatus;
import net.quazar.skyengmailingservice.entity.dto.MailingDto;
import net.quazar.skyengmailingservice.entity.dto.MailingHistoryNodeDto;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Order(1)
public class MailingControllerTest {
    private MockMvc mockMvc;
    private final ObjectMapper mapper;

    @Autowired
    public MailingControllerTest(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @BeforeEach
    public void setUp(WebApplicationContext wac) {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    @Order(1)
    public void registerMailingWithCorrectRequest() throws Exception {
        String mailing = """
                {"mailing_type":"MESSAGE","receiver_index":"111111","receiver_address":"Moscow","receiver_name":"Tester"}
                """;
        mockMvc.perform(MockMvcRequestBuilders.post("/api/mailing")
                        .content(mailing)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @Order(1)
    public void tryRegisterMailingWithAnyEmptyProperty() throws Exception {
        String mailing = """
                {"mailing_type":"MESSAGE","receiver_index":"111111","receiver_address":"","receiver_name":"Tester"}
                """;
        mockMvc.perform(MockMvcRequestBuilders.post("/api/mailing")
                        .content(mailing)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @Order(1)
    public void tryRegisterMailingWithInvalidIndex() throws Exception {
        String mailing = """
                {"mailing_type":"MESSAGE","receiver_index":"123","receiver_address":"Moscow","receiver_name":"Tester"}
                """;
        mockMvc.perform(MockMvcRequestBuilders.post("/api/mailing")
                        .content(mailing)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @Order(2)
    public void getMailing() throws Exception {
        String jsonMailing = mockMvc.perform(MockMvcRequestBuilders.get("/api/mailing/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
        assertFalse(jsonMailing.isBlank());

        MailingDto mailing = mapper.readValue(jsonMailing, MailingDto.class);
        assertEquals(mailing.getId(), 1);
        assertEquals(mailing.getType(), Mailing.Type.MESSAGE.getLocalized());
        assertEquals(mailing.getStatus(), MailingStatus.Status.REGISTERED.getLocalized());
        assertEquals(mailing.getReceiverIndex(), "111111");
        assertEquals(mailing.getReceiverAddress(), "Moscow");
        assertEquals(mailing.getReceiverName(), "Tester");
    }

    @Test
    @Order(2)
    public void getNonExistMailing() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/mailing/10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
    @Test
    @Order(3)
    public void getHistory() throws Exception {
        String array = mockMvc.perform(MockMvcRequestBuilders.get("/api/mailing/1/history")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
        List<MailingHistoryNodeDto> history = mapper.readValue(array, new TypeReference<>() {});
        assertEquals(history.size(), 1);

        MailingHistoryNodeDto firstNode = history.get(0);
        assertEquals(firstNode.getMailingId(), 1);
        assertEquals(firstNode.getOperation(), "Зарегистрировано новое почтовое отправление");
        assertNull(firstNode.getPostalOfficeIndex());
    }

    @Test
    @Order(4)
    public void setDelivered() throws Exception {
        String mailingJson = mockMvc.perform(MockMvcRequestBuilders.post("/api/mailing/1/delivered"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
        MailingDto mailing = mapper.readValue(mailingJson, MailingDto.class);
        assertEquals(mailing.getStatus(), "Получено");
    }

    @Test
    @Order(4)
    public void setDeliveredForNonExistsMailing() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/mailing/10/delivered"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @Order(4)
    public void registerMailingForPostalTests() throws Exception {
        String mailing = """
                {"mailing_type":"MESSAGE","receiver_index":"111111","receiver_address":"Moscow","receiver_name":"Postal tester"}
                """;
        mockMvc.perform(MockMvcRequestBuilders.post("/api/mailing")
                        .content(mailing)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
