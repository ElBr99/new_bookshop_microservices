INSERT INTO entities.orders (id, customer, customer_email, book_storage_id, status, sum, customer_box) VALUES
  ('550e8400-e29b-41d4-a716-446655440013', '550e8400-e29b-41d4-a716-446655440011', 'john.doe@example.com', '550e8400-e29b-41d4-a716-446655440004', 'ACCEPTED', 29.97, '[{"id": "550e8400-e29b-41d4-a716-446655440000", "title": "Dune", "amount": 2}]' FORMAT JSON),
  ('550e8400-e29b-41d4-a716-446655440014', '550e8400-e29b-41d4-a716-446655440012', 'jane.smith@example.com', '550e8400-e29b-41d4-a716-446655440005', 'PENDING', 15.98, '[{"id": "550e8400-e29b-41d4-a716-446655440000", "title": "Dune", "amount": 2}]' FORMAT JSON );


INSERT INTO entities.cheques (id, order_id, offline_purchase_id, online_purchase_id, customer_email, sum, purchase_date, cheque_type) VALUES
  ('550e8400-e29b-41d4-a716-446655440015', '550e8400-e29b-41d4-a716-446655440013', NULL, '550e8400-e29b-41d4-a716-446655440004', 'john.doe@example.com', 29.97, NOW(), 'Online'),
  ('550e8400-e29b-41d4-a716-446655440016', '550e8400-e29b-41d4-a716-446655440014', '550e8400-e29b-41d4-a716-446655440006', NULL, 'jane.smith@example.com', 15.98, NOW(), 'Offline');


INSERT INTO entities.user_favorite_books (user_id, book_info_id) VALUES
  ('550e8400-e29b-41d4-a716-446655440011', '550e8400-e29b-41d4-a716-446655440008'),
  ('550e8400-e29b-41d4-a716-446655440011', '550e8400-e29b-41d4-a716-446655440010');