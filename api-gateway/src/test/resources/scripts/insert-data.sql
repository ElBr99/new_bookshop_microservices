INSERT INTO entities.books (id, title, author, genre, publishing_year) VALUES
  ('550e8400-e29b-41d4-a716-446655440000', 'Dune', 'Frank Herbert', 'SCIENCE_FICTION', '1965'),
  ('550e8400-e29b-41d4-a716-446655440001', '1984', 'George Orwell', 'NOVEL', '1949'),
  ('550e8400-e29b-41d4-a716-446655440002', 'Man’s Search for Meaning', 'Viktor Frankl', 'PSYCHOLOGY', '1946'),
  ('550e8400-e29b-41d4-a716-446655440003', 'The Alchemist', 'Paulo Coelho', 'STORY', '1988');


INSERT INTO entities.book_storage (id, address, name, total_amount) VALUES
  ('550e8400-e29b-41d4-a716-446655440004', '123 Main St, Springfield', 'Springfield Bookstore', 100),
  ('550e8400-e29b-41d4-a716-446655440005', '456 Elm St, Metropolis', 'Metropolis Bookshop', 250);


INSERT INTO entities.offline_bookshop (id, address, name, total_amount) VALUES
  ('550e8400-e29b-41d4-a716-446655440006', '789 Oak St, Gotham', 'Gotham Books', 150),
  ('550e8400-e29b-41d4-a716-446655440007', '321 Pine St, Star City', 'Star City Bookstore', 80);


INSERT INTO entities.book_info (id, book_id, amount, price, storage_id, offline_book_shop_id) VALUES
  ('550e8400-e29b-41d4-a716-446655440008', '550e8400-e29b-41d4-a716-446655440000', 30, 9.99, '550e8400-e29b-41d4-a716-446655440004', NULL),
  ('550e8400-e29b-41d4-a716-446655440009', '550e8400-e29b-41d4-a716-446655440001', 20, 7.99, null, '550e8400-e29b-41d4-a716-446655440006'),
  ('550e8400-e29b-41d4-a716-446655440010', '550e8400-e29b-41d4-a716-446655440000', 25, 8.50, '550e8400-e29b-41d4-a716-446655440005', NULL);


--INSERT INTO entities.users (id, email, name, password, book_storage_id, book_shop_id, users_role, DTYPE) VALUES
--  ('550e8400-e29b-41d4-a716-446655440011', 'john.doe@example.com', 'John Doe', '$2a$12$LoS4SqPd3gBtCOLaOqAwIecSb6VhM0Uh2BW56jjWQ6kSfB./1r8ZC', null, null, 'CUSTOMER', 'Customer'),
--  ('550e8400-e29b-41d4-a716-446655440012', 'jane.smith@example.com', 'Jane Smith', '$2a$12$qetqhqcJla/RdQJ3t1usNe1ii0lP71F9NYygkaCQddt66McwUqVI6', null, null, 'CUSTOMER', 'Customer'),
--  ('be610de3-0b62-4de8-9530-4164289839b6', 'seller.seller@example.com', 'Seller Seller', '$2a$12$PyQRCbhjt7XuFcsByesbeunsfyFj4Lo3Mz8Qkp14sUqy8bB1mWbSO', null, '550e8400-e29b-41d4-a716-446655440006', 'BOOKSELLER', 'BookSeller'),
--  ('e95188b5-9c73-4165-92d3-506f54422443', 'keeper.keeper@example.com', 'Keeper Keeper', '$2a$12$u/LC2EIR6oXx88u/e4xd..JRqZ4apaxIamqDozhsP9p5d787XbaJK','550e8400-e29b-41d4-a716-446655440004', null, 'STOREKEEPER', 'StoreKeeper');
--

--INSERT INTO entities.orders (id, customer_id, book_storage_id, status, sum, customer_box) VALUES
--  ('550e8400-e29b-41d4-a716-446655440013', '550e8400-e29b-41d4-a716-446655440011', '550e8400-e29b-41d4-a716-446655440004', 'ACCEPTED', 29.97, '[{"id": "550e8400-e29b-41d4-a716-446655440000", "title": "Dune", "amount": 2}]' FORMAT JSON),
--  ('550e8400-e29b-41d4-a716-446655440014', '550e8400-e29b-41d4-a716-446655440012', '550e8400-e29b-41d4-a716-446655440005', 'PENDING', 15.98, '[{"id": "550e8400-e29b-41d4-a716-446655440000", "title": "Dune", "amount": 2}]' FORMAT JSON );

INSERT INTO entities.orders (id, customer, customer_email, book_storage_id, status, sum, customer_box) VALUES
  ('550e8400-e29b-41d4-a716-446655440013', '550e8400-e29b-41d4-a716-446655440011', 'john.doe@example.com', '550e8400-e29b-41d4-a716-446655440004', 'ACCEPTED', 29.97, '[{"id": "550e8400-e29b-41d4-a716-446655440000", "title": "Dune", "amount": 2}]' FORMAT JSON),
  ('550e8400-e29b-41d4-a716-446655440014', '550e8400-e29b-41d4-a716-446655440012', 'jane.smith@example.com', '550e8400-e29b-41d4-a716-446655440005', 'PENDING', 15.98, '[{"id": "550e8400-e29b-41d4-a716-446655440000", "title": "Dune", "amount": 2}]' FORMAT JSON );


INSERT INTO entities.cheques (id, order_id, offline_purchase_id, online_purchase_id, customer_email, sum, purchase_date, cheque_type) VALUES
  ('550e8400-e29b-41d4-a716-446655440015', '550e8400-e29b-41d4-a716-446655440013', NULL, '550e8400-e29b-41d4-a716-446655440004', 'john.doe@example.com', 29.97, NOW(), 'Online'),
  ('550e8400-e29b-41d4-a716-446655440016', '550e8400-e29b-41d4-a716-446655440014', '550e8400-e29b-41d4-a716-446655440006', NULL, 'jane.smith@example.com', 15.98, NOW(), 'Offline');


INSERT INTO entities.user_favorite_books (user_id, book_info_id) VALUES
  ('550e8400-e29b-41d4-a716-446655440011', '550e8400-e29b-41d4-a716-446655440008'),
  ('550e8400-e29b-41d4-a716-446655440011', '550e8400-e29b-41d4-a716-446655440010');