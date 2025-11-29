package com.syedhisham41.cron_validator.Controller;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import com.syedhisham41.cron_validator.Service.CronService;

@WebMvcTest(CronController.class)
public class CronControllerTests {

    @MockitoBean(name = "QUARTZ")
    private CronService quartzService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void validateCron_ShouldReturnTrue() throws Exception {

        String json = """
        {
          "cronExpr": "0 0/5 * * * ?",
          "cronType": "QUARTZ"
        }
        """;

        Mockito.when(quartzService.validate("0 0/5 * * * ?"))
               .thenReturn(true);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/validate")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(json)
        )
        .andExpect(status().isOk())
        .andExpect(content().string("true"));
    }

    @Test
    public void validateCron_ShouldReturnFalse() throws Exception {

        String json = """
        {
          "cronExpr": "0 0/5 * * * *",
          "cronType": "QUARTZ"
        }
        """;

        Mockito.when(quartzService.validate("0 0/5 * * * *"))
               .thenReturn(false);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/validate")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(json)
        )
        .andExpect(status().isOk())
        .andExpect(content().string("false"));
    }

    @Test
    public void cronToText_ShouldReturnText() throws Exception {

        String json = """
        {
          "cronExpr": "0 0 12 * * ?",
          "cronType": "QUARTZ"
        }
        """;

        Mockito.when(quartzService.cronToText("0 0 12 * * ?"))
               .thenReturn("At 12:00 PM every day");

        mockMvc.perform(post("/api/crontext")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andExpect(content().string("At 12:00 PM every day"));
    }

    @Test
    public void cronToTextTest_ShouldReturnParsedText() throws Exception {

        String json = """
        {
          "cronExpr": "0 15 10 ? * MON-FRI",
          "cronType": "QUARTZ"
        }
        """;

        Mockito.when(quartzService.parseCronToText("0 15 10 ? * MON-FRI"))
               .thenReturn("At 10:15 AM Monday through Friday");

        mockMvc.perform(post("/api/crontexttest")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andExpect(content().string("At 10:15 AM Monday through Friday"));
    }
}
