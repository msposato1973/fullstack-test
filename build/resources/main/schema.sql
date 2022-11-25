DROP TABLE IF EXISTS person;
CREATE TABLE IF NOT EXISTS person
(
    id      BIGINT       NOT NULL AUTO_INCREMENT,
    created DATETIME     NOT NULL DEFAULT now(),
    name    VARCHAR(255) NOT NULL,
    age     INT(2)       DEFAULT NULL,

    PRIMARY KEY (id)
);
