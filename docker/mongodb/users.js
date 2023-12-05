
db = db.getSiblingDB('open-messenger');

db.user.createIndex( { "userName": 1 }, { unique: true } )

users = [{
    "userName": "daniel.silva",
    "firstName": "Daniel",
    "lastName": "Silva",
    "password": "$2a$10$GbV.MylNMwTwsJxynINW/.ol13IvYgQi.N4lWAICpP3f..LuGCmPi",
    "email": "daniel.silva@company.com",
    "roles": [
      "ROLE_USER"
    ],
    "_class": "net.bean.java.open.messenger.model.User"
  },{
    "userName": "dominica.rosatti",
    "firstName": "Dominica",
    "lastName": "Rosatti",
    "password": "$2a$10$gqAG/DJEJ4d2oHkUoaQt5uI0K.dXthZA/GMWKJR/VJsFfNmactOJK",
    "email": "dominica.rosatti@company.com",
    "roles": [
      "ROLE_USER"
    ],
    "_class": "net.bean.java.open.messenger.model.User"
  },{
    "userName": "christopher.wolf",
    "firstName": "Christopher",
    "lastName": "Wolf",
    "password": "$2a$10$l/aYvieIZ1uXn1fUGfrILOqg5SDKl3KOGIid3VyPzALK6Ao5yKY2W",
    "email": "christopher.wolf@company.com",
    "roles": [
      "ROLE_USER"
    ],
    "_class": "net.bean.java.open.messenger.model.User"
  },{
    "userName": "claudia.wiliams",
    "firstName": "Claudia",
    "lastName": "Wiliams",
    "password": "$2a$10$hLlnyj/iCF.kqwzrHIy6ruP8tqc9HSoKdfMCmXLb4JZaS4acliZ/G",
    "email": "claudia.wiliams@company.com",
    "roles": [
      "ROLE_USER"
    ],
    "_class": "net.bean.java.open.messenger.model.User"
  },{
    "userName": "monica.rosatti",
    "firstName": "Monica",
    "lastName": "Rosatti",
    "password": "$2a$10$ur21hiGKLmnfZYUvG1/h4.nvbR9SO9wK1hW8zL0lB.wnEgPyqas2q",
    "email": "monica.rosatti@company.com",
    "roles": [
      "ROLE_USER"
    ],
    "_class": "net.bean.java.open.messenger.model.User"
  },{
    "userName": "djnjgiugnj",
    "firstName": "Tomasz",
    "lastName": "Badon",
    "password": "$2a$10$R5yF4X0OX.JiGvDlb8kKg./1ZDvrufhBWJHdNpHhe4AfEt8LEIf1C",
    "email": "tomasz.badon@hotmail.com",
    "roles": [
      "ROLE_USER"
    ],
    "_class": "net.bean.java.open.messenger.model.User"
  }];

db.user.insertMany(users);

db.createUser(
  {
    user: "open-messenger-user",
    pwd: "secret",
    roles: [
       { role: "readWrite", db: "open-messenger" }
    ]
  }
);
