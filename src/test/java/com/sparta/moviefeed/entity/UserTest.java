package com.sparta.moviefeed.entity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.moviefeed.dto.requestdto.MypageRequestDto;
import com.sparta.moviefeed.dto.requestdto.UserSignupRequestDto;
import com.sparta.moviefeed.enumeration.UserStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    private ObjectMapper objectMapper;
    private User user;

    @BeforeEach
    public void init() throws JsonProcessingException {

        objectMapper = new ObjectMapper();
        String jsonString = "{"
                + "\"userId\": \"ryukhunhan4396\","
                + "\"password\": \"ryurbsgks5114!\","
                + "\"intro\": \"한 줄 소개를 입력해주세요.\","
                + "\"userName\": \"이름을 입력해주세요.\","
                + "\"email\": \"ryukhunhan5114@naver.com\""
                + "}";
        UserSignupRequestDto requestDto = objectMapper.readValue(jsonString, UserSignupRequestDto.class);
        user = new User(requestDto, UserStatus.NORMAL);

    }

    @Test
    @DisplayName("user entity constructor")
    public void testConstructor() {
        assertEquals("ryukhunhan4396", user.getUserId());
        assertEquals("ryurbsgks5114!", user.getPassword());
        assertEquals("이름을 입력해주세요.", user.getUserName());
        assertEquals("ryukhunhan5114@naver.com", user.getEmail());
        assertEquals("한 줄 소개를 입력해주세요.", user.getIntro());
        assertEquals(UserStatus.NORMAL, user.getUserStatus());
    }

    @Test
    @DisplayName("user entity updateMyPage method")
    public void testUpdateMyPage() throws JsonProcessingException {

        String jsonString = "{"
                + "\"userName\": \"ryurbsgks\","
                + "\"intro\": \"한 줄 소개 입력\""
                + "}";

        MypageRequestDto requestDto = objectMapper.readValue(jsonString, MypageRequestDto.class);

        user.updateMypage(requestDto);

        assertEquals("ryurbsgks", user.getUserName());
        assertEquals("한 줄 소개 입력", user.getIntro());

    }

    @Test
    @DisplayName("user entity updatePassword method")
    public void testUpdatePassword() {

        user.updatePassword("ryurbsgks4396!");

        assertEquals("ryurbsgks4396!", user.getPassword());

    }

    @Test
    @DisplayName("user entity updateRefreshToken method")
    public void testUpdateRefreshToken() {

        user.updateRefreshToken("updateRefreshToken");

        assertEquals("updateRefreshToken", user.getRefreshToken());

    }

    @Test
    @DisplayName("user entity updateUserStatus method")
    public void testUpdateUserStatus() {

        LocalDateTime now = LocalDateTime.now();

        user.updateUserStatus(UserStatus.LEAVE, now);

        assertEquals(UserStatus.LEAVE, user.getUserStatus());
        assertEquals(now, user.getStatusAt());

    }

    @Test
    @DisplayName("user entity encryptionPassword method")
    public void testEncryptionPassword() {

        user.encryptionPassword("encryptionPassword");

        assertEquals("encryptionPassword", user.getPassword());

    }

}