package com.sparta.moviefeed.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.moviefeed.dto.requestdto.UserSignupRequestDto;
import com.sparta.moviefeed.entity.User;
import com.sparta.moviefeed.repository.UserRepository;
import com.sparta.moviefeed.security.UserDetailsImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class UserServiceIntegrationTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    private ObjectMapper objectMapper;

    @Test
    @DisplayName("signup")
    public void testSignup() throws JsonProcessingException {

        objectMapper = new ObjectMapper();
        String jsonString = "{"
                + "\"userId\": \"ryukhunhan9877\","
                + "\"password\": \"ryurbsgks5114!\","
                + "\"intro\": \"한 줄 소개를 입력해주세요.\","
                + "\"userName\": \"이름을 입력해주세요.\","
                + "\"email\": \"ryukhunhan5114@naver.com\""
                + "}";
        UserSignupRequestDto requestDto = objectMapper.readValue(jsonString, UserSignupRequestDto.class);

        userService.signup(requestDto);

        Optional<User> user = userRepository.findByUserId("ryukhunhan9877");

        assertTrue(user.isPresent());
        assertEquals("ryukhunhan9877", user.get().getUserId());

    }

    @Test
    @DisplayName("logout")
    public void testLogout() {

        Optional<User> userOptional = userRepository.findByUserId("ryukhunhan5114");

        if (userOptional.isPresent()) {

            User user = userOptional.get();

            user.updateRefreshToken("testToken");
            userRepository.save(user);

            UserDetailsImpl userDetails = new UserDetailsImpl(user);
            SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(userDetails, null));

            userService.logout();

            Optional<User> updateUserOptional = userRepository.findByUserId("ryukhunhan5114");

            if (updateUserOptional.isPresent()) {

                User updateUser = updateUserOptional.get();
                assertNull(updateUser.getRefreshToken());

            }

        }

    }

}
