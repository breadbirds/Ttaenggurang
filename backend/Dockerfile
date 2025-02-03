FROM openjdk:17-jdk

# UTF-8 로케일 설정
ENV LANG=ko_KR.UTF-8
ENV LANGUAGE=ko_KR:ko
ENV LC_ALL=ko_KR.UTF-8

WORKDIR /app

# 캐시 방지를 위해 빌드 시간 추가
ARG CACHE_BUSTER=1

# 애플리케이션 복사 및 실행 설정
COPY build/libs/*SNAPSHOT.jar ttgr.jar

ENTRYPOINT ["java", "-jar", "ttgr.jar"]