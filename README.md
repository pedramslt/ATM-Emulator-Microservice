# ATM-Emulator-Microservice


to add mysql database and redis to project please install docker and run following commands :

mysql :

sudo docker run --restart=always -v /home/emdad/mysql/data:/var/lib/mysql --network aa  -p 3307:3306 -d --name mysql -e MYSQL_USER="aa" -e MYSQL_PASSWORD="aa" -e MYSQL_ROOT_PASSWORD="root" mysql:latest




*****redis : (installing redis is necessary to use the application)**********

docker run --restart=always -p 6379:6379 -v /root/redis:/data --name redis-ms -d redis:alpine




phpmyadmin : (phpmyadmin is mysql ui) 

sudo docker run --restart=always --network aa --name myadmin -d -e PMA_HOST="mysql:3306" -p 8081:80 phpmyadmin/phpmyadmin:latest


