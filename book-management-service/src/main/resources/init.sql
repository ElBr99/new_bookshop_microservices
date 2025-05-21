create schema  if not exists book-management-service;

create table if not exists book-management-service.books (
  id UUID DEFAULT RANDOM_UUID() primary key,
  title varchar(255) not null,
  author varchar(255) not null,
  genre varchar(255) not null,
  publishing_year varchar(255) not null
  );

create table if not exists book-management-service.book_storage (
    id UUID DEFAULT RANDOM_UUID() primary key,
    address varchar(255) not null unique,
    name varchar(255) not null,
    total_amount int
    );

create table if not exists book-management-service.offline_bookshop (
     id UUID DEFAULT RANDOM_UUID() primary key,
      address varchar(255) not null unique,
      name varchar(255) not null,
      total_amount int
    );

create table if not exists book-management-service.book_info (
  id UUID DEFAULT RANDOM_UUID() primary key,
  book_id UUID not null references entities.books(id) on delete cascade,
  amount int,
  price numeric(15,2),
  storage_id UUID references entities.book_storage(id) on delete cascade,
  offline_book_shop_id UUID references entities.offline_bookshop(id) on delete cascade
  );


CREATE TABLE if not exists book-management-service.user_favorite_books (
    user_id UUID NOT NULL,
    book_info_id UUID NOT NULL,
    PRIMARY KEY (user_id, book_info_id),
    FOREIGN KEY (book_info_id) REFERENCES entities.book_info(id) ON DELETE CASCADE
);

