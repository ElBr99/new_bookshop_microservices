create schema  if not exists entities;

create table if not exists entities.books (
  id UUID DEFAULT RANDOM_UUID() primary key,
  title varchar(255) not null,
  author varchar(255) not null,
  genre varchar(255) not null,
  publishing_year varchar(255) not null
  );

create table if not exists entities.book_storage (
    id UUID DEFAULT RANDOM_UUID() primary key,
    address varchar(255) not null unique,
    name varchar(255) not null,
    total_amount int
    );

create table if not exists entities.offline_bookshop (
     id UUID DEFAULT RANDOM_UUID() primary key,
      address varchar(255) not null unique,
      name varchar(255) not null,
      total_amount int
    );

create table if not exists entities.book_info (
  id UUID DEFAULT RANDOM_UUID() primary key,
  book_id UUID not null references entities.books(id) on delete cascade,
  amount int,
  price numeric(15,2),
  storage_id UUID references entities.book_storage(id) on delete cascade,
  offline_book_shop_id UUID references entities.offline_bookshop(id) on delete cascade
  );


--create table if not exists entities.users(
--  id UUID DEFAULT RANDOM_UUID() primary key,
--  email varchar(255) not null unique,
--  name varchar(255) not null,
--  password varchar(255) not null,
--  book_storage_id UUID references entities.book_storage(id),
--  book_shop_id UUID references entities.offline_bookshop(id),
--  users_role varchar(200) not null,
--  DTYPE VARCHAR(50)
--  );


create table if not exists entities.orders (
    id UUID DEFAULT RANDOM_UUID() primary key,
    customer UUID ,
    customer_email varchar (250),
    book_storage_id UUID references entities.book_storage(id),
    book_shop_id UUID references entities.offline_bookshop(id),
    status varchar (50) not null,
    sum numeric(10, 2) NOT NULL,
    customer_box JSON
    );


CREATE TABLE if not exists entities.user_favorite_books (
    user_id UUID NOT NULL,
    book_info_id UUID NOT NULL,
    PRIMARY KEY (user_id, book_info_id),
--    FOREIGN KEY (user_id) REFERENCES entities.users(id) ON DELETE CASCADE,
    FOREIGN KEY (book_info_id) REFERENCES entities.book_info(id) ON DELETE CASCADE
);


CREATE TABLE if not exists entities.cheques (
      id UUID DEFAULT RANDOM_UUID() primary key,
      order_id UUID references entities.orders(id),
      offline_purchase_id UUID  references entities.offline_bookshop(id),
      online_purchase_id UUID references entities.book_storage(id),
      customer_email VARCHAR(255) ,
      sum DECIMAL(10, 2) NOT NULL,
      purchase_date TIMESTAMP WITH TIME ZONE NOT NULL,
      cheque_type VARCHAR(50) NOT NULL
  );

