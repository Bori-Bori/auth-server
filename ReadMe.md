# Bori-Bori Auth Server

> 본 레포지토리는 보리보리의 인증 서버입니다.

# How to Run?

1. First Step

```shell
$ git clone https://github.com/Bori-Bori/auth-server.git
```

2. Second Step

locate directory

3. Third Step

```shell
$ ./gradlew build
```

# How to Contribute?

1. Register Issue
2. editing code
3. create pull & request

# Server Functions

## Oauth2 Login

1. Kakao Oauth2 Protocol을 이용한 로그인을 이용합니다.

## API 보안

* 서버 자체 JWT를 생성하여 사용자에게 발급합니다.

## JWT Refresh

* RefreshToken을 이용하여 AccessToken을 Refresh 합니다.

# Architecture

## Server Architecture
<img width="854" alt="스크린샷 2022-10-27 오전 11 16 06" src="https://user-images.githubusercontent.com/79268661/198176140-6aa8060e-77b6-492f-90a2-d46f42bb5129.png">


## Cloud Architecture
