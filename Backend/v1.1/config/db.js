const mysql = require("mysql2");
// const app = express();

const pool = mysql.createPool({
    host: process.env.DB_HOST,
    user: process.env.DB_USER,
    database: process.env.DB_NAME,
    password: process.env.DB_PASSWORD,
});

// // connect to a procedure that calls all the services in the db
// pool.query('CALL getAllServices()', (err,rows) => {
//     if(err) return console.log('error:' + err.message);
//     console.log('data recevied from db...');
//     console.log(rows);
// });

// app.get("/",(req,res) => {
//     pool.getConnection((err, connection) => {
//         if(err) return console.error('error: ' + err.message);
//         console.log(`connected as id ` + connection.threadId);
//         connection.query('CALL getAllServices()', (err,rows) => {
//             connection.release();
//             if(err) return console.log('error:' + err.message);
//             console.log('data recevied from db...');
//             console.log(rows);
//         });
//     });
//   });


/*
// Connecting to the db
connection.connect((err) => {
    if(err) {
        return console.error('error: ' + err.message);
    }
    console.log('Connected to the MySQL database server.');
});


// connect to a procedure that calls all the services in the db
connection.query('CALL getAllServices()', (err,rows) => {
    if(err) return console.log('error:' + err.message);
    console.log('data recevied from db...');
    console.log(rows);
});


// Close the connection to the db
connection.end((err) => {
    if (err) {
      return console.log('error:' + err.message);
    }
    console.log('Close the database connection.');
});
*/


module.exports = pool.promise();