const pool = require("../config/db");

// GET - gets all services from db
exports.getAllServices = async (req, res, next) => {
  try {
    const [services] = await pool.execute("CALL get_all_services()");
    res.status(200).json(services[0]);
  } catch (err) {
    console.log(err);
    next(err);
  }
};

// gets all services partialy from db
exports.getAllServicesPartially = async (req, res, next) => {
  try {
    const [services] = await pool.execute("CALL get_all_services_partially()");
    res.status(200).json(services[0]);
  } catch (err) {
    console.log(err);
    next(err);
  }
};

// gets specific service from db
exports.getServiceById = async (req, res, next) => {
  const id = req.params.id;
  try {
    const [result, _] = await pool.execute("CALL get_service_by_id(?)", [id]);
    const service = result[0][0];
    res.status(200).json(service);
  } catch (err) {
    console.log(err);
    next(err);
  }
};

// POST - post new service to the db
exports.createNewService = async (req, res, next) => {
  const {
    service_name,
    organization_name,
    category,
    country,
    city,
    address,
    has_apartment,
    is_second_year_only,
    is_morning_service,
    is_evening_service,
    description_service,
    coordinator_name,
  } = req.body;
  try {
    const service = await pool.execute(
      "CALL add_new_service(?,?,?,?,?,?,?,?,?,?,?,?)",
      [
        service_name,
        organization_name,
        category,
        country,
        city,
        address,
        has_apartment,
        is_second_year_only,
        is_morning_service,
        is_evening_service,
        description_service,
        coordinator_name,
      ]
    );
    res.status(201).json({ service });
  } catch (err) {
    console.log(err);
    next(err);
  }
};

// PUT - put new update to a service from the db
exports.updateServiceById = async (req, res, next) => {
  const id = req.params.id;
  const {
    service_name,
    organization_name,
    category,
    country,
    city,
    address,
    has_apartment,
    is_second_year_only,
    is_morning_service,
    is_evening_service,
    description_service,
    coordinator_name,
  } = req.body;
  try {
    const service = await pool.execute(
      "CALL update_service(?,?,?,?,?,?,?,?,?,?,?,?,?)",
      [
        id,
        service_name,
        organization_name,
        category,
        country,
        city,
        address,
        has_apartment,
        is_second_year_only,
        is_morning_service,
        is_evening_service,
        description_service,
        coordinator_name,
      ]
    );
    res.status(201).json(service[0]);
  } catch (err) {
    console.log(err);
    next(err);
  }
};

// DELETE - delete service from db by id
exports.deleteServiceById = async (req, res, next) => {
  const id = req.params.id;
  try {
    const deleted = await pool.execute("CALL delete_service(?)", [id]);
    res.status(200).json(deleted[0]);
  } catch (err) {
    console.log(err);
    next(err);
  }
};
