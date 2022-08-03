const pool = require("../config/db");

// calls a procedure that gets all the services in the db
exports.getServices = async () => {
    const [rows] = await pool.execute('CALL get_all_services()');
    return rows;
};

// calls a procedure that gets specific service from db
exports.getServiceId = async (id) => {
    const [row, _] = await pool.execute('CALL get_service_by_id(?)', [id]);
    return row[0];
};


