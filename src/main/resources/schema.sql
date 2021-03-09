CREATE TABLE IF NOT EXISTS tb_user
(
    id        INT AUTO_INCREMENT NOT NULL,
    full_name VARCHAR(255),
    email     VARCHAR(255),
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;;

CREATE TABLE IF NOT EXISTS tb_resource
(
    id          INT AUTO_INCREMENT      NOT NULL,
    type        enum('book', 'ebook')   NOT NULL,
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;;

CREATE TABLE IF NOT EXISTS tb_book
(
    id          INT             NOT NULL,
    title       VARCHAR(255)    NOT NULL,
    author      VARCHAR(255)    NOT NULL,
    description TEXT,
    owner_id    INT,
    reader_id   INT,
    available   BOOLEAN,
    PRIMARY KEY (id),
    FOREIGN KEY (id) REFERENCES tb_resource (id),
    FOREIGN KEY (owner_id) REFERENCES tb_user (id),
    FOREIGN KEY (reader_id) REFERENCES tb_user (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;;

CREATE TABLE IF NOT EXISTS tb_ebook
(
    id          INT           NOT NULL,
    google_id   VARCHAR(255)  NOT NULL,
    title       VARCHAR(255),
    author      VARCHAR(255),
    description TEXT,
    url         VARCHAR(2083),
    PRIMARY KEY (id),
    UNIQUE KEY (google_id),
    FOREIGN KEY (id) REFERENCES tb_resource (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;;

CREATE TABLE IF NOT EXISTS tb_opinion
(
    id          INT AUTO_INCREMENT NOT NULL,
    rating      INT                NOT NULL,
    description TEXT,
    resource_id INT                NOT NULL,
    author_id   INT                NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (resource_id) REFERENCES tb_resource (id),
    FOREIGN KEY (author_id) REFERENCES tb_user (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;;

CREATE TABLE IF NOT EXISTS tb_tag
(
    id   INT AUTO_INCREMENT NOT NULL,
    name varchar(255)       NOT NULL,
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;;

CREATE TABLE IF NOT EXISTS tb_resource_tags
(
    resource_id INT NOT NULL,
    tag_id      INT NOT NULL,
    PRIMARY KEY (resource_id, tag_id),
    FOREIGN KEY (resource_id) REFERENCES tb_resource (id),
    FOREIGN KEY (tag_id) REFERENCES tb_tag (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;;

CREATE TABLE IF NOT EXISTS tb_book_user_request
(
    id           INT AUTO_INCREMENT NOT NULL,
    requester_id INT                NOT NULL,
    book_id      INT                NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (requester_id) REFERENCES tb_user (id),
    FOREIGN KEY (book_id) REFERENCES tb_book (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;;

DROP PROCEDURE IF EXISTS INSERT_BOOK;;

CREATE PROCEDURE INSERT_BOOK (IN v_title VARCHAR(255) CHARSET utf8,
                            IN v_author VARCHAR(255) CHARSET utf8,
                            IN v_description TEXT CHARSET utf8,
                            IN v_owner_id INTEGER,
                            IN v_reader_id INTEGER,
                            OUT v_book_id INTEGER)
BEGIN
INSERT INTO tb_resource(type)
VALUES ('book');
INSERT INTO tb_book (id, title, author, description, owner_id, reader_id, available)
VALUES (LAST_INSERT_ID(), v_title, v_author, v_description, v_owner_id, v_reader_id, 1);
SELECT LAST_INSERT_ID() INTO v_book_id;
END;;

DROP PROCEDURE IF EXISTS INSERT_EBOOK;;

CREATE PROCEDURE INSERT_EBOOK (IN v_google_id VARCHAR(255) CHARSET utf8,
                            IN v_title VARCHAR(255) CHARSET utf8,
                            IN v_author VARCHAR(255) CHARSET utf8,
                            IN v_description TEXT CHARSET utf8,
                            IN v_url VARCHAR(2083) CHARSET utf8,
                            OUT v_ebook_id INTEGER)
BEGIN
INSERT INTO tb_resource(type)
VALUES ('book');
INSERT INTO tb_book (id, google_id, title, author, description, url)
VALUES (LAST_INSERT_ID(), v_google_id, v_title, v_author, v_description, v_url);
SELECT LAST_INSERT_ID() INTO v_ebook_id;
END;;
