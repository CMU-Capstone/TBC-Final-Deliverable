# base image
FROM node:12.2.0-alpine

# Add bash for debugging purposes
RUN apk update \
    && apk add --virtual build-dependencies \
        build-base \
        gcc \
        wget \
        git \
    && apk add \
        bash

# set working directory
WORKDIR /app

# add `/app/node_modules/.bin` to $PATH
ENV PATH /app/node_modules/.bin:$PATH

# install and cache app dependencies
COPY package.json /app/package.json
RUN git clone https://github.com/creativetimofficial/material-dashboard-react.git
RUN mv material-dashboard-react/* /app/
RUN rm -r /app/src/
COPY src/ /app/src/
RUN rm /app/package.json
COPY package.json /app/package.json

RUN npm install --silent
RUN npm audit fix --silent
RUN npm install react-scripts@3.0.1 -g --silent

# start app
CMD ["npm", "start"]