FROM mongo:6.0.4
USER mongodb


#ENV MONGO_INITDB_ROOT_USERNAME = "mongoadmin"
#ENV MONGO_INITDB_ROOT_PASSWORD = "mongopassword"
#ENV MONGO_INITDB_DATABASE = "open-messenger"

ADD users.js /docker-entrypoint-initdb.d/users.js

# docker build --tag mongodb-open-messenger:latest . 
# docker run -d -p 27017:27017 mongodb-open-messenger:latest
# mongosh mongodb://mongoadmin:secret@localhost:27017
# mongosh mongodb://mongoadmin:secret@localhost:27017

# docker run --name some-mongo -p 27017:27017 -e MONGO_INITDB_DATABASE=open-messenger -e MONGO_INITDB_ROOT_USERNAME=mongoadmin -e MONGO_INITDB_ROOT_PASSWORD=secret -d mongo:6.0.4
