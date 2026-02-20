CREATE DATABASE `comic`

CREATE TABLE `chapter` (
                           `created_date` datetime(6) DEFAULT NULL,
                           `id` bigint(20) NOT NULL AUTO_INCREMENT,
                           `manga_id` bigint(20) DEFAULT NULL,
                           `modified_date` datetime(6) DEFAULT NULL,
                           `chapter_code` varchar(255) DEFAULT NULL,
                           `chapter_images` longtext DEFAULT NULL,
                           `chapter_name` varchar(255) DEFAULT NULL,
                           `chapter_number` DOUBLE DEFAULT NULL,
                           `created_by` varchar(255) DEFAULT NULL,
                           `modified_by` varchar(255) DEFAULT NULL,
                           PRIMARY KEY (`id`),
                           KEY `FKk2edktheu8eubpa5pjo0nwxj2` (`manga_id`),
                           CONSTRAINT `FKk2edktheu8eubpa5pjo0nwxj2` FOREIGN KEY (`manga_id`) REFERENCES `manga` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=132465 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_uca1400_ai_ci;

-- comic.manga definition

CREATE TABLE `manga` (
                         `rating` double DEFAULT NULL,
                         `created_date` datetime(6) DEFAULT NULL,
                         `id` bigint(20) NOT NULL AUTO_INCREMENT,
                         `modified_date` datetime(6) DEFAULT NULL,
                         `total_view` bigint(20) DEFAULT NULL,
                         `author` varchar(255) DEFAULT NULL,
                         `created_by` varchar(255) DEFAULT NULL,
                         `last_chapter` varchar(255) DEFAULT NULL,
                         `manga_avatar_url` varchar(255) DEFAULT NULL,
                         `manga_code` varchar(255) DEFAULT NULL,
                         `manga_name` varchar(255) DEFAULT NULL,
                         `modified_by` varchar(255) DEFAULT NULL,
                         `title` varchar(255) DEFAULT NULL,
                         `colored` TINYINT DEFAULT NULL,
                         PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1413 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_uca1400_ai_ci;
ALTER TABLE comic.manga ADD colored TINYINT NULL;


-- comic.upload_transaction definition

CREATE TABLE `upload_transaction` (
                                      `import_type` int(11) DEFAULT NULL,
                                      `staus` int(11) DEFAULT NULL,
                                      `created_date` datetime(6) DEFAULT NULL,
                                      `id` bigint(20) NOT NULL AUTO_INCREMENT,
                                      `modified_date` datetime(6) DEFAULT NULL,
                                      `created_by` varchar(255) DEFAULT NULL,
                                      `file_origin_name` varchar(255) DEFAULT NULL,
                                      `modified_by` varchar(255) DEFAULT NULL,
                                      `patch` varchar(255) DEFAULT NULL,
                                      PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_uca1400_ai_ci;


CREATE TABLE category (
                          id BIGINT AUTO_INCREMENT PRIMARY KEY,
                          name VARCHAR(100) ,
                          code VARCHAR(100) NOT NULL ,
                          slug VARCHAR(100) ,
                          description VARCHAR(255),
                          `modified_date` datetime(6) DEFAULT NULL,
                          `modified_by` varchar(255) DEFAULT NULL,
                          `created_by` varchar(255) DEFAULT NULL,
                          created_date DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6)
);

CREATE TABLE genre (
                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       name VARCHAR(100)  ,
                       code VARCHAR(100) NOT NULL ,
                       slug VARCHAR(100)  ,
                       `created_date` datetime(6) DEFAULT NULL,
                       `modified_date` datetime(6) DEFAULT NULL,
                       `modified_by` varchar(255) DEFAULT NULL,
                       `created_by` varchar(255) DEFAULT NULL,
                       description VARCHAR(255)
);

CREATE TABLE roles (
                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       name VARCHAR(255) UNIQUE NOT NULL,
                       created_date TIMESTAMP DEFAULT NOW(),
                       modified_date TIMESTAMP DEFAULT NOW(),
                       created_by VARCHAR(255),
                       modified_by VARCHAR(255)
);

CREATE TABLE users (
                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       username VARCHAR(255) UNIQUE NOT NULL,
                       password VARCHAR(255) NOT NULL,
                       email VARCHAR(255) NOT NULL,
                       enabled bool NOT NULL,
                       created_date TIMESTAMP DEFAULT NOW(),
                       modified_date TIMESTAMP DEFAULT NOW(),
                       created_by VARCHAR(255),
                       modified_by VARCHAR(255)
);

CREATE TABLE user_roles (
                            user_id INT NOT NULL,
                            role_id INT NOT NULL,
                            created_date TIMESTAMP DEFAULT NOW(),
                            modified_date TIMESTAMP DEFAULT NOW(),
                            created_by VARCHAR(255),
                            modified_by VARCHAR(255),
                            PRIMARY KEY (user_id, role_id)
);

ALTER TABLE manga
    ADD COLUMN category_id BIGINT AFTER id;

ALTER TABLE manga
    ADD CONSTRAINT fk_manga_category
        FOREIGN KEY (category_id) REFERENCES category(id);

CREATE TABLE manga_genre (
                             manga_id BIGINT NOT NULL,
                             genre_id BIGINT NOT NULL,
                             `created_date` datetime(6) DEFAULT NULL,
                             `modified_date` datetime(6) DEFAULT NULL,
                             `modified_by` varchar(255) DEFAULT NULL,
                             `created_by` varchar(255) DEFAULT NULL,
                             PRIMARY KEY (manga_id, genre_id),

                             CONSTRAINT fk_mg_manga
                                 FOREIGN KEY (manga_id) REFERENCES manga(id)
                                     ON DELETE CASCADE,

                             CONSTRAINT fk_mg_genre
                                 FOREIGN KEY (genre_id) REFERENCES genre(id)
                                     ON DELETE CASCADE
);


CREATE TABLE email_template
(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    created_date      timestamp default now(),
    modified_date     timestamp default now(),
    created_by        varchar(255),
    modified_by       varchar(255),
    template_name      varchar(255) not null,
    template_code      varchar(100) not null,
    template_key       varchar(200) not null,
    status            smallint,
    content           TEXT
);

CREATE TABLE email_log
(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    created_date      timestamp default now(),
    modified_date     timestamp default now(),
    created_by        varchar(255),
    modified_by       varchar(255),
    email_to          varchar(255) not null,
    sent_time         timestamp default now(),
    subject           varchar(1000) not null,
    content           TEXT,
    type              varchar(50)
);


CREATE TABLE sys_config(
                           id BIGINT AUTO_INCREMENT PRIMARY KEY,
                           created_date TIMESTAMP DEFAULT NOW(),
                           modified_date TIMESTAMP DEFAULT NOW(),
                           created_by VARCHAR(255),
                           modified_by VARCHAR(255),
                           code VARCHAR(50),
                           value VARCHAR(150)
);

CREATE INDEX idx_sys_config_id ON sys_config(id);
CREATE INDEX idx_sys_config_code ON sys_config(code);

create index idx_email_log_email_to on email_log (email_to);
create index idx_email_log_sent_time on email_log (sent_time);
create index idx_email_log_subject on email_log (subject);

create index idx_email_template_code on email_template (template_code);
create index idx_email_template_id on email_template (id);

ALTER TABLE email_template ADD COLUMN is_config bool;
ALTER TABLE email_template ADD COLUMN type varchar(50);

ALTER TABLE user_roles ADD CONSTRAINT fk_user_roles_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE;
ALTER TABLE user_roles ADD CONSTRAINT fk_user_roles_role FOREIGN KEY (role_id) REFERENCES roles(id) ON DELETE CASCADE;



-- Index for Roles (Primary Key is already indexed by default)
CREATE INDEX idx_roles_name ON roles(name);

CREATE INDEX idx_user_roles_user ON user_roles(user_id);
CREATE INDEX idx_user_roles_role ON user_roles(role_id);

CREATE INDEX idx_roles_created_date ON roles(created_date);
CREATE INDEX idx_roles_modified_date ON roles(modified_date);

CREATE INDEX idx_genre_code ON genre(code);
CREATE INDEX idx_mg_genre_id_manga_id ON manga_genre (genre_id, manga_id);
CREATE INDEX idx_mg_manga_id_genre_id ON manga_genre (manga_id, genre_id);
CREATE INDEX idx_manga_total_view ON manga(total_view);

DROP SEQUENCE IF EXISTS seq_manga_category;
CREATE SEQUENCE seq_manga_category
    START WITH 1
    INCREMENT BY 50
    CACHE 50;

DROP SEQUENCE IF EXISTS SEQ_GENRE;
CREATE SEQUENCE SEQ_GENRE
    START WITH 1
    INCREMENT BY 50
    CACHE 50;
INSERT INTO genre (code) VALUES
                             ('Action'),
                             ('Action Adventure'),
                             ('Adaptation'),
                             ('Adult'),
                             ('Adventure'),
                             ('Adventure Drama'),
                             ('Comedy'),
                             ('Cooking'),
                             ('Crime'),
                             ('Demon'),
                             ('Demons'),
                             ('Drama'),
                             ('Ecchi'),
                             ('Fantasy'),
                             ('Game'),
                             ('Gender Bender'),
                             ('Gore'),
                             ('Harem'),
                             ('Hero'),
                             ('Historical'),
                             ('Horror'),
                             ('Isekai'),
                             ('Josei'),
                             ('Kombay'),
                             ('Long Strip'),
                             ('Magic'),
                             ('Manhwa'),
                             ('Martial Arts'),
                             ('Mature'),
                             ('Mecha'),
                             ('Medical'),
                             ('Military'),
                             ('Monsters'),
                             ('Music'),
                             ('Musical'),
                             ('Mystery'),
                             ('Police'),
                             ('Project'),
                             ('Psychological'),
                             ('Regression'),
                             ('Reincarnation'),
                             ('Reincarnation Seinen'),
                             ('Returner'),
                             ('Romance'),
                             ('School'),
                             ('School life'),
                             ('Sci-fi'),
                             ('Seinen'),
                             ('Shotacon'),
                             ('Shoujo'),
                             ('Shoujo Ai'),
                             ('Shounen'),
                             ('Shounen Ai'),
                             ('Slice of Life'),
                             ('Sports'),
                             ('Super Power'),
                             ('Supernatural'),
                             ('Supranatural'),
                             ('Survival'),
                             ('System'),
                             ('Thriller'),
                             ('Time Travel'),
                             ('Tragedy'),
                             ('Vampire'),
                             ('Vampires'),
                             ('Villainess'),
                             ('Web Comic'),
                             ('Wuxia');

INSERT INTO category  (code) VALUES
                                 ('Novel'),
                                 ('Manga'),
                                 ('Manhwa'),
                                 ('Manhua'),
                                 ('Comic');
ALTER TABLE comic.manga ADD manga_category varchar(100) NULL;
ALTER TABLE articles
    ADD FULLTEXT idx_ft_title_content (title, content);

CREATE INDEX idx_chapter_manga_created
    ON chapter (manga_id, created_at DESC);
