package com.sparta.moviefeed.dto.requestdto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class UserSignupRequestDtoTest {

    private ObjectMapper objectMapper;
    private Validator validator;

    @BeforeEach
    public void init() {

        objectMapper = new ObjectMapper();
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();

    }

    @Test
    @DisplayName("user signupRequestDto clear")
    public void testCreateDto() throws JsonProcessingException {

        String jsonString = "{"
                + "\"userId\": \"ryukhunhan4396\","
                + "\"password\": \"ryurbsgks5114!\","
                + "\"intro\": \"한 줄 소개를 입력해주세요.\","
                + "\"userName\": \"이름을 입력해주세요.\","
                + "\"email\": \"ryukhunhan5114@naver.com\""
                + "}";
        UserSignupRequestDto requestDto = objectMapper.readValue(jsonString, UserSignupRequestDto.class);

        Set<ConstraintViolation<UserSignupRequestDto>> violations = validator.validate(requestDto);

        assertTrue(violations.isEmpty());

    }

    @Test
    @DisplayName("user signupRequestDto test userId pattern")
    public void testUserIdPattern() throws JsonProcessingException {

        String jsonString = "{"
                + "\"userId\": \"!@#!@#!@@#!@#!@\","
                + "\"password\": \"ryurbsgks5114!\","
                + "\"intro\": \"한 줄 소개를 입력해주세요.\","
                + "\"userName\": \"이름을 입력해주세요.\","
                + "\"email\": \"ryukhunhan5114@naver.com\""
                + "}";
        UserSignupRequestDto dto = objectMapper.readValue(jsonString, UserSignupRequestDto.class);

        Set<ConstraintViolation<UserSignupRequestDto>> violations = validator.validate(dto);

        assertEquals(1, violations.size());
        ConstraintViolation<UserSignupRequestDto> violation = violations.iterator().next();
        assertEquals("아이디는 영문 대소문자, 숫자만 가능합니다.", violation.getMessage());
        assertEquals("userId", violation.getPropertyPath().toString());

    }

    @Test
    @DisplayName("user signupRequestDto test password pattern")
    public void testPasswordPattern() throws JsonProcessingException {

        String jsonString = "{"
                + "\"userId\": \"ryukhunhan4396\","
                + "\"password\": \"ASAFASDFASFSADF\","
                + "\"intro\": \"한 줄 소개를 입력해주세요.\","
                + "\"userName\": \"이름을 입력해주세요.\","
                + "\"email\": \"ryukhunhan5114@naver.com\""
                + "}";
        UserSignupRequestDto dto = objectMapper.readValue(jsonString, UserSignupRequestDto.class);

        Set<ConstraintViolation<UserSignupRequestDto>> violations = validator.validate(dto);

        assertEquals(1, violations.size());
        ConstraintViolation<UserSignupRequestDto> violation = violations.iterator().next();
        assertEquals("비밀번호는 영문 대소문자 + 숫자 + 특수문자를 최소 1글자씩 포함합니다.", violation.getMessage());
        assertEquals("password", violation.getPropertyPath().toString());

    }

    @Test
    @DisplayName("user signupRequestDto test userName not blank")
    public void testUserNameNotBlank() throws JsonProcessingException {

        String jsonString = "{"
                + "\"userId\": \"ryukhunhan4396\","
                + "\"password\": \"ryurbsgks5114!\","
                + "\"intro\": \"한 줄 소개를 입력해주세요.\","
                + "\"userName\": \"\","
                + "\"email\": \"ryukhunhan5114@naver.com\""
                + "}";
        UserSignupRequestDto dto = objectMapper.readValue(jsonString, UserSignupRequestDto.class);

        Set<ConstraintViolation<UserSignupRequestDto>> violations = validator.validate(dto);

        assertEquals(1, violations.size());
        ConstraintViolation<UserSignupRequestDto> violation = violations.iterator().next();
        assertEquals("이름을 입력해주세요.", violation.getMessage());
        assertEquals("userName", violation.getPropertyPath().toString());

    }

    @Test
    @DisplayName("user signupRequestDto test email pattern")
    public void testEmailPattern() throws JsonProcessingException {

        String jsonString = "{"
                + "\"userId\": \"ryukhunhan4396\","
                + "\"password\": \"ryurbsgks5114!\","
                + "\"intro\": \"한 줄 소개를 입력해주세요.\","
                + "\"userName\": \"이름을 입력해주세요.\","
                + "\"email\": \"ryukhunhan5114naver.com\""
                + "}";
        UserSignupRequestDto dto = objectMapper.readValue(jsonString, UserSignupRequestDto.class);

        Set<ConstraintViolation<UserSignupRequestDto>> violations = validator.validate(dto);

        assertEquals(1, violations.size());
        ConstraintViolation<UserSignupRequestDto> violation = violations.iterator().next();
        assertEquals("이메일 형식을 확인해 주세요.", violation.getMessage());
        assertEquals("email", violation.getPropertyPath().toString());

    }

    @Test
    @DisplayName("user signupRequestDto test intro not blank")
    public void testIntroNotBlank() throws JsonProcessingException {

        String jsonString = "{"
                + "\"userId\": \"ryukhunhan4396\","
                + "\"password\": \"ryurbsgks5114!\","
                + "\"intro\": \" \","
                + "\"userName\": \"이름을 입력해주세요.\","
                + "\"email\": \"ryukhunhan5114@naver.com\""
                + "}";
        UserSignupRequestDto dto = objectMapper.readValue(jsonString, UserSignupRequestDto.class);

        Set<ConstraintViolation<UserSignupRequestDto>> violations = validator.validate(dto);

        assertEquals(1, violations.size());
        ConstraintViolation<UserSignupRequestDto> violation = violations.iterator().next();
        assertEquals("한 줄 소개를 입력해주세요.", violation.getMessage());
        assertEquals("intro", violation.getPropertyPath().toString());

    }

}