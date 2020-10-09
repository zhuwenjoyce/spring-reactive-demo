DROP DATABASE IF EXISTS t_post;
# CREATE DATABASE t_post;
CREATE TABLE t_post
(
    id          SERIAL,
    title       VARCHAR(100),
    content     VARCHAR(1000),
    create_date datetime DEFAULT now(),
    CONSTRAINT pk_post_id PRIMARY KEY (id)
) DEFAULT CHARSET 'utf8';