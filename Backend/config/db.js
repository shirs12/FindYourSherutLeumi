const mysql = require("mysql2");

// creating a connection with the db
const pool = mysql.createPool({
  host: process.env.DB_HOST,
  user: process.env.DB_USER,
  database: process.env.DB_NAME,
  password: "",
});

module.exports = pool.promise();
