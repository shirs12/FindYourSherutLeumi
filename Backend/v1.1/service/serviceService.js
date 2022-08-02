const pool = require("../config/db");

exports.getServices = async () => {
    pool.getConnection((err, connection) => {
    //     if(err) return console.error('error: ' + err.message);
    //     console.log(`connected as id ` + connection.threadId); // not necessary
    //     connection.query('CALL getAllServices()', (err,rows) => {
    //         connection.release();
    //         if(err) return console.log('error:' + err.message);
    //         console.log('data recevied from db...');
    //         console.log(rows);
    //         return rows;
    //     });
    console.log("hello");
    });

    // pool.execute('CALL getAllServices()', (err,rows) => {
    //     if(err) return console.log('error:' + err.message);
    //     console.log("111");
    //     return rows;
    // });
}


