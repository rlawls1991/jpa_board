# 기능
* 사용자는 텍스트로 된 공지를 추가할 수 있다.
* 사용자는 공지를 수정/삭제할 수 있다.
* 사용자는 공지 목록을 조회할 수 있다.
* 조회시 제목, 작성일, 작성자, 최종 수정일, 내용이 조회 가능하다.
* 목록은 페이징 기능이 있다.

# Rest API
* RestAPI로 개발
    * MockMvcRestDocumentation.document; 사용
* build > generated-snippets 에서 해당되는 RestAPI Test 결과 확인 가능
* src > asciidoc > index.adoc 에서 전체적인 내용 확인 가능 

* 실행환경
    * java 11
    * H2 DB 
    * Intellij 
    * git 
    * JPA
    * restdocs
    * querydsl
    * lombok
    * hateoas