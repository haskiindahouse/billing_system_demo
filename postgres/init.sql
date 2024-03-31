CREATE SCHEMA IF NOT EXISTS tmpk;

-- Create Clients Table
CREATE TABLE IF NOT EXISTS clients (
    id SERIAL PRIMARY KEY,
    balance NUMERIC NOT NULL,
    "limit" NUMERIC NOT NULL,
    status VARCHAR(255) NOT NULL,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    address VARCHAR(255) NOT NULL,
    phone_number VARCHAR(255) NOT NULL
);

-- Create Services Table
CREATE TABLE IF NOT EXISTS services (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    periodicity VARCHAR(255) NOT NULL,
    cost NUMERIC NOT NULL
);

-- Create Payments Table
CREATE TABLE IF NOT EXISTS payments (
    id SERIAL PRIMARY KEY,
    client_id INTEGER NOT NULL,
    amount NUMERIC NOT NULL,
    date DATE NOT NULL,
    method VARCHAR(255) NOT NULL,
    description VARCHAR(255),
    transaction_id VARCHAR(255),
    CONSTRAINT client_fk FOREIGN KEY (client_id) REFERENCES clients (id)
);

-- Создаем функцию, которая будет вызвана триггером
CREATE OR REPLACE FUNCTION update_client_balance()
RETURNS TRIGGER AS $$
BEGIN
    -- Обновляем баланс клиента, добавляя сумму платежа
    UPDATE clients
    SET balance = balance + NEW.amount
    WHERE id = NEW.client_id;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- Создаем триггер, который вызывает функцию после вставки в таблицу payments
CREATE TRIGGER update_balance_after_payment
AFTER INSERT ON payments
FOR EACH ROW EXECUTE FUNCTION update_client_balance();
