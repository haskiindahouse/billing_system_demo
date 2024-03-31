FROM node:18
RUN apt update -y
RUN apt install xdg-utils -y
WORKDIR /react-vite-app
EXPOSE 3000
COPY package.json package-lock.json ./
RUN npm install --silent
COPY . ./
CMD ["npm", "run", "dev"]
