#!/bin/sh
# (필요시 환경변수, 마이그레이션 등 추가)
echo "Starting app with DB_URL=$DB_URL"

exec java -jar \
  -DDB_URL=$DB_URL \
  -DDB_USER=$DB_USER \
  -DDB_PASSWORD=$DB_PASSWORD \
  -DEMAIL_PASSWORD=$EMAIL_PASSWORD \
  -DJWT_SECRET=$JWT_SECRET \
  -DKAKAO_CLIENT_ID=$KAKAO_CLIENT_ID \
  app.jar