# MightyBlockGram

## Description
This is an Instagram-like application that allows creating users,
logging in, creating and getting all posts and liking/unliking them.

---

## Setting up the database
This project uses a mysql database with 3 tables, you should create it yourself in your local mysql under the name of MightyBlockGram on port 3306, then,  you can check the
table creation and insert some test records with the files in https://github.com/nachoboro/mightyBlockGram/tree/master/src/test/resources/sql

---

## Understanding the endpoints
### Creating an account
The first step is creating an account, for that you can use the account creation ednpoint

```
/accounts
```
This is a POST method and only requires a body with a username and password
(Note: both of this **cannot** be null or empty).

```
{
    "username":"testuser",
    "password":"testpass"
}
```

### Logging in
Once you have created your account the next step is logging in, use the next endpoint:

```
/login
```

This is also a POST method, and its body is equal to the account creation body
```
{
    "username":"testuser",
    "password":"testpass"
}
```

This endpoint will respond with a json web token that you will need to send as a header to validate you are logged in

```
{
    "token": "token"
}
``` 
### Authorizing subsequent requests
From now on, every request should contain an authentication header like the following:
```
'Auth-Token': 'token'
```
with the token being the token you got as the login response, else the application will return forbidden for each of the requests below.


### Getting all posts
If you want to browse through all posts then you should use the next GET endpoint:

```
/posts/list?offset={offset}&limit={limit}
```

These GET will return a list of posts, with their respective description, account id, upload date and the path to where the image is saved, ordered by upload date from newest to oldest and their current likes.
Offset and limit parameters are used to see which page we are currently in, if you want to get the first 6 posts, offset should be 0 and limit 6.
If you are interested in the second page of 6 posts, offset should be 6 and limit 6 as well.

### Creating a post
To create a post, use the next POST endpoint: 

```
/posts
```

This POST request creates a post for an account, its body should be a type of form-data, including
the post's image, the description as text and the id of the account. It will respond with the created post
with its description, account id, image path, likes and upload date

```
{
    "description": "description",
    "likes": 0,
    "image_path": "path",
    "upload_date": "2021-08-23-15:09:54",
    "account_id": 4
}
```

### Liking or unliking a post
In order to like or unlike an already liked post, you should use the next PUT endpoint:

```
/likes?accountId={account_id}&postId={post_id}
```
The account and post id passed should both exist already, this will like a post if the account hasn't liked the post already
and it will toggle the "like" status from the post if it is already liked (liking an already liked post will unlike it and liking an unliked post will relike it);