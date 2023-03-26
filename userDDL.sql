CREATE TABLE users
(
    username VARCHAR(50)  NOT NULL PRIMARY KEY,
    password VARCHAR(500) NOT NULL,
    enabled  tinyint(1) NOT NULL
);

CREATE TABLE authorities
(
    username  VARCHAR(50) NOT NULL,
    authority VARCHAR(50) NOT NULL,
    CONSTRAINT ix_auth_username
        UNIQUE (username, authority),
    CONSTRAINT fk_authorities_users
        FOREIGN KEY (username) REFERENCES users (username)
);
