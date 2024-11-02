insert into student (index_number, first_name, last_name) values ('5', 'Marko', 'Marković');
insert into student (index_number, first_name, last_name) values ('ra2-2014', 'Milan', 'Milanović');
insert into student (index_number, first_name, last_name) values ('ra3-2014', 'Ivana', 'Ivanović');
insert into student (index_number, first_name, last_name) values ('ra4-2014', 'Bojan', 'Bojanović');
insert into student (index_number, first_name, last_name) values ('ra5-2014', 'Pera', 'Perić');
insert into student (index_number, first_name, last_name) values ('ra6-2014', 'Zoran', 'Zoranović');
insert into student (index_number, first_name, last_name) values ('ra7-2014', 'Bojana', 'Bojanović');
insert into student (index_number, first_name, last_name) values ('ra8-2014', 'Milana', 'Milanović');
insert into student (index_number, first_name, last_name) values ('ra9-2014', 'Jovana', 'Jovanić');

insert into course (name) values ('Matematika');
insert into course (name) values ('Osnove programiranja');
insert into course (name) values ('Objektno programiranje');

insert into teacher (first_name, last_name, deleted) values ('Strahinja', 'Simić', false);
insert into teacher (first_name, last_name, deleted) values ('Marina', 'Antić', false);
insert into teacher (first_name, last_name, deleted) values ('Siniša', 'Branković', false);

insert into teaching (course_id, teacher_id) values (1, 1);
insert into teaching (course_id, teacher_id) values (1, 2);
insert into teaching (course_id, teacher_id) values (2, 2);
insert into teaching (course_id, teacher_id) values (3, 3);

insert into exam (student_id, course_id, date, grade) values (1, 1, '2016-02-01', 9);
insert into exam (student_id, course_id, date, grade) values (1, 2, '2016-04-19', 8);
insert into exam (student_id, course_id, date, grade) values (2, 1, '2016-02-01', 10);
insert into exam (student_id, course_id, date, grade) values (2, 2, '2016-04-19', 10);

INSERT INTO users (username, email, password, activated) VALUES ('marko', 'marko@example.com', 'password123', true);
INSERT INTO users (username, email, password, activated) VALUES ('milan', 'milan@example.com', 'password456', true);
INSERT INTO users (username, email, password, activated) VALUES ('ivana', 'ivana@example.com', 'password789', false);
INSERT INTO users (username, email, password, activated) VALUES ('bojan', 'bojan@example.com', 'bojanpass', true);
INSERT INTO users (username, email, password, activated) VALUES ('pera', 'pera@example.com', 'perapass', false);
INSERT INTO users (username, email, password, activated) VALUES ('zoran', 'zoran@example.com', 'zoranpass', true);
INSERT INTO users (username, email, password, activated) VALUES ('bojana', 'bojana@example.com', 'bojanapass', false);
INSERT INTO users (username, email, password, activated) VALUES ('milana', 'milana@example.com', 'milanapass', true);
INSERT INTO users (username, email, password, activated) VALUES ('jovana', 'jovana@example.com', 'jovanapass', false);
