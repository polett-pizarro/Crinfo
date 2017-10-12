
DROP SCHEMA IF EXISTS crinfo;
CREATE SCHEMA crinfo;
USE crinfo;


CREATE TABLE commune (
  id_commune  SMALLINT UNSIGNED NOT NULL AUTO_INCREMENT,
  nombre_commune varchar(255) NOT NULL,
  last_update TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (id_commune)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE crime (
  id_crime  SMALLINT UNSIGNED NOT NULL AUTO_INCREMENT,
  crime_name varchar(255) NOT NULL,
  last_update TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (id_crime)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE wordBag (
  id_word  SMALLINT UNSIGNED NOT NULL AUTO_INCREMENT,
  id_crime SMALLINT UNSIGNED NOT NULL,
  word varchar(255) NOT NULL,
  last_update TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (id_word),
  FOREIGN KEY (id_crime) REFERENCES crime (id_crime)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE tweet (
  id_tweet SMALLINT UNSIGNED NOT NULL AUTO_INCREMENT,
  publication_date varchar(255) DEFAULT NULL,
  id_commune SMALLINT UNSIGNED DEFAULT NULL,
  id_crime SMALLINT UNSIGNED NOT NULL,
  last_update TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (id_tweet),
  FOREIGN KEY (id_crime) REFERENCES crime (id_crime),
  FOREIGN KEY (id_commune) REFERENCES commune (id_commune)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

