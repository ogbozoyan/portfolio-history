FROM nginx:latest

COPY spa /usr/share/nginx/html
COPY nginx.conf /etc/nginx/nginx.conf
COPY default.conf /etc/nginx/conf.d/default.conf
CMD nginx -g "daemon off;"
