const express = require("express");
const mysql = require("mysql");

const app = express();
const PORT = 3000;

// Create connection to the mysql db
let connection = mysql.createConnection({
    host: 'localhost',
    user: 'root',
    password: '',
    database: 'FindYourSherutLeumiDB',
});

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

/*
// const serviceTest = { ServiceName: 'name1', OrganizationName: 'organization1',
//                 Category: 'cat1', Country: 'country1', City: 'city1',
//                 StreetName: 'streetname1', StreetNum: 1, HasApartment: true,
//                 IsSecondYearOnly: false, IsMorningService: true, IsEveningService: false,
//                 DescriptionService: 'description1111111111111111111111',
//                 CoordinatorName: 'coordinator'};

// connect to a procedure that post a service into the db
connection.query(`CALL insertServiceInfo(?)`,(err, results, fields) => {
    if (err) {
      return console.error(err.message);
    }
    console.log(results[0]); // for checking
  });
*/
  


// Close the connection to the db
connection.end((err) => {
    if (err) {
      return console.log('error:' + err.message);
    }
    console.log('Close the database connection.');
});

app.listen(PORT, () => console.log(`Listening on port ${PORT}...`));





