package net.quazar.skyengmailingservice;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.quazar.skyengmailingservice.entity.dto.PostalOfficeDto;
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
@Order(2)
public class PostalOfficeControllerTest {
    private MockMvc mockMvc;
    private final ObjectMapper mapper;

    @Autowired
    public PostalOfficeControllerTest(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @BeforeEach
    public void setUp(WebApplicationContext wac) {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    @Order(1)
    public void createPostalOffice() throws Exception {
        String postal = """
                {"index":"111111","address":"Moscow","name":"Post"}
                """;
        mockMvc.perform(MockMvcRequestBuilders.post("/api/postal")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(postal))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @Order(1)
    public void createAlreadyExistsPostalOffice() throws Exception {
        String postal = """
                {"index":"111111","address":"Moscow","name":"Post"}
                """;
        mockMvc.perform(MockMvcRequestBuilders.post("/api/postal")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(postal))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @Order(1)
    public void tryCreatePostalOfficeWithAnyEmptyProperty() throws Exception {
        String postal = """
                {"index":"111111","address":"Moscow","name":""}
                """;
        mockMvc.perform(MockMvcRequestBuilders.post("/api/postal")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(postal))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @Order(1)
    public void tryCreatePostalOfficeWithInvalidIndex() throws Exception {
        String postal = """
                {"index":"111","address":"Moscow","name":"Post"}
                """;
        mockMvc.perform(MockMvcRequestBuilders.post("/api/postal")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(postal))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @Order(2)
    public void getPostalOfficeByIndex() throws Exception {
        String postalJson = mockMvc.perform(MockMvcRequestBuilders.get("/api/postal/111111")
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
        PostalOfficeDto postal = mapper.readValue(postalJson, PostalOfficeDto.class);
        assertEquals(postal.getIndex(), "111111");
        assertEquals(postal.getAddress(), "Moscow");
        assertEquals(postal.getName(), "Post");
    }

    @Test
    @Order(2)
    public void getNonExistsPostalOffice() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/postal/123333")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @Order(2)
    public void getAllPostalOffices() throws Exception {
        String array = mockMvc.perform(MockMvcRequestBuilders.get("/api/postal")
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
        List<PostalOfficeDto> list = mapper.readValue(array, new TypeReference<>() {
        });
        assertEquals(list.size(), 1);
    }

    @Test
    @Order(3)
    public void registerIncomingMailingTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/postal/111111/incoming/2"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @Order(3)
    public void registerIncomingMailingInNonExistsPostalOfficeTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/postal/123333/incoming/2"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @Order(3)
    public void registerIncomingMailingWithNonExistsMailingTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/postal/111111/incoming/10"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @Order(4)
    public void registerOutgoingMailingTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/postal/111111/outgoing/2"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @Order(4)
    public void registerOutgoingMailingInNonExistsPostalOfficeTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/postal/123333/outgoing/2"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @Order(4)
    public void registerOutgoingMailingWithNonExistsMailingTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/postal/111111/outgoing/10"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}
