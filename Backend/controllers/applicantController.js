const pool = require("../config/db");
const bcrypt = require("bcrypt");

// GET - gets all applicants from db
exports.getAllApplicants = async (req, res, next) => {
  try {
    const [applicants] = await pool.execute("CALL get_all_applicants()");
    res.status(200).json(applicants[0]);
  } catch (err) {
    console.log(err);
    next(err);
  }
};

// gets specific applicant from db
exports.getApplicantById = async (req, res, next) => {
  const id = req.params.id;
  try {
    const [result, _] = await pool.execute("CALL get_applicant_by_id(?)", [
      id,
    ]);
    const applicant = result[0][0];
    res.status(200).json(applicant);
    console.log(applicant[0]);
  } catch (err) {
    console.log(err);
    next(err);
  }
};

// gets specific applicant from db by email
exports.getApplicantByEmail = async (req, res, next) => {
  const email = req.params.email;
  try {
    const [result, _] = await pool.execute("CALL get_applicant_by_email(?)", [
      email,
    ]);
    const applicant = result[0][0];
    res.status(200).json(applicant);
  } catch (err) {
    console.log(err);
    next(err);
  }
};

// POST - post new applicant to the db
exports.createNewApplicant = async (req, res, next) => {
  const {
    first_name,
    last_name,
    phone_number,
    city,
    email,
    u_password,
  } = req.body;
  try {
      const hash_password = await bcrypt.hash(u_password, 12);
      const applicant = await pool.execute(
      "CALL add_new_applicant(?,?,?,?,?,?)",
      [first_name, last_name, phone_number, city, email, hash_password]
    );
    res.status(201).json({ applicant });
  } catch (err) {
    console.log(err);
    next(err);
  }
};

// PUT - put new update to a applicant from the db
exports.updateApplicantById = async (req, res, next) => {
  const id = req.params.id;
  const {
    first_name,
    last_name,
    phone_number,
    city,
    email,
    u_password,
  } = req.body;
  try {
    const hash_password = await bcrypt.hash(u_password, 12);
    const applicant = await pool.execute(
      "CALL update_applicant(?,?,?,?,?,?,?)",
      [id, first_name, last_name, phone_number, city, email, hash_password]
    );
    res.status(201).json(applicant[0]);
  } catch (err) {
    console.log(err);
    next(err);
  }
};

// DELETE - delete applicant from db by id
exports.deleteApplicantById = async (req, res, next) => {
  const id = req.params.id;
  try {
    const deleted = await pool.execute("CALL delete_applicant(?)", [id]);
    res.status(200).json(deleted[0]);
  } catch (err) {
    console.log(err);
    next(err);
  }
};
