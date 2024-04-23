FROM node:18-alpine as build
WORKDIR /app
COPY package.json package-lock.json ./
RUN npm install
COPY . .
RUN npm run build

FROM nginx:alpine
COPY --from=build /app/dist/effective-todo /usr/share/nginx/html
EXPOSE 4200
CMD ["nginx", "-g", "daemon off;"]
