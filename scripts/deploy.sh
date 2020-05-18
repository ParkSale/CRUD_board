#!/bin/bash

REPOSITORY=/home/ec2-user/app/step2
PROJECT_NAME=CRUD_board

echo "> Build 파일 복사"

cp $REPOSITORY/zip/*.jar $REPOSITORY/

echo "> 현재 구동중인 애플리케이션 종료"

fuser -k -n tcp 8080

echo "> 새 애플리케이션 배포"

JAR_NAME=$(ls -tr $REPOSITORY/ | grep .jar | tail -n 1)

echo "> JAR Name: $JAR_NAME"

echo "> JAR Name 에 실행권한 추가"

chmod +x $JAR_NAME

echo "> $JAR_NAME 실행"

nohup java -jar \
        -Dspring.config.location=classpath:/application.properties,/home/ec2-user/app/aws.yml,/home/ec2-user/app/application-real-db.properties \
        -Dspring.profiles.active=real \
        $JAR_NAME > $REPOSITORY/nohup.out  2>&1 &