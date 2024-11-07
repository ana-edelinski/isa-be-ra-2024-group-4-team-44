insert into users (username, email, password, activated, name) values ('pera', 'pera@gmail.com', '$2a$10$phbu2oHcLQFL4PoXYyRW/erd.WJsHr9oAkuMxk0MoBdNjZ02Evxm2', true , 'pera');

insert into addresses (street, city, postal_code) values ( 'Main Street 123', 'Belgrade', '11000');

insert into posts (user_id, description, creation_time, image_path, location_id)
values (
           1,
           'This is a sample post description.',
           '2024-11-07 12:30:00',
           '/images/sample.jpg',
           1
       );

insert into likes(user_id, post_id)
values  (
         1,
         1
        );
insert into comments(text, creation_time, user_id, post_id)
values (
        'komentar',
        '2024-11-07 12:30:00',
        1,
        1
       )

