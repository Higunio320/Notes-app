FROM node:20-alpine AS build

WORKDIR /app

COPY package*.json ./

RUN npm install --legacy-peer-deps

COPY . .

RUN npm run build:prod

FROM nginx:stable-alpine3.17-slim AS prod

COPY --from=build /app/dist/frontend /usr/share/nginx/html
COPY ./nginx.conf /etc/nginx/conf.d/default.conf

EXPOSE 80
