const pool = require("../config/db");

// GET - gets all coordinators from db
exports.getAllCoordinators = async (req, res, next) => {
  try {
    const [coordinators] = await pool.execute("CALL get_all_coordinators()");
    res.status(200).json({ coordinators });
  } catch (err) {
    console.log(err);
    next(err);
  }
};

// gets specific coordinator from db
exports.getCoordinatorById = async (req, res, next) => {
  const id = req.params.id;
  try {
    const [coordinator, _] = await pool.execute(
      "CALL get_coordinator_by_id(?)",
      [id]
    );
    res.status(200).json({ coordinator });
  } catch (err) {
    console.log(err);
    next(err);
  }
};

// POST - post new coordinator to the db
exports.createNewCoordinator = async (req, res, next) => {
  const {
    first_name,
    last_name,
    phone_number,
    email,
    coordinator_password,
    organization_name,
  } = req.body;
  try {
    const coordinator = await pool.execute(
      "CALL add_new_coordinator(?,?,?,?,?,?)",
      [
        first_name,
        last_name,
        phone_number,
        email,
        coordinator_password,
        organization_name,
      ]
    );
    res.status(201).json({ coordinator });
  } catch (err) {
    console.log(err);
    next(err);
  }
};

// PUT - put new update to a coordinator from the db
exports.updateCoordinatorById = async (req, res, next) => {
  const id = req.params.id;
  const {
    first_name,
    last_name,
    phone_number,
    email,
    coordinator_password,
    organization_name,
  } = req.body;
  try {
    const coordinator = await pool.execute(
      "CALL update_coordinator(?,?,?,?,?,?,?)",
      [
        id,
        first_name,
        last_name,
        phone_number,
        email,
        coordinator_password,
        organization_name,
      ]
    );
    res.status(201).json({ coordinator });
  } catch (err) {
    console.log(err);
    next(err);
  }
};

// DELETE - delete coordinator from db by id
exports.deleteCoordinatorById = async (req, res, next) => {
  const id = req.params.id;
  try {
    const deleted = await pool.execute("CALL delete_coordinator(?)", [id]);
    res.status(200).json({ deleted });
  } catch (err) {
    console.log(err);
    next(err);
  }
};
