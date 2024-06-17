package com.sparta.moviefeed.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.moviefeed.dto.requestdto.UserSignupRequestDto;
import com.sparta.moviefeed.entity.User;
import com.sparta.moviefeed.repository.UserRepository;
import com.sparta.moviefeed.util.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private JwtUtil jwtUtil;

    private PasswordEncoder passwordEncoder;
    private UserService userService;
    private ObjectMapper objectMapper;

    @BeforeEach
    public void init() {
        objectMapper = new ObjectMapper();
        passwordEncoder = new BCryptPasswordEncoder();
        userService = new UserService(userRepository, passwordEncoder, jwtUtil);
    }

    @Test
    @DisplayName("password encoder")
    public void testPasswordEncoder() throws JsonProcessingException {

        String jsonString = "{"
                + "\"userId\": \"ryukhunhan4396\","
                + "\"password\": \"ryurbsgks5114!\","
                + "\"intro\": \"한 줄 소개를 입력해주세요.\","
                + "\"userName\": \"이름을 입력해주세요.\","
                + "\"email\": \"ryukhunhan5114@naver.com\""
                + "}";
        UserSignupRequestDto requestDto = objectMapper.readValue(jsonString, UserSignupRequestDto.class);

        userService.signup(requestDto);

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userCaptor.capture());
        User savedUser = userCaptor.getValue();

        assertTrue(passwordEncoder.matches("ryurbsgks5114!", savedUser.getPassword()));

    }

}