```
docker run --name airplane-mysql -e MYSQL_DATABASE=airplane -e MYSQL_ROOT_PASSWORD=airplane -d -p 3306:3306 mysql:5.7 --character-set-server=utf8mb4 --collation-server=utf8mb4_general_ci
docker run --name airplane-redis -d -p 6379:6379 redis:4.0
```