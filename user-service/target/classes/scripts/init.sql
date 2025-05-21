create schema  if not exists user_service;


create table if not exists user_service.users(
  id UUID DEFAULT RANDOM_UUID() primary key,
  email varchar(255) not null unique,
  name varchar(255) not null,
  password varchar(255) not null,
  book_storage_id UUID ,
  book_shop_id UUID ,
  users_role varchar(200) not null,
  DTYPE VARCHAR(50)
  );

