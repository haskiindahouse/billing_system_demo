CREATE TABLE clients (
    id SERIAL PRIMARY KEY,
    balance DECIMAL(10, 2),
    limit DECIMAL(10, 2),
    status VARCHAR(255),
    type VARCHAR(255),
    name VARCHAR(255),
    email VARCHAR(255),
    dob DATE,
    address VARCHAR(255)
);

CREATE TABLE services (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255),
    periodicity VARCHAR(255),
    cost DECIMAL(10, 2)
);

CREATE TABLE payments (
    id SERIAL PRIMARY KEY,
    client_id INTEGER REFERENCES clients(id),
    type VARCHAR(255),
    amount DECIMAL(10, 2),
    date TIMESTAMP
);

CREATE TABLE expenses (
    id SERIAL PRIMARY KEY,
    client_id INTEGER REFERENCES clients(id),
    service_id INTEGER REFERENCES services(id),
    amount DECIMAL(10, 2),
    date TIMESTAMP
);

CREATE TABLE adjustments (
    id SERIAL PRIMARY KEY,
    client_id INTEGER REFERENCES clients(id),
    type VARCHAR(255),
    amount DECIMAL(10, 2),
    date TIMESTAMP
);
