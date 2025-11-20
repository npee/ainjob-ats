# 📌 AinJob ATS Backend
> 채용 지원자 관리(ATS: Applicant Tracking System) 백엔드 애플리케이션
지원자 조회, 필터 검색(학력/전공/경력/보유 기술), 진행 상태 변경을 제공합니다.
QueryDSL 기반 고급 검색, ID 기반 페이징, OpenAPI 3.0 문서 자동화가 적용되어 있습니다.

## 🚀 Tech Stack

| 영역        | 기술                                 |
|-----------|------------------------------------|
| Language  | Java 17                            |
| Framework | Spring Boot 3.5.7                  |
| DB        | MySQL                              |
| ORM       | JPA(Hibernate 6) + QueryDSL 5      |
| API Docs  | SpringDoc OpenAPI 3.0 (Swagger UI) |
| Build Tool| Gradle                             |
| 기타       | Lombok                             |

## 📁 프로젝트 구조 (Domain-Driven Package)
```text
src/main/java/com.ainjob
 ├─ global                  # 공통 설정/예외
 │   ├─ config              # QueryDSL, Swagger 설정 등
 │   └─ error               # 전역 예외 처리기
 │
 └─ domain
     └─ applicant           # 지원자 도메인
         ├─ entity          # JPA 엔티티
         ├─ dto
         │   ├─ request     # 요청 DTO
         │   ├─ response    # 응답 DTO
         │   └─ projection  # QueryDSL Projection DTO
         ├─ enum            # 프로세스 상태, 학력 등
         ├─ repository      # JPA + QueryDSL Repository
         ├─ service         # 비즈니스 로직
         └─ controller      # REST API

```

## 🧠 주요 기능
### 🔎 지원자 필터 조회
- 학력(고졸/준학사/학사(대졸)/석사/박사)
- 전공 계열
- 보유 기술 복수 검색
- 경력 연차 범위
- 진행 상태(지원/서류합격/면접대기/합격)
> **QueryDSL GroupBy + ID Pagination + Sub Query 조합**  
> 필터 스킬 검색 시에도 지원자의 모든 보유 기술을 반환합니다.

### 🔄 지원자 상태 변경 API
- 지원 → 서류합격 → 면접대기 → 합격 흐름
- Enum 기반 예외 처리 + 응답 DTO 출력

## 📄 Swagger/OpenAPI 문서
### 접속 URL
| URL                     | 설명              |
|------------------------|-----------------|
| /swagger-ui/index.html  | UI 화면          |
| /v3/api-docs           | OpenAPI JSON    |

## 🛠 실행 방법
### 1) 별도로 관리되는 설정 파일(application.yml) 추가
> **주의: 해당 프로젝트는 외부에서 접근할 수 없는 개인 서버의 DB를 사용하고 있어, 로컬 환경에서 실행하려면 로컬 DB를 구축해야 합니다.**

```yaml
spring:
  application:
    name: ainjob-codingtest
  datasource:
    url: ${DB_URL}
    username: ${DB_USER}
    password: ${DB_PASSWORD}
    driver-class-name: com.mysql.cj.jdbc.Driver
  jpa:
    hibernate:
      ddl-auto: none
    show-sql: true
    properties:
      hibernate:
        format_sql: true
        dialect: org.hibernate.dialect.MySQL8Dialect

springdoc:
  api-docs:
    version: OPENAPI_3_0
```
### 2) Gradle Build & Run
```bash
./gradlew clean build
./gradlew bootRun
```

## 🧪 Test Strategy (요약)
| 대상        | 방식                                   |
|-----------|--------------------------------------|
| Service   | Repository Mock + Projection Factory |
| Projection| 실제 객체 생성 (Factory Pattern)           |
| Repository| QueryDSL Integration Test (미구현)      |

## 📌 앞으로의 개선 방향
- JWT 인증 + 관리자 권한 추가
- ElasticSearch 기반 검색 고도화
- 이력서-공고 관계 중심의 관리