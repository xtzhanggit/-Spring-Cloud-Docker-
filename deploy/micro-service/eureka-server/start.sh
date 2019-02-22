docker build -t eureka-server:latest .
docker run --name eureka-server -it -d -p 8666:8666 eureka-server:latest --eureka-address=127.0.0.1
