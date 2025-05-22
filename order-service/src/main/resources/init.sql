create schema  if not exists order_service;

create table if not exists order_service.orders (
    id UUID DEFAULT RANDOM_UUID() primary key,
    customer UUID,
    customer_email varchar (250),
    book_storage_id UUID ,
    book_shop_id UUID ,
    status varchar (50) not null,
    sum numeric(10, 2) NOT NULL,
    customer_box JSON
    );


CREATE TABLE if not exists order_service.cheques (
      id UUID DEFAULT RANDOM_UUID() primary key,
      order_id UUID references order_service.orders(id),
      offline_purchase_id UUID ,
      online_purchase_id UUID ,
      customer_email VARCHAR(255) ,
      sum DECIMAL(10, 2) NOT NULL,
      purchase_date TIMESTAMP WITH TIME ZONE NOT NULL,
      cheque_type VARCHAR(50) NOT NULL
  );

