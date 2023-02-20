# monalisa
book store(toy project)


# 프로젝트 목적 및 목표

1. 내가 만들고 싶은 프로젝트의 기능을 꾸준히 발전시켜나간다.
    - 나는 책 읽는걸 좋아한다. 그리고 책을 구매하거나 책 쇼핑하는 것을 좋아한다. 특히 요즘은 중고서적을 이용해서 책을 자주 구매하곤한다. 그래서 자연스레 중고서점 토이프로젝트를 구현하고 서비스해보면 재밌겠다 라고 생각했고, 내가 필요한 기능도 마음대로 넣어보고 싶어서 프로젝트를 진행하게 되었다.
2. 배운걸 프로젝트에 적용한다.
    - 개발관련 강의를 자주 듣는 편이고, 앞으로도 더 많은 강의를 수강할 생각이다. 
    하지만 강의를 듣는 순간엔 “지식이 내것이다”라고 착각하곤하지만, 시간이 흐른 후 막상 프로젝트에 적용하려하면 잘 안된다. 제대로 이해하지못한것이다. 그래서 배운것들을 확실히 이해하고 내것으로 만들기 위해 프로젝트를 진행하게 되었다.
3. 코드의 질을 향상시킨다.
    - 내 코드는 쓰레기라고 생각한다. 하지만 다행히도 인터넷에는 좋은 코드가 엄청 많아서, 내 쓰레기 코드를 정화시킬 수 있는 기회가 존재한다. 남에 좋은 코드를 보고 훔쳐와서 내 코드의 질을 향상시키고 싶다.
4. 피드백과 새롭게 알게 된 지식을 글로 기록한다.
    - 피드백과 지식을 글이나 무언가로 남기지 않으면 까먹는다. 심지어 나는 남겨도 까먹더라ㅠ 그래도 내손으로 직접 타이핑해서 글로 남기면 훨씬 머리에 오래가고 나중에 다시 돌아볼 수 있다. 시간은 오래걸릴지 몰라도 기록하고 남기는 것을 생활화하려고 한다.
5. 글쓰기 능력을 향상시킨다.
    - 개발자는 코드를 작성하는것뿐만 아니라 타인과의 의사소통도 매우 중요하다. 하지만 타인에게 내 의견이나 생각을 전달하는것은 매우 어렵다. 이것도 연습해야한다고 생각한다. 그래서  이 프로젝트를 진행하면서 많이, 꾸준히 기록하려 한다. 심지어 다른곳에서 습득한 지식도 최대한 내 방식대로 정제해서 기록하려고 노력할 것이다.



# 사용
- java11
- Springboot 2.7
- Spring-Data-JPA
- QueryDSL
- H2
- Junit5

# 작업 완료 현황
https://www.notion.so/pozit/edeac4eb180241a2a0e4ffb219fd0b13?v=3ad6c72d320e4844beaf91a00114bf8a&pvs=4

# 개발일지

### 22/2/20
- 주문(Order) api 구현
- QueryService 추가 및 리팩토링
- 주문(Order) 기능&예외 테스트, DTO 테스트
- [Service안에 Service 만들기](https://github.com/pable91/TIL/blob/main/Service%EC%95%88%EC%97%90%20Service%20%EB%A7%8C%EB%93%A4%EA%B8%B0.md) 

### 22/2/17
- 책 조회 기능 및 서비스 테스트 코드
- 유저 조회 기능 및 서비스 테스트 코드

### 22/2/15
- Service slice 테스트 코드 작성
- Service 예외 처리 테스트 코드 
- 책 삭제 api 구현
- [JPA 프록시 객체 json 반환 문제](https://github.com/pable91/TIL/blob/main/JPA%20%ED%94%84%EB%A1%9D%EC%8B%9C%20%EA%B0%9D%EC%B2%B4%20json%20%EB%B0%98%ED%99%98%20%EB%AC%B8%EC%A0%9C.md)

### 22/2/13
- @ControllerAdvice 로 전역 에러 핸들링
- ErrorResponse 응답 객체 구현
- 사용자 정의(BusinessException, InvalidValueException, EntityNotFoundException) Exception 구현
- ErrorCode 구현
- [예외 핸들링 및 응답 객체 리팩토링](https://github.com/pable91/TIL/blob/main/%EC%98%88%EC%99%B8%20%ED%95%B8%EB%93%A4%EB%A7%81%20%EB%B0%8F%20%EC%9D%91%EB%8B%B5%20%EA%B0%9D%EC%B2%B4%20%ED%94%BC%EB%93%9C%EB%B0%B1.md)
- [ErrorCode 리팩토링](https://github.com/pable91/TIL/blob/main/ErrorCode%20%EC%B6%94%EC%83%81%ED%99%94%20%ED%94%BC%EB%93%9C%EB%B0%B1.md)

### 22/2/11
- Spring, JPA 기본설정
- 책 판매 등록 api 구현
- 등록한 책 수정 api 구현
- 도메인, DTO 테스트 코드 구현
- [Service와 ServiceImpl를 계속 이대로 써야할까?](https://github.com/pable91/TIL/blob/main/Service%EC%99%80%20ServiceImpl.md)
- [DTO 관리](https://github.com/pable91/TIL/blob/main/%EB%84%88%EB%AC%B4%20%EB%A7%8E%EC%9D%80%20DTO%20%ED%81%B4%EB%9E%98%EC%8A%A4%20%EA%B4%80%EB%A6%AC.md)
