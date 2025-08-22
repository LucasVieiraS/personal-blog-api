CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_users_email ON users (email);
CREATE INDEX idx_users_active ON users (active);
CREATE INDEX idx_users_created_at ON users (created_at);

INSERT INTO users (email, first_name, last_name, active) VALUES
('john.doe@example.com', 'John', 'Doe', true),
('jane.smith@example.com', 'Jane', 'Smith', true),
('bob.johnson@example.com', 'Bob', 'Johnson', false);
