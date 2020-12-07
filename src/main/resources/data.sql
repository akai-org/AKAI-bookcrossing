INSERT INTO bookcrossing.tb_user (full_name, email)
VALUES ('Adam Nowak', 'adam.nowak@akai.org.pl');

INSERT INTO bookcrossing.tb_book (title, author, description, owner_id, reader_id, available)
VALUES ('Czysty kod. Podręcznik dobrego programisty', 'Robert C. Martin', 'Bardzo ważna książka', 1, 1, True);

INSERT INTO bookcrossing.tb_book (title, author, description, owner_id, reader_id, available)
VALUES ('Spring w Akcji 5', 'Nie', 'Bardzo ważna książka', 1, 1, True);

INSERT INTO bookcrossing.tb_opinion (rating, book_id, author_id)
VALUES (6, 1, 1);

INSERT INTO bookcrossing.tb_tag (name)
VALUES ('SPRING');

# INSERT INTO bookcrossing.tb_book_tags (book_id, tag_id)
# VALUES (1,1);
