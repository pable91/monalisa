insert into USER (account_id, pw, name, email, create_date) values
    ('AccountID1','$2a$10$eIIdfGFT8qJiavcREbl','kim','kim@naver.com', NOW()),
    ('AccountID2','$2a$10$eIIdfGFT8qJiavcREbl','lee','lee@gmail.com', NOW()),
    ('AccountID3','$2a$10$eIIdfGFT8qJiavcREbl','park','part@gmail.com', NOW());

insert into BOOK (name,description,cost,author,is_sold,user_id,order_id) values
    ('java', 'java book desciption', 5000, 'tom', false, 2, null),
    ('jpa', 'jpa book desciption', 7000, 'kevin', false, 2, null),
    ('spring', 'spring book desciption', 15000, 'k', false, 3, null);