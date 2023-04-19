package com.monalisa.domain.user.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.monalisa.domain.book.domain.Book;
import com.monalisa.domain.book.dto.request.BookRequestDto;
import com.monalisa.domain.order.domain.Order;
import com.monalisa.domain.user.domain.User;
import com.monalisa.domain.user.dto.UserRequestDto;
import com.monalisa.domain.user.dto.response.UserResponseDto;
import com.monalisa.domain.user.service.UserService;
import com.monalisa.global.config.security.jwt.JwtTokenProvider;
import com.monalisa.global.config.security.jwt.RefreshToken;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.operation.preprocess.Preprocessors;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

    private ObjectMapper objectMapper = new ObjectMapper();

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
                        ),
                        responseHeaders(
                                headerWithName("ACCESS_TOKEN").description("응답 헤더의 Access Token"),
                                headerWithName("REFRESH_TOKEN").description("응답 헤더의 Access Token")
                        )
                ));
    }

    @DisplayName("AccessToken 갱신 테스트")
    @Test
    @WithMockUser
    void refreshAccessTokenTest() throws Exception {
        User user = User.createUser("kim", "1234", "kim", "kim@naver.com");

        given(userService.findByAccountId(any()))
                .willReturn(user);
        given(jwtTokenProvider.recreateAccessToken(any(), any()))
                .willReturn("Test NewAccessToken");

        mockMvc.perform(get("/user/refresh")
                .param("accountId", "kim")
                .header("REFRESH_TOKEN", "Test refresh Token")
        )
                .andExpect(status().isOk())
                .andExpect(header().stringValues("ACCESS_TOKEN", "Test NewAccessToken"))
                .andDo(document("/user/refresh",
                        Preprocessors.preprocessRequest(prettyPrint()),
                        Preprocessors.preprocessResponse(prettyPrint()),
                        requestHeaders(
                                headerWithName("REFRESH_TOKEN").description("요청헤더의 Refresh Token")
                        ),
                        requestParameters(
                                parameterWithName("accountId").description("사용자 아이디")
                        ),
                        responseHeaders(
                                headerWithName("ACCESS_TOKEN").description("응답 헤더의 갱신된 AccessToken")
                        ),
                        responseBody()
                ));
    }

    @DisplayName("Profile 테스트")
    @Test
    @WithMockUser
    void profileTest() throws Exception {
        UserResponseDto.Profile responseDto = UserResponseDto.Profile.of(User.createUser("kim", "1234", "kim", "kim@naver.com"));

        given(userService.profile(any()))
                .willReturn(responseDto);

        mockMvc.perform(get("/user/profile")
        )
                .andExpect(status().isOk())
                .andDo(document("/user/profile",
                        Preprocessors.preprocessRequest(prettyPrint()),
                        Preprocessors.preprocessResponse(prettyPrint()),
                        responseFields(
                                fieldWithPath("userName").type(JsonFieldType.STRING).description("아이디"),
                                fieldWithPath("name").type(JsonFieldType.STRING).description("사용자 이름"),
                                fieldWithPath("email").type(JsonFieldType.STRING).description("사용자 이메일"),
                                fieldWithPath("role").type(JsonFieldType.STRING).description("역할"),
                                fieldWithPath("registerBookList").type(JsonFieldType.ARRAY).description("판매 등록한 책 리스트")
                        )
                ));
    }

    @DisplayName("내 주문 목록 조회 테스트")
    @Test
    @WithMockUser
    void findMyOrderListTest() throws Exception {
        UserResponseDto.OrderList responseDto = UserResponseDto.OrderList.of(
                List.of(
                        Order.createOrderBySingleBook(
                                Book.registerBook(BookRequestDto.Add.builder()
                                                .name("book1")
                                                .desc("desc1")
                                                .cost(10000)
                                                .author("author1")
                                                .build()
                                        , User.createUser("seller1", "1234", "kim", "kim@naver.com")
                                )
                                , User.createUser("buyer1", "1234", "kim", "kim@naver.com")
                        )
                )
        );

        given(userService.findOrderList(any()))
                .willReturn(responseDto);


        mockMvc.perform(get("/user/orderList")
        )
                .andExpect(status().isOk())
                .andDo(document("/user/orderList",
                        Preprocessors.preprocessRequest(prettyPrint()),
                        Preprocessors.preprocessResponse(prettyPrint()),
                        responseFields(
                                fieldWithPath("orderList").type(JsonFieldType.ARRAY).description("주문 리스트"),
                                fieldWithPath("orderList[].totalPrice").type(JsonFieldType.NUMBER).description("아이디"),
                                fieldWithPath("orderList[].orderDate").type(JsonFieldType.STRING).description("날짜"),
                                fieldWithPath("orderList[].bookList[].userName").type(JsonFieldType.STRING).description("아이디"),
                                fieldWithPath("orderList[].bookList[].name").type(JsonFieldType.STRING).description("책 이름"),
                                fieldWithPath("orderList[].bookList[].desc").type(JsonFieldType.STRING).description("책 설명"),
                                fieldWithPath("orderList[].bookList[].cost").type(JsonFieldType.NUMBER).description("책 가격"),
                                fieldWithPath("orderList[].bookList[].author").type(JsonFieldType.STRING).description("저자"),
                                fieldWithPath("orderList[].bookList[].isSold").type(JsonFieldType.BOOLEAN).description("판매여부"),
                                fieldWithPath("orderList[].bookList[].like").type(JsonFieldType.NUMBER).description("좋아요 개수")
                        )
                ));
    }
}
