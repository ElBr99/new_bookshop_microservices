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


INSERT INTO user_service.users (id, email, name, password, book_storage_id, book_shop_id, users_role, DTYPE) VALUES
    ('550e8400-e29b-41d4-a716-446655440011', 'john.doe@example.com', 'John Doe', '$2a$12$LoS4SqPd3gBtCOLaOqAwIecSb6VhM0Uh2BW56jjWQ6kSfB./1r8ZC', null, null, 'CUSTOMER', 'Customer'),
    ('550e8400-e29b-41d4-a716-446655440012', 'jane.smith@example.com', 'Jane Smith', '$2a$12$qetqhqcJla/RdQJ3t1usNe1ii0lP71F9NYygkaCQddt66McwUqVI6', null, null, 'CUSTOMER', 'Customer'),
    ('be610de3-0b62-4de8-9530-4164289839b6', 'seller.seller@example.com', 'Seller Seller', '$2a$12$PyQRCbhjt7XuFcsByesbeunsfyFj4Lo3Mz8Qkp14sUqy8bB1mWbSO', null, '550e8400-e29b-41d4-a716-446655440006', 'BOOKSELLER', 'BookSeller'),
    ('e95188b5-9c73-4165-92d3-506f54422443', 'keeper.keeper@example.com', 'Keeper Keeper', '$2a$12$u/LC2EIR6oXx88u/e4xd..JRqZ4apaxIamqDozhsP9p5d787XbaJK','550e8400-e29b-41d4-a716-446655440004', null, 'STOREKEEPER', 'StoreKeeper');

