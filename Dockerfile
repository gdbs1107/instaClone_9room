# 1. JDK 이미지를 기반으로 함
FROM openjdk:17-jdk-slim

# 2. 애플리케이션을 위한 작업 디렉터리 생성
WORKDIR /app

# 3. 프로젝트의 JAR 파일을 컨테이너에 복사
COPY build/libs/*.jar /app/app.jar

# 4. 컨테이너가 시작될 때 JAR 파일 실행
CMD ["java", "-jar", "/app/app.jar"]

# 5. 포트 설정 (Spring Boot의 기본 포트는 8080)
EXPOSE 8080
