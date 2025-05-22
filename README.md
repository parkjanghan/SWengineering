### 중앙대학교 소프트웨어공학01분반 16조

# Yutnori Game
##📌 Project Overview
본 프로젝트는 중앙대학교 소프트웨어공학 수업의 팀 프로젝트 과제로,
소프트웨어 개발 생명주기를 기반으로 전통 민속놀이인 윷놀이 게임을 Java 언어로 구현하는 것을 목표로 하였습니다.

프로젝트 수행 과정에서는 다음과 같은 소프트웨어 공학 원칙 및 기법을 적용하였습니다:

요구사항 분석 및 명세
- 사용자 관점에서 유스케이스 도출 및 명세서 작성

시스템 설계
- UML 다이어그램(Use Case, Class, Sequence 등)을 기반으로 설계
- MVC 패턴을 적용하여 Model-View-Controller의 책임을 분리
- Observer 패턴을 활용해 뷰 갱신 구조 설계

구현 및 테스트
- Java와 Swing을 기반으로 UI 및 로직을 구현하고,
- 기능별 JUnit 테스트 및 수동 테스트를 병행하여 검증

기능 구성
- 다양한 보드 설정(사각형/오각형/육각형)
- 플레이어 수 및 말 개수 설정
- 윷 던지기, 말 이동, 말 잡기, 추가 턴, 게임 종료/재시작
  
## 🛠 Tech Stack
- Java
- Java Swing(GUI)
- Observer Pattern

## 📁 Directory
```
yutnori
├── src
│   ├── assets
│   ├── board
│   ├── display
│   ├── GameModel
│   ├── main
│   └── play
└── README.md
```
## 📝 Document
- Use case diagram
- Class diagram
- State diagram

## 🙋‍♂️ Created by
- 20212#63 김재경
- 20213#42 박장한
- 20214#55 전형원
- 20221#04 정소은
- 20234#93 최예린
