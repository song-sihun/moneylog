| 화면         | 행동                     | 호출 API               |
| ------------ | ------------------------ | ---------------------- |
| 로그인       | 인증 회원가입            | POST /auth/signup      |
| 로그인       | 인증 로그인->JWT 저장    | POST /auth/login       |
| 수입지출내역 | 카테고리 선택지 불러오기 | GET /moneylog/category |
| 수입지출내역 | 수입지출 필터링,정렬     | GET /moneylog/파라미터 |
| 수입지출내역 | 전체 수입지출 불러오기   | GET /moneylog          |
| 수입지출내역 | 수입지출내역 저장        | POST /moneylog/{id}    |
| 수입지출내역 | 수입지출내역 삭제        | DELETE /moneylog/{id}  |
| 수입지출내역 | 수입지출내역 수정        | PUT /moneylog/{id}     |
