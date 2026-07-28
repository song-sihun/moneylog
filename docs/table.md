users(사용자), trans_logs(거래내역)

users

| columm                     | 내용         | 비고 |
| -------------------------- | ------------ | ---- |
| id                         | BIGINT       | PK   |
| email(로그인ID)            | varchar(255) |      |
| password(BCrypt 저장)      | varchar(255) |      |
| nickname(사용자 표시 이름) | varchar(255) |      |
| created_at(가입시각)       | datetime     |      |

trans_log (users가 여러개를 가짐)

| columm                          | 내용                      | 비고                                                   |
| ------------------------------- | ------------------------- | ------------------------------------------------------ |
| id                              | BIGINT                    | PK                                                     |
| user_id(사용자 id FK)           | BIGINT                    | FK                                                     |
| amount (수입/지출 금액)         | BIGINT                    | --                                                     |
| type(수입/지출 구분)            | ENUM('INCOME', 'EXPENSE') | --                                                     |
| category(교통,식비 등 카테고리) | varchar(255)              | category는 mvc의 서비스에서 enum 참고해서 입력 할 예정 |
| description(내용)               | VARCHAR(255)              | --                                                     |
| trans_date                      | date                      | --                                                     |
| created_at(생성 시각)           | datetime                  | --                                                     |
| updated_at(수정 시각)           | datetime                  | --                                                     |
