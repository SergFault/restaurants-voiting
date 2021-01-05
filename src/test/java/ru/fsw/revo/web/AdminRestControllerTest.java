package ru.fsw.revo.web;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import ru.fsw.revo.domain.to.RestaurantTo;
import ru.fsw.revo.service.RestaurantService;
import ru.fsw.revo.service.VoteService;
import ru.fsw.revo.web.json.JsonUtil;

import javax.annotation.PostConstruct;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.fsw.revo.RestaurantsTestData.*;
import static ru.fsw.revo.UserTestData.*;

@SpringJUnitWebConfig(locations = {
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-mvc.xml",
        "classpath:spring/spring-db.xml"
})
@Transactional
@ActiveProfiles("prod")
public class AdminRestControllerTest {

    private static final String REST_URL = AdminRestController.REST_URL + "/";
    private static final CharacterEncodingFilter CHARACTER_ENCODING_FILTER = new CharacterEncodingFilter();

    @Autowired
    public Environment env;

    @Autowired
    protected VoteService service;
    @Autowired
    protected RestaurantService resService;

    static {
        CHARACTER_ENCODING_FILTER.setEncoding("UTF-8");
        CHARACTER_ENCODING_FILTER.setForceEncoding(true);
    }

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @PostConstruct
    private void postConstruct() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .addFilter(CHARACTER_ENCODING_FILTER)
                .apply(springSecurity())
                .build();
    }

    protected ResultActions perform(MockHttpServletRequestBuilder builder) throws Exception {
        return mockMvc.perform(builder);
    }

    @Test
    @Sql(scripts = "classpath:db/populateDB.sql")
    void createNewRestaurant() throws Exception {
        perform(MockMvcRequestBuilders.post(REST_URL)
                .with(SecurityMockMvcRequestPostProcessors.httpBasic(admin.getEmail() , admin.getPassword()))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(getNew())))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
        RestaurantTo restaurantWithRating = resService.getRestaurantWithRating(createdTo.getId());
        Assertions.assertThat(restaurantWithRating)
                .usingRecursiveComparison()
                .isEqualTo(createdTo);

    }

    @Test
    void updateRestaurant() throws Exception {
        perform(MockMvcRequestBuilders.put(REST_URL + REST1_ID)
                .with(SecurityMockMvcRequestPostProcessors.httpBasic(admin.getEmail() , admin.getPassword()))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(rest1_updated)))
                .andExpect(status().isNoContent());
        RestaurantTo restaurantWithRating = resService.getRestaurantWithRating(rest1.id());
        Assertions.assertThat(restaurantWithRating)
                .usingRecursiveComparison()
                .isEqualTo(rest1_updatedTo);
    }

    @Test
    @Sql(scripts = "classpath:db/populateDB.sql")
    void updateMenu() throws Exception {
        perform(MockMvcRequestBuilders.put(REST_URL + REST1_ID + "/menu")
                .with(SecurityMockMvcRequestPostProcessors.httpBasic(admin.getEmail() , admin.getPassword()))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(rest1_updated_menu)))
                .andExpect(status().isNoContent());
        rest1.setMenu(rest1_updated_menu);
        RestaurantTo restaurantWithRating = resService.getRestaurantWithRating(rest1.id());
        Assertions.assertThat(restaurantWithRating)
                .usingRecursiveComparison()
                .isEqualTo(rest1To_with_updated_menu);
    }


}