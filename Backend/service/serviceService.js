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

// post new service to the db
exports.createService = async (
    service_name,organization_name,category,country,
    city,street_name,street_num,has_apartment,is_second_year_only,
    is_morning_service,is_evening_service,description_service,
    coordinator_name) => {
    const newService = await pool.execute(
        'CALL add_new_service(?,?,?,?,?,?,?,?,?,?,?,?,?)',[
            service_name,organization_name,category,country,
            city,street_name,street_num,has_apartment,is_second_year_only,
            is_morning_service,is_evening_service,description_service,
            coordinator_name
        ]);
    return newService;
};

// put new update to a service from the db
exports.updateService = async (id) => {
    // TODO
};


