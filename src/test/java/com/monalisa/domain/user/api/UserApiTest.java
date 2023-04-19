package com.monalisa.domain.user.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.monalisa.domain.user.domain.User;
import com.monalisa.domain.user.dto.UserRequestDto;
import com.monalisa.domain.user.dto.response.UserResponseDto;
import com.monalisa.domain.user.service.UserService;
import com.monalisa.global.config.security.jwt.JwtTokenProvider;
import com.monalisa.global.config.security.jwt.RefreshToken;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.operation.preprocess.Preprocessors;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureRestDocs
@WebMvcTest(UserApi.class)
class UserApiTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private JwtTokenProvider jwtTokenProvider;

    ObjectMapper objectMapper = new ObjectMapper();

    @DisplayName("회원가입 테스트")
    @Test
    @WithMockUser
    void signupTest() throws Exception {
        // give
        UserRequestDto.SignUp requestDto = UserRequestDto.SignUp.builder()
                .accountId("kim")
                .pw("1234")
                .name("kim")
                .email("kim@naver.com")
                .build();

        UserResponseDto.SignUp responseDto = UserResponseDto.SignUp.of(User.createUser("kim", "1234", "kim", "kim@naver.com"));

        given(userService.signup(any(UserRequestDto.SignUp.class)))
                .willReturn(responseDto);

        // when, then
        mockMvc.perform(post("/user/signup").with(csrf())
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andDo(document("/user/signup",
                        Preprocessors.preprocessRequest(prettyPrint()),
                        Preprocessors.preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("accountId").description("사용자 아이디").type(JsonFieldType.STRING),
                                fieldWithPath("pw").description("비밀번호").type(JsonFieldType.STRING),
                                fieldWithPath("name").description("사용자 이름").type(JsonFieldType.STRING),
                                fieldWithPath("email").description("이메일").type(JsonFieldType.STRING)
                        ),
                        responseFields(
                                fieldWithPath("accountId").description("사용자 아이디").type(JsonFieldType.STRING),
                                fieldWithPath("name").description("사용자 이름").type(JsonFieldType.STRING),
                                fieldWithPath("email").description("이메일").type(JsonFieldType.STRING)
                        )
                ));
    }


    @DisplayName("로그인 테스트")
    @Test
    @WithMockUser
    void loginTest() throws Exception {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        // give
        UserRequestDto.Login requestDto = UserRequestDto.Login.builder()
                .accountId("kim")
                .pw("1234")
                .build();

        String pw = passwordEncoder.encode("1234");
        User user = User.createUser("kim", pw, "kim", "kim@naver.com");

        given(userService.login(any(UserRequestDto.Login.class)))
                .willReturn(user);
        given(jwtTokenProvider.createAccessToken(any(User.class)))
                .willReturn("Test AccessToken");
        given(jwtTokenProvider.createRefreshToken(any(User.class)))
                .willReturn(new RefreshToken(UUID.randomUUID().toString(), "kim"));

        // when, then
        mockMvc.perform(post("/user/login").with(csrf())
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto))
                .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isOk())
            .andExpect(header().stringValues("ACCESS_TOKEN", "Test AccessToken"))
            .andExpect(header().exists("REFRESH_TOKEN"))
            .andDo(document("/user/login",
                    Preprocessors.preprocessRequest(prettyPrint()),
                    Preprocessors.preprocessResponse(prettyPrint()),
                    requestFields(
                            fieldWithPath("accountId").description("사용자 아이디").type(JsonFieldType.STRING),
                            fieldWithPath("pw").description("비밀번호").type(JsonFieldType.STRING)
                    ),
                    responseFields(
                            fieldWithPath("userName").description("사용자 이름").type(JsonFieldType.STRING),
                            fieldWithPath("registerBookList").description("등록한 책 리스트").type(JsonFieldType.ARRAY),
                            fieldWithPath("accessToken").description("엑세스 토큰").type(JsonFieldType.STRING),
                            fieldWithPath("refreshToken").description("리프레시 토큰").type(JsonFieldType.STRING)
                    )
        ));
    }
}
