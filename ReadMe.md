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

4. Fourth Step

```shell
$ nohup java -jar ~.jar & /dev/null
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

## 프로필 변경
* 사용자의 닉네임을 변경합니다.
* 사용자의 프로필 이미지를 변경합니다.

## 이벤트 publish & consume
* Kafka를 통해서 이벤트를 발행합니다.
* Kafka를 통해서 이벤트를 소비합니다.

# Architecture

## Server Architecture
<img width="623" alt="스크린샷 2022-12-01 오후 5 34 30" src="https://user-images.githubusercontent.com/79268661/205028429-28a5b603-6f57-4f9b-959e-ddf49ed1dc1a.png">

# Server Stack

* Framework : Spring boot 2.7.5

* Web Library : Spring Reactive Web, WebFlux

* DB :
  * Cassandra latest version
* Event Queue :
  * zookeeper latest version
  * kafka latest version

# Reference

* [✅ Auth-Server Repository](https://github.com/Bori-Bori/auth-server)

* [✅ Board-Server Repository](https://github.com/Bori-Bori/board-server)

책에 대한 게시글 서비스를 제공하는 서버입니다.

* [✅ Book-Server Repository](https://github.com/Bori-Bori/book-server)
책 정보를 수집하여 제공하는 서버입니다.

* [✅ Auth Wiki](https://github.com/Bori-Bori/auth-server/wiki)

* [✅ BoriBori Wiki](https://simyeon-workspace.notion.site/Bori-Bori-881dcdee1688425bb8c887d637cac598)

* [✅ API 명세서](https://simyeon-workspace.notion.site/API-eb991d72d50d4f5c8069334874fc6442)



