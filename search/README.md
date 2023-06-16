# Investment

## 0. jar 다운로드
[search-1.0.0-andrew.wav.jar 다운로드 링크](./search-1.0.0-andrew.wav.jar)

## 1 기능요구사항
### 1.1 블로그 검색
- 키워드를 통해 블로그를 검색할 수 있어야 합니다.
- 검색 결과에서 Sorting(정확도순, 최신순) 기능을 지원해야 합니다.
- 검색 결과는 Pagination 형태로 제공해야 합니다.
- 검색 소스는 카카오 API의 키워드로 블로그 [검색](https://developers.kakao.com/docs/latest/ko/daum-search/dev-guide#search-blog) 을 활용합니다.
- 추후 카카오 API 이외에 새로운 검색 소스가 추가될 수 있음을 고려해야 합니다.

### 1.2. 인기 검색어 목록
- 사용자들이 많이 검색한 순서대로, 최대 10개의 검색 키워드를 제공합니다.
- 검색어 별로 검색된 횟수도 함께 표기해 주세요.


## 2. 문제 해결 Point

1. 사용자가 검색한 keyword의 count를 DB에 저장합니다.
2. Keyword 조회는 unique Column을 사용하여 string에 대한 자동 인덱스 추가합니다.
3. exception은 handler로 한곳에서처리, 각 응답마다 알맞게 http status를 내려줍니다..

## 3. Request & Response

### 3.1 응답 형식 및 예시
- code : 응답코드
- status : 응답코드에 대한 상태
- error : 오류에 대한 상세 설명 (optional)
- documents 또는 keywords : 요청한 데이터
```json
{
    "code": 200,
    "status": "OK",
    "error": null,
    "documents": {},
    "keywords": {}
}
```

### 3.2 응답 코드

| 구분 | code | status       | description                                       |
| --- |------|--------------|---------------------------------------------------|
| 성공 | 200  | OK           | 성공                                                |
| 성공 | 204  | NO_CONTENT   | 인기 검색어 요청시 data의 내용이 아무것도 없는 상태                   |
| 실패 | 400  | BAD_REQUEST  | 블로그 검색 요청시 클라이언트 파라미터 오류시 발생 |
| 실패 | 500  | INTERNAL_SERVER_ERROR  | 서버에서 에러 발생                                        |

## 4. 블로그 검색 API

상품 모집기간 (started_at, finished_at) 내의 상품 전체 응답. 페이징 없음

- METHOD : GET
- PATH : /v1/search
```bash
  curl -X GET "http://localhost:8080/v1/search?query=test&sort=accuracy&page=1&size=3"
```

### 4.1 블로그 검색 요청

| 이름 | 타입 | 설명                                                                         | 필수 |
|-----|----|----------------------------------------------------------------------------|------|
| query	| String| 검색을 원하는 질의어 특정 블로그 글만 검색하고 싶은 경우, 블로그 url과 검색어를 공백(' ') 구분자로 넣을 수 있음       |	O|
| sort	|String | 	결과 문서 정렬 방식, accuracy(정확도순) 또는 recency(최신순), 기본 값 accuracy	               | X|
| page	|Integer| 	결과 페이지 번호, 1~50 사이의 값, 기본 값 1                                             |	X|
| size	|Integer| 	한 페이지에 보여질 문서 수, 1~50 사이의 값, 기본 값 10	                                     |X|

### 4.2 블로그 검색 응답
| 이름         | 타입         | 설명              | 필수  |
|------------|------------|-----------------|-----|
| code	      | Int        | 응답 상태 코드        | 	O  |
| status	    | String     | 	응답 상태 설명	      | O   |
| error	     | String     | 	에러인 경우 메시지     | 	X  |
| documents	 | Document[] | 	블로그 검색 결과 리스트	 | X   |


### 4.3 블로그 검색 응답 예시
 ```json
 {
  "documents": [
    {
      "title": "z-<b>test</b>(z-통계량)",
      "contents": "2020년 만 7세 여자 어린이의 평균키는 120cm이다. 대립가설(H1) ① 2020년 만 7세 여자 어린이의 평균키는 120cm가 아니다. : 제1형 (Two-Sided <b>Test</b> = Two Tailed <b>Test</b>) ② 2020년 만 7세 여자 어린이의 평균키는 120cm보다 작다. : 제2형 (One-Sided <b>Test</b> = Lower Tailed <b>Test</b>) ③ 2020년 만 7세 여자 어린이의 평균...",
      "url": "http://hi-210.tistory.com/29",
      "blogname": "빅데이터 전문가 되기",
      "thumbnail": "https://search2.kakaocdn.net/argon/130x130_85_c/9wmoxU6Klzx",
      "datetime": "2023-06-09T16:18:20.000+09:00"
    },
    {
      "title": "[Java <b>Test</b>] 4. Jmeter 성능테스트",
      "contents": "JMeter - Apache JMeter™ Apache JMeter™ The Apache JMeter™ application is open source software, a 100% pure Java application designed to load <b>test</b> functional behavior and measure performance. It was originally designed for testing Web Applications but has since expanded to oth jmeter.apache.org...",
      "url": "http://maybechrisk.tistory.com/115",
      "blogname": "개발합니다.",
      "thumbnail": "https://search1.kakaocdn.net/argon/130x130_85_c/EuSUp9VyMmn",
      "datetime": "2023-05-25T18:01:07.000+09:00"
    }
  ],
  "status": "OK",
  "error": null,
  "code": 200
}
 ```

## 5. 상위 인기 검색어 API

- METHOD : GET
- PATH : /v1/popular
```bash
  curl -X GET "http://localhost:8080/v1/popular"
```
### 5.1 인기 검색어 요청

### 5.2 블로그 검색 응답
| 이름        | 타입               | 설명              | 필수  |
|-----------|------------------|-----------------|-----|
| code	     | Int              | 응답 상태 코드        | 	O  |
| status	   | String           | 	응답 상태 설명	      | O   |
| error	    | String           | 	에러인 경우 메시지     | 	X  |
| keywords	 | PopularKeyword[] | 	인기 검색어 결과 리스트	 | X   |

### 5.3 블로그 검색 응답 예시
 ```json
{
  "code": 200,
  "status": "OK",
  "error": null,
  "keywords": [
    {
      "keyword": "test",
      "count": 5
    },
    {
      "keyword": "test2",
      "count": 3
    },
    {
      "keyword": "test3",
      "count": 2
    }
  ]
}
 ```
