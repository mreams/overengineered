package hello;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class FarewellControllerTests {
    @Autowired
    private MockMvc mockMvc;

    //tests that came with the sample project
    @Test
    public void noParamGreetingShouldReturnDefaultMessage() throws Exception {

        this.mockMvc.perform(get("/farewell")).andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.content").value("Goodbye, World!"));
    }

    @Test
    public void paramGreetingShouldReturnTailoredMessage() throws Exception {

        this.mockMvc.perform(get("/farewell").param("name", "Spring Community"))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.content").value("Goodbye, Spring Community!"));
    }

    //tests I added
    @Test
    public void paramGreetingShouldReturnCorrectLanguage() throws Exception {

        this.mockMvc.perform(get("/farewell").param("language", "es"))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.content").value("Adios, World!"));
    }

    @Test
    public void paramGreetingShouldReturnDefaultForUnknownLanguage() throws Exception {

        this.mockMvc.perform(get("/farewell").param("language", "xx"))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.content").value("Goodbye, World!"));
    }

    @Test
    public void paramGreetingShouldFailOnBadLanguage() throws Exception {

        this.mockMvc.perform(get("/farewell").param("language", "zzzzzzzzzz"))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }
}
