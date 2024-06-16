package com.sparta.moviefeed.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.moviefeed.MockSpringSecurityFilter;
import com.sparta.moviefeed.config.WebSecurityConfig;
import com.sparta.moviefeed.dto.requestdto.UserSignupRequestDto;
import com.sparta.moviefeed.entity.User;
import com.sparta.moviefeed.enumeration.UserStatus;
import com.sparta.moviefeed.security.UserDetailsImpl;
import com.sparta.moviefeed.service.UserService;
import com.sparta.moviefeed.util.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.security.Principal;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.anonymous;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = UserController.class,
        excludeFilters = {@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfig.class)})
class UserControllerTest {

    private MockMvc mvc;
    private Principal mockPrincipal;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    UserService userService;

    @MockBean
    PasswordEncoder passwordEncoder;

    @MockBean
    JwtUtil jwtUtil;

    @BeforeEach
    public void setup() {

        mvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(springSecurity(new MockSpringSecurityFilter()))
                .build();

    }

    private void mockUserSetup() throws JsonProcessingException {

        String jsonString = "{"
                + "\"userId\": \"ryukhunhan4396\","
                + "\"password\": \"ryurbsgks5114!\","
                + "\"intro\": \"한 줄 소개를 입력해주세요.\","
                + "\"userName\": \"이름을 입력해주세요.\","
                + "\"email\": \"ryukhunhan5114@naver.com\""
                + "}";
        UserSignupRequestDto requestDto = objectMapper.readValue(jsonString, UserSignupRequestDto.class);
        User testUser = new User(requestDto, UserStatus.NORMAL);
        UserDetailsImpl testUserDetails = new UserDetailsImpl(testUser);
        mockPrincipal = new UsernamePasswordAuthenticationToken(testUserDetails, "", testUserDetails.getAuthorities());

    }

    @Test
    @DisplayName("signup")
    public void signup() throws Exception {

        String jsonString = "{"
                + "\"userId\": \"ryukhunhan4396\","
                + "\"password\": \"ryurbsgks5114!\","
                + "\"intro\": \"한 줄 소개를 입력해주세요.\","
                + "\"userName\": \"이름을 입력해주세요.\","
                + "\"email\": \"ryukhunhan5114@naver.com\""
                + "}";

        mvc.perform(post("/api/users/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonString)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andDo(print());

    }

    @Test
    @DisplayName("logout")
    public void logout() throws Exception {

        this.mockUserSetup();

        mvc.perform(post("/api/users/logout")
                        .accept(MediaType.APPLICATION_JSON)
                        .principal(mockPrincipal))
                .andExpect(status().isOk())
                .andDo(print());

    }

    @Test
    @DisplayName("withdrawal")
    public void withdrawal() throws Exception {

        this.mockUserSetup();

        String jsonString = "{"
                + "\"password\": \"ryurbsgks5114!\""
                + "}";

        mvc.perform(put("/api/users/withdrawal")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonString)
                        .accept(MediaType.APPLICATION_JSON)
                        .principal(mockPrincipal))
                .andExpect(status().isOk())
                .andDo(print());

    }

    @Test
    @DisplayName("refresh")
    public void refresh() throws Exception {

        mvc.perform(post("/api/users/refresh")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());

    }

}