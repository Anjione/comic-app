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
                         PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1413 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_uca1400_ai_ci;


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


ALTER TABLE comic.manga ADD manga_category varchar(100) NULL;
ALTER TABLE articles
    ADD FULLTEXT idx_ft_title_content (title, content);
