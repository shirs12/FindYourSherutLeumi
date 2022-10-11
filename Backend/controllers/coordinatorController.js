const pool = require("../config/db");
const bcrypt = require("bcrypt");

// GET - gets all coordinators from db
exports.getAllCoordinators = async (req, res, next) => {
  try {
    const [coordinators] = await pool.execute("CALL get_all_coordinators()");
    res.status(200).json(coordinators[0]);
  } catch (err) {
    console.log(err);
    next(err);
  }
};

// gets specific coordinator from db
exports.getCoordinatorById = async (req, res, next) => {
  const id = req.params.id;
  try {
    const [result, _] = await pool.execute(
      "CALL get_coordinator_by_id(?)",
      [id]
    );
    const coordinator = result[0][0];
    res.status(200).json(coordinator);
  } catch (err) {
    console.log(err);
    next(err);
  }
};

exports.getCoordinatorByEmail = async (req, res, next) => {
  const email = req.params.email;
  try {
    const [result, _] = await pool.execute(
      "CALL get_coordinator_by_email(?)",
      [email]
    );
    const coordinator = result[0][0];
    console.log(coordinator);
    res.status(200).json(coordinator);
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
    u_password,
    organization_name,
  } = req.body;
  try {
    const hash_password = await bcrypt.hash(u_password, 12);
    const coordinator = await pool.execute(
      "CALL add_new_coordinator(?,?,?,?,?,?)",
      [
        first_name,
        last_name,
        phone_number,
        email,
        hash_password,
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
    u_password,
    organization_name,
  } = req.body;
  try {
    let hash_password = u_password;
    console.log("hello: " + u_password.length);
    if(u_password.length != 60){
      hash_password = await bcrypt.hash(u_password, 12);
    }
    const coordinator = await pool.execute(
      "CALL update_coordinator(?,?,?,?,?,?,?)",
      [
        id,
        first_name,
        last_name,
        phone_number,
        email,
        hash_password,
        organization_name,
      ]
    );
    res.status(201).json(coordinator[0]);
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
    res.sendStatus(200);
  } catch (err) {
    console.log(err);
    next(err);
  }
};


