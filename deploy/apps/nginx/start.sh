docker run --name app-nginx -it -d -v ~/house-project/apps/nginx/default.conf:/etc/nginx/conf.d/default.conf -v ~/house-project/apps/nginx/images:/images -p 8087:80 nginx:1.13
