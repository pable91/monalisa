# monalisa
서점 백엔드 api 프로젝트 

# 프로젝트 목적 및 목표
1. 내가 만들고 싶은 프로젝트의 기능을 꾸준히 발전시켜나간다.
    - 책읽는걸 좋아해서 책을 자주 구매하곤한다. 그래서 자연스레 서점 토이프로젝트를 구현하고 내가 필요한 기능도 마음대로 넣어보고 싶어서 프로젝트를 진행하게 되었다.
2. 배운걸 프로젝트에 적용한다.
    - 개발관련 강의를 자주 듣는 편인데, 머리에 생각보다 잘 안남는다.그래서 배운것들을 확실히 이해하고 내것으로 만들기 위해 프로젝트를 진행하게 되었다.
3. 코드의 질을 향상시킨다.
    - 나보다 더 좋은 코드를 보며 내 코드에 적용시키고 싶다. 
4. 피드백과 새롭게 알게 된 지식을 글로 기록한다.
    - 내손으로 직접 타이핑해서 글로 남기면 훨씬 머리에 오래남고 미래에 다시 돌아볼 수 있다. 

# 사용기술
- java11
- Springboot 2.7
- Spring-Data-JPA
- Redis
- QueryDSL
- H2(AWS에서는 MariaDB)
- Junit5
- AWS ec2

# 구조
![architecture](https://user-images.githubusercontent.com/22884224/220965624-b52c7655-febb-42ab-b8cd-c38567eb726f.png)

# 작업 진행 상황(v1)
- [x] 판매 책 CRUD 구현
- [x] 주문 CRUD 구현 (복수 주문 미구현)
- [x] 유저 회원가입, 로그인 구현(jwt + Refresh + Redis)
- [x] AWS ec2 코드 배포
- [x] Github Action을 이용한 CI / CD 구현
- 프로젝트 이슈 목록 => https://github.com/users/pable91/projects/1/views/1

# 개발일지
- [AWS 서비스 배포하기(EC2, RDS생성)](https://github.com/pable91/TIL/blob/main/AWS%20%EC%84%9C%EB%B9%84%EC%8A%A4%20%EB%B0%B0%ED%8F%AC%ED%95%98%EA%B8%B0(EC2%2C%20RDS%EC%83%9D%EC%84%B1).md)
- [AWS 서비스 배포하기(Github Action)](https://github.com/pable91/TIL/blob/main/AWS%20%EC%84%9C%EB%B9%84%EC%8A%A4%20%EB%B0%B0%ED%8F%AC%ED%95%98%EA%B8%B0(Github%20Action).md)
- [Service안에 Service 만들기](https://github.com/pable91/TIL/blob/main/Service%EC%95%88%EC%97%90%20Service%20%EB%A7%8C%EB%93%A4%EA%B8%B0.md) 
- [JPA 프록시 객체 json 반환 문제](https://github.com/pable91/TIL/blob/main/JPA%20%ED%94%84%EB%A1%9D%EC%8B%9C%20%EA%B0%9D%EC%B2%B4%20json%20%EB%B0%98%ED%99%98%20%EB%AC%B8%EC%A0%9C.md)
- [예외 핸들링 및 응답 객체 리팩토링](https://github.com/pable91/TIL/blob/main/%EC%98%88%EC%99%B8%20%ED%95%B8%EB%93%A4%EB%A7%81%20%EB%B0%8F%20%EC%9D%91%EB%8B%B5%20%EA%B0%9D%EC%B2%B4%20%ED%94%BC%EB%93%9C%EB%B0%B1.md)
- [ErrorCode 리팩토링](https://github.com/pable91/TIL/blob/main/ErrorCode%20%EC%B6%94%EC%83%81%ED%99%94%20%ED%94%BC%EB%93%9C%EB%B0%B1.md)
- [Service와 ServiceImpl를 계속 이대로 써야할까?](https://github.com/pable91/TIL/blob/main/Service%EC%99%80%20ServiceImpl.md)
- [DTO 관리](https://github.com/pable91/TIL/blob/main/%EB%84%88%EB%AC%B4%20%EB%A7%8E%EC%9D%80%20DTO%20%ED%81%B4%EB%9E%98%EC%8A%A4%20%EA%B4%80%EB%A6%AC.md)
