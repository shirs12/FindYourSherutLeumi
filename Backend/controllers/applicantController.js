const pool = require("../config/db");

// GET - gets all applicants from db
exports.getAllApplicants = async (req, res, next) => {
    console.log("hello");
    try {
      const [applicants] = await pool.execute("CALL get_all_applicants()");
      res.status(200).json({ applicants });
    } catch (err) {
      console.log(err);
      next(err);
    }
  };

