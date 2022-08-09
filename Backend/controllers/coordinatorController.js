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
