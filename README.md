# 💰 땡그랑 (초등 경제 교육 서비스)

[<img src="/docs/image/땡그랑 소개.png" width = "800">](https://youtu.be/ZU994x_80P4)

**👆 위 이미지 클릭하면 `영상 포트폴리오` 감상 가능합니다 👆**

<br/>

<div id="1"></div>

## ✨ 개요

### SSAFY 12기 공통 프로젝트

**서비스명** : 땡그랑 ( 똑똑한 경제 학습 )

**한줄 설명** : 디지털 교과서 전환에 맞춘, 테블릿 활용 초등 경제 학습 서비스

**팀원** : FE 3명 / BE 3명 (6명)

**도메인** : 모바일

**프로젝트 기간** : 2025.01.13 ~ 2025.02.21 (6주)

<br/>

## ✨ 목차

1. [**기획 배경**](#-기획-배경)
1. [**주요 기능**](#-주요-기능)
1. [**주요 기능별 화면 (교사 / 학생)**](#-땡그랑-서비스-화면-교사)
1. [**기술적 특징**](#-기술적-특징)
1. [**기술 스택**](#-기술-스택)
1. [**프로젝트 산출물**](#-프로젝트-산출물)
1. [**개발 멤버 및 역할 분담**](#-개발-멤버-및-역할분담)
1. [**메뉴얼 및 상세문서**](#-메뉴얼-및-상세-문서)

<br/>

## ✨ 기획 배경

초등학교 교실을 작은 사회로 운영하는 **교실 경제 활동**이 주목받고 있지만, 현장에는 여전히 해결되지 않은 문제들이 있었습니다.

- 교실 경제 활동 수업 경험 교사 : 수기 장부 관리·수동 정산의 번거로움
- 교실 경제 활동 수업 미경험 교사 : 정보 부족과 준비 부담
- 기존 유사 앱(퍼플 등) : 서버 불안정·기능 부재로 현장 활용 한계

정부의 **학생 1인 1태블릿 보급 정책**(2023년~)에 맞춰, 이 문제를 해결하기 위한 태블릿 기반 경제 교육 서비스 **땡그랑**을 기획했습니다.

<br/>

**📎 참고 자료**

- 📺 [유 퀴즈 온 더 블럭 — 옥효진 선생님의 교실 경제 교육](https://www.youtube.com/watch?v=8hMvV-DCEGs)
- 📊 [초등 경제 시뮬레이션 활동 지원 앱 교사 설문조사](/screenshots/0114설문.gif)
- 📄 [전라북도교육청, 「학생 교육용 스마트기기 보급 계획 안내](/docs/references/학생용스마트기기보급사업안내.pdf)
- 📘 [김미경, 『초등학교 경제 개념 학습을 위한 시뮬레이션 게임의 개발 및 적용』, 2003](/docs/references/초등학교_경제_개념_학습을_위한_시뮬레이션_게임의_개발_및_적용___'시장과_가격',_'소득과_소비'를_중심으로.pdf)

<br/>

<div id="2"></div>

## ✨ 주요 기능

- 서비스 설명 : 교사가 교실 경제를 운영하고, 학생이 급여·저축·투자·소비를 직접 체험하는 **태블릿 기반 초등 경제 교육 플랫폼**

- 주요 기능 :

    **👩‍🏫 교사**

    | 기능                       | 설명                                                                      |
    | -------------------------- | ------------------------------------------------------------------------- |
    | 학생·급여 관리             | 학생 계정 생성, 급여·인센티브 지급, 반 전체 경제 현황 대시보드            |
    | 주식·ETF 시장 운영         | 종목 등록, 시장 개장/폐장 제어                                            |
    | 은행 상품·아이템·세금 설정 | 적금 상품 등록, 아이템 등록, 세금 부과 및 사용처 설정                     |
    | **AI 경제 뉴스 생성·발행** | GPT-3.5-turbo 기반 주식 공시 뉴스 자동 생성 → FCM 푸시 알림으로 학생 전송 |

    **👧 학생**

    | 기능               | 설명                                                                             |
    | ------------------ | -------------------------------------------------------------------------------- |
    | 계좌·자산 관리     | 수입/지출 내역 확인, 개인 자산 현황 조회                                         |
    | 주식·ETF 투자      | 개장 시간 내 매수·매도, 시세 변동 확인                                           |
    | 은행 서비스        | 적금 상품 가입, 저축 목표 설정                                                   |
    | 기타 경제 활동     | 세금 납부, 아이템 구매·사용, 학급 투표 참여                                      |
    | **FCM 푸시 알림**  | 뉴스 발행·적금 만기·주간 리포트 이벤트 발생 시 실시간 푸시 알림 수신             |
    | **AI 주간 리포트** | K-Means + Random Forest로 소비 유형 분류 (정확도 98.89%), GPT 개인화 피드백 제공 |

<br/>

<div id="3"></div>

## ✨ 주요 기능별 화면

**[👉 전체 화면 보러가기](https://github.com/breadbirds/Ttaenggurang/wiki/%EC%A0%84%EC%B2%B4-%EC%84%9C%EB%B9%84%EC%8A%A4-%ED%99%94%EB%A9%B4)**

### 👧 땡그랑 서비스 화면 (학생)

**[👉 전체 학생 화면 보러가기](https://github.com/breadbirds/Ttaenggurang/wiki/%EC%A0%84%EC%B2%B4-%EC%84%9C%EB%B9%84%EC%8A%A4-%ED%99%94%EB%A9%B4#%ED%95%99%EC%83%9D)**

### 👩‍🏫 땡그랑 서비스 화면 (교사)

**[👉 전체 교사 화면 보러가기](https://github.com/breadbirds/Ttaenggurang/wiki/%EC%A0%84%EC%B2%B4-%EC%84%9C%EB%B9%84%EC%8A%A4-%ED%99%94%EB%A9%B4#%EA%B5%90%EC%82%AC)**

<br/>

<div id="4"></div>

## ✨ 기술적 특징

### 1. K-Means Clustering 모델 활용

> 비지도 학습으로 소비 유형을 군집화하고, 지도 학습으로 실시간 예측까지 이어지는 2단계 ML 파이프라인 구현

| 단계            | 내용                                                                                                                              |
| --------------- | --------------------------------------------------------------------------------------------------------------------------------- |
| **비지도 학습** | 10만 건의 가상 학생 금융 데이터로 K-Means / DBSCAN / GMM 3가지 모델을 학습하고, Silhouette Score가 가장 높은 모델을 자동으로 선택 |
| **군집 매핑**   | 저축률·소비 비율·투자 비율을 기준으로 학생의 소비 성향을 **소비형 / 저축형 / 투자형** 3가지로 분류                                |
| **지도 학습**   | SMOTE 오버샘플링으로 클래스 불균형을 보정한 뒤 Random Forest 분류기를 학습해 테스트 정확도 **98.89%** 달성                        |
| **서비스 연동** | FastAPI의 `/predict-cluster` 엔드포인트를 통해 Spring Boot 백엔드와 연동, 학생 데이터 입력 시 소비 유형을 실시간으로 예측         |

<img src="/docs/image/Ai활용.gif" width = "800">

### 2. OpenAI 프롬프팅을 활용한 경제 뉴스 생성

> 단순 API 호출을 넘어, 초등학생 눈높이에 맞는 프롬프트를 직접 설계하고 응답 파싱까지 구현

- 교사가 뉴스 생성 버튼을 클릭하면 등록된 주식 종목 중 하나를 랜덤으로 선택해 **GPT-3.5-turbo** 를 호출
- 직접적인 주가 등락 표현 대신, 관련 산업 동향이나 환경 변화를 간접적으로 담은 공시 형태의 뉴스로 생성되도록 프롬프트 설계
- GPT 응답을 **제목 / 내용 / 유형(호재·악재)** 형식으로 파싱해 DB에 저장하고, **FCM 푸시 알림**으로 학생들에게 즉시 전달
- 학생의 주간 금융 데이터와 소비 유형을 함께 전달해 **개인화된 주간 피드백**을 2~3개 항목으로 생성

<br/>

## 📚 기술 스택

### Backend & Ai

<div align=left> 
  <img src="https://img.shields.io/badge/java-007396?style=flat-square&logo=java&logoColor=white">
  <img src="https://img.shields.io/badge/mysql-4479A1?style=flat-square&logo=mysql&logoColor=white"> 
  <img src="https://img.shields.io/badge/firebase-FFCA28?style=flat-square&logo=firebase&logoColor=white">
  <img src="https://img.shields.io/badge/intellijidea-000000?style=flat-square&logo=intellijidea&logoColor=white">
  <img src="https://img.shields.io/badge/spring-6DB33F?style=flat-square&logo=spring&logoColor=white">
  <img src="https://img.shields.io/badge/springboot-6DB33F?style=flat-square&logo=springboot&logoColor=white">
  <img src="https://img.shields.io/badge/jitpack-000000?style=flat-square&logo=jitpack&logoColor=white">
  <img src="https://img.shields.io/badge/redis-FF4438?style=flat-square&logo=redis&logoColor=white">
  <img src="https://img.shields.io/badge/openjdk-000000?style=flat-square&logo=openjdk&logoColor=white">
  <img src="https://img.shields.io/badge/fastapi-009688?style=flat-square&logo=fastapi&logoColor=white">
  <img src="https://img.shields.io/badge/java-007396?style=flat-square&logo=java&logoColor=white">
  <img src="https://img.shields.io/badge/python-3776AB?style=flat-square&logo=python&logoColor=white">
  <img src="https://img.shields.io/badge/openai-412991?style=flat-square&logo=openai&logoColor=white">

</div>

### FrontEnd

<div align=left> 
  <img src="https://img.shields.io/badge/firebase-FFCA28?style=flat-square&logo=firebase&logoColor=white">
  <img src="https://img.shields.io/badge/gradle-02303A?style=flat-square&logo=gradle&logoColor=white">
  <img src="https://img.shields.io/badge/kotlin-7F52FF?style=flat-square&logo=kotlin&logoColor=white">
  <img src="https://img.shields.io/badge/openjdk-000000?style=flat-square&logo=openjdk&logoColor=white">
  <img src="https://img.shields.io/badge/lottiefiles-00DDB3?style=flat-square&logo=lottiefiles&logoColor=white">
  <img src="https://img.shields.io/badge/android-34A853?style=flat-square&logo=android&logoColor=white">
  <img src="https://img.shields.io/badge/androidstudio-3DDC84?style=flat-square&logo=androidstudio&logoColor=white">
  <img src="https://img.shields.io/badge/xml-005FAD?style=flat-square&logo=xml&logoColor=white">
  <img src="https://img.shields.io/badge/jitpack-000000?style=flat-square&logo=jitpack&logoColor=white">

</div>

### Infra

<div align=left> 
  <img src="https://img.shields.io/badge/docker-2496ED?style=flat-square&logo=docker&logoColor=white">
  <img src="https://img.shields.io/badge/jenkins-D24939?style=flat-square&logo=jenkins&logoColor=white">
  <img src="https://img.shields.io/badge/nginx-009639?style=flat-square&logo=nginx&logoColor=white">
  <img src="https://img.shields.io/badge/letsencrypt-003A70?style=flat-square&logo=letsencrypt&logoColor=white">
  <img src="https://img.shields.io/badge/flydotio-24175B?style=flat-square&logo=flydotio&logoColor=white">
  <img src="https://img.shields.io/badge/amazonec2-FF9900?style=flat-square&logo=amazonec2&logoColor=white">

</div>

### Project Management & DevOps

<div align=left> 
  <img src="https://img.shields.io/badge/git-F05032?style=flat-square&logo=git&logoColor=white">
  <img src="https://img.shields.io/badge/gitlab-FC6D26?style=flat-square&logo=gitlab&logoColor=white">
  <img src="https://img.shields.io/badge/mattermost-0058CC?style=flat-square&logo=mattermost&logoColor=white">
  <img src="https://img.shields.io/badge/notion-000000?style=flat-square&logo=notion&logoColor=white">
  <img src="https://img.shields.io/badge/jira-0052CC?style=flat-square&logo=jira&logoColor=white">
 
</div>

<br/>

<div id="5"></div>

## ✨ 프로젝트 산출물

- [API 명세서](https://github.com/breadbirds/Ttaenggurang/wiki/%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8-%EC%82%B0%EC%B6%9C%EB%AC%BC#api-%EB%AA%85%EC%84%B8%EC%84%9C)

- [ERD](https://github.com/breadbirds/Ttaenggurang/wiki/%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8-%EC%82%B0%EC%B6%9C%EB%AC%BC#erd)

- [서비스 아키텍쳐](https://github.com/breadbirds/Ttaenggurang/wiki/%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8-%EC%82%B0%EC%B6%9C%EB%AC%BC#%EC%84%9C%EB%B9%84%EC%8A%A4-%EC%95%84%ED%82%A4%ED%85%8D%EC%B3%90)

- [화면 설계서](https://github.com/breadbirds/Ttaenggurang/wiki/%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8-%EC%82%B0%EC%B6%9C%EB%AC%BC#%ED%99%94%EB%A9%B4-%EC%84%A4%EA%B3%84%EC%84%9C)

- [GIT](https://github.com/breadbirds/Ttaenggurang/wiki/%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8-%EC%82%B0%EC%B6%9C%EB%AC%BC#git)

- [영상 포트폴리오](https://youtu.be/ZU994x_80P4)

<br/>

<div id="6"></div>

## 👨‍👩‍👧‍👦 개발 멤버 및 역할분담

<table>
  <tr>
    <td align="center" width="300">
      <a href="https://github.com/breadbirds">
        <img src="https://github.com/breadbirds.png" width="300" style="border-radius: 50%;">
        <br>
        <strong>정유진</strong>
      </a>
    </td>
    <td align="center" width="300">
      <a href="https://github.com/iamjinhyeon">
        <img src="https://github.com/iamjinhyeon.png" width="300" style="border-radius: 50%;">
        <br>
        <strong>박진현</strong>
      </a>
    </td>
    <td align="center" width="300">
      <a href="https://github.com/itsanisland">
        <img src="https://github.com/itsanisland.png" width="300" style="border-radius: 50%;">
        <br>
        <strong>서미지</strong>
      </a>
    </td>
  </tr>
  <tr>
    <td align="center">
      <sub>Leader & Android</sub>
    </td>
    <td align="center">
      <sub>Android</sub>
    </td>
    <td align="center">
      <sub>Backend & Infra</sub>
    </td>
  </tr>
  <tr>
    <td align="center" width="300">
      <a href="https://github.com/ipcp365">
        <img src="https://github.com/ipcp365.png" width="300" style="border-radius: 50%;">
        <br>
        <strong>이사랑</strong>
      </a>
    </td>
    <td align="center" width="300">
      <a href="https://github.com/harperim">
        <img src="https://github.com/harperim.png" width="300" style="border-radius: 50%;">
        <br>
        <strong>임정인</strong>
      </a>
    </td>
    <td align="center" width="300">
      <a href="https://github.com/yeonji3038">
        <img src="https://github.com/yeonji3038.png" width="300" style="border-radius: 50%;">
        <br>
        <strong>최연지</strong>
      </a>
    </td>
  </tr>
  <tr>
    <td align="center">
      <sub>Android</sub>
    </td>
    <td align="center">
      <sub>Backend</sub>
    </td>
    <td align="center">
      <sub>Backend</sub>
    </td>
  </tr>
</table>

<img src="/docs/image/팀원역할.png" width = "800">

<br/>

<div id="7"></div>

## 📒 메뉴얼 및 상세 문서

- [전체 서비스 화면](https://github.com/breadbirds/Ttaenggurang/wiki/%EC%A0%84%EC%B2%B4-%EC%84%9C%EB%B9%84%EC%8A%A4-%ED%99%94%EB%A9%B4)

- [프로젝트 산출물](https://github.com/breadbirds/Ttaenggurang/wiki/%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8-%EC%82%B0%EC%B6%9C%EB%AC%BC#%ED%99%94%EB%A9%B4-%EC%84%A4%EA%B3%84%EC%84%9C)

- [포팅메뉴얼](https://github.com/breadbirds/Ttaenggurang/wiki/%ED%8F%AC%ED%8C%85-%EB%A9%94%EB%89%B4%EC%96%BC)
