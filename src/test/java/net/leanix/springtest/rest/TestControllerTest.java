package net.leanix.springtest.rest;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import net.leanix.springtest.TestService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@WebMvcTest(TestController.class)
public class TestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TestService testService;

    @Test
    public void test1() throws Exception {
        when(testService.getServerName()).thenReturn("Cathie");
        mockMvc.perform(MockMvcRequestBuilders.get("/hello").param("myName", "Bob"))
            .andExpect(content().string("Hello Bob! This is Cathie!"));
    }

}
