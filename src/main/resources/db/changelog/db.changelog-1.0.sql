-- liquibase formatted sql

-- changeset initial-schema:1
CREATE TABLE users
(
    id         SERIAL PRIMARY KEY,
    first_name VARCHAR(50)  NOT NULL,
    last_name  VARCHAR(50)  NOT NULL,
    email      VARCHAR(100) NOT NULL UNIQUE,
    password   VARCHAR(255) NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

-- Add indexes
CREATE INDEX idx_users_email ON users (email);

-- changeset friends-table:2
CREATE TABLE user_friends
(
    user_id    INTEGER NOT NULL,
    friend_id  INTEGER NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (user_id, friend_id),
    CONSTRAINT fk_user_friends_user_id FOREIGN KEY (user_id)
        REFERENCES users (id) ON DELETE CASCADE,
    CONSTRAINT fk_user_friends_friend_id FOREIGN KEY (friend_id)
        REFERENCES users (id) ON DELETE CASCADE,
    CONSTRAINT check_different_users CHECK (user_id != friend_id)
);

-- Add indexes for faster friend lookups
CREATE INDEX idx_user_friends_user_id ON user_friends (user_id);
CREATE INDEX idx_user_friends_friend_id ON user_friends (friend_id);

-- changeset add-user-status:3
ALTER TABLE users
    ADD COLUMN status VARCHAR(20) DEFAULT 'ACTIVE' NOT NULL,
ADD COLUMN last_login_at TIMESTAMP WITH TIME ZONE;

-- changeset add-verification:4
ALTER TABLE users
    ADD COLUMN email_verified BOOLEAN DEFAULT FALSE,
    ADD COLUMN verification_token VARCHAR(100),
    ADD COLUMN verification_token_expiry TIMESTAMP WITH TIME ZONE;

-- Add index for verification token lookups
CREATE INDEX idx_users_verification_token ON users (verification_token);

-- changeset initial-data:5
INSERT INTO users (first_name, last_name, email, password, status,
                   email_verified)
VALUES ('Admin', 'User', 'admin@example.com',
        '$2a$10$XQf2Wr9FBkoFh/GvQyaJJ.3qz6Jl9NQ1qD.zD7wZePxVjNM1.eIGa',
        'ACTIVE', true);
