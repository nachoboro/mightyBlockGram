INSERT INTO `accounts` (`account_id`,`name`,`pass`) VALUES (1,'Nacho','Nacho');
INSERT INTO `accounts` (`account_id`,`name`,`pass`) VALUES (2,'Nacho','Nacho');
INSERT INTO `accounts` (`account_id`,`name`,`pass`) VALUES (3,'foo','$2a$10$OYclzgRiB1SsvMaJ9XHcc.MRtxjqNkayPkH/c5y4t3s47GFwzwg8e');
INSERT INTO `accounts` (`account_id`,`name`,`pass`) VALUES (4,'testuser','$2a$10$VJ3Tkk8vAcSQv9Sv7CQLluuXSEfDXcH/F1jCZevc6b3DA92xpHwSC');

INSERT INTO `posts` (`post_id`,`description`,`image_path`,`upload_date`,`account_id`) VALUES (1,'description','/images/test','2021-08-20 00:00:00',1);
INSERT INTO `posts` (`post_id`,`description`,`image_path`,`upload_date`,`account_id`) VALUES (2,'description','/images/test','2021-08-20 00:00:00',1);
INSERT INTO `posts` (`post_id`,`description`,`image_path`,`upload_date`,`account_id`) VALUES (3,'description','/images/test','2021-08-20 00:00:00',1);
INSERT INTO `posts` (`post_id`,`description`,`image_path`,`upload_date`,`account_id`) VALUES (4,'desc2','/images/test2','2021-08-21 00:00:00',2);
INSERT INTO `posts` (`post_id`,`description`,`image_path`,`upload_date`,`account_id`) VALUES (5,'This is a photo of me and Kun','/Users/iborovsky/mightyblock/images/2_2021-08-21-21:15:23','2021-08-21 21:15:23',2);
INSERT INTO `posts` (`post_id`,`description`,`image_path`,`upload_date`,`account_id`) VALUES (6,'','/Users/iborovsky/mightyblock/images/2_2021-08-21-21:16:48','2021-08-21 21:16:48',2);
INSERT INTO `posts` (`post_id`,`description`,`image_path`,`upload_date`,`account_id`) VALUES (7,'','/Users/iborovsky/mightyblock/images/2_2021-08-22-00:31:57','2021-08-22 00:31:57',2);
INSERT INTO `posts` (`post_id`,`description`,`image_path`,`upload_date`,`account_id`) VALUES (8,'test photo with another token','/Users/iborovsky/mightyblock/images/2_2021-08-22-16:48:26','2021-08-22 16:48:26',2);
INSERT INTO `posts` (`post_id`,`description`,`image_path`,`upload_date`,`account_id`) VALUES (9,'test photo with another token','/Users/iborovsky/mightyblock/images/4_2021-08-22-16:49:23','2021-08-22 16:49:23',4);

INSERT INTO `likes` (`like_id`,`account_id`,`post_id`,`active`) VALUES (1,1,1,1);
INSERT INTO `likes` (`like_id`,`account_id`,`post_id`,`active`) VALUES (2,2,1,1);
INSERT INTO `likes` (`like_id`,`account_id`,`post_id`,`active`) VALUES (3,2,1,0);
INSERT INTO `likes` (`like_id`,`account_id`,`post_id`,`active`) VALUES (4,2,2,1);
INSERT INTO `likes` (`like_id`,`account_id`,`post_id`,`active`) VALUES (5,2,1,0);
INSERT INTO `likes` (`like_id`,`account_id`,`post_id`,`active`) VALUES (6,4,9,1);
INSERT INTO `likes` (`like_id`,`account_id`,`post_id`,`active`) VALUES (7,4,8,0);