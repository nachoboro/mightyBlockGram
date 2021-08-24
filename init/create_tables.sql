CREATE TABLE accounts (
  account_id int(11) NOT NULL AUTO_INCREMENT,
  name varchar(45) NOT NULL,
  pass varchar(255) NOT NULL,
  PRIMARY KEY (account_id)
);

CREATE TABLE posts (
  post_id int(11) NOT NULL AUTO_INCREMENT,
  description varchar(255) DEFAULT NULL,
  image_path varchar(255) NOT NULL,
  upload_date datetime NOT NULL,
  account_id int(11) NOT NULL,
  PRIMARY KEY (post_id),
  FOREIGN KEY (account_id) REFERENCES accounts (account_id)
);

CREATE TABLE likes (
  like_id int(11) NOT NULL AUTO_INCREMENT,
  account_id int(11) NOT NULL,
  post_id int(11) NOT NULL,
  active tinyint(1) NOT NULL,
  PRIMARY KEY (like_id),
  FOREIGN KEY (account_id) REFERENCES accounts (account_id),
  FOREIGN KEY (post_id) REFERENCES posts (post_id)
);