DROP TABLE IF EXISTS person;
DROP TABLE IF EXISTS book;
DROP TABLE IF EXISTS issue_card;
DROP TABLE IF EXISTS rating;


CREATE TABLE person (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  first_name VARCHAR(250) NOT NULL,
  last_name VARCHAR(250) NOT NULL,
  birthDate  Date NULL,
  last_update  Date NULL,
  created_date Date NULL
);

CREATE TABLE book (
    id INT AUTO_INCREMENT PRIMARY KEY,
    book_name VARCHAR(250) NOT NULL,
    author_name VARCHAR(250) NOT NULL,
    category_type VARCHAR(250) NOT NULL,
    book_register_date DATE NOT NULL,
    volume INT NOT NULL,
    IS_DELETED INT NOT NULL
);

CREATE TABLE issue_card (
    id INT AUTO_INCREMENT PRIMARY KEY,
    person_id INT,
    issue_no VARCHAR(250) UNIQUE NOT NULL,
    created_date DATE NULL,
    expired_date DATE NULL,
    book_id INT ,
    FOREIGN KEY(person_id) REFERENCES person(id),
    FOREIGN KEY(book_id) REFERENCES book(id)
);


CREATE TABLE rating (
    id INT AUTO_INCREMENT PRIMARY KEY,
    rating_star VARCHAR(250)  NOT NULL,
    description VARCHAR(250),
    issue_card_id INT NULL,
    FOREIGN KEY(issue_card_id) REFERENCES issue_card(id)
);

