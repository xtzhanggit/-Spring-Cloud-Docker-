docker run -it -d --name app-mysql -e MYSQL_ROOT_PASSWORD=acehjl -v ~/house-project/apps/mysql/data-backup:/var/lib/mysql -v ~/house-project/apps/mysql/sql:/sql -p 3306:3306 mysql:latest 
