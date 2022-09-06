const pool = require("../config/db");

// GET - gets all levels from db
exports.getAllLevels = async (req, res, next) => {
  try {
    const [levels] = await pool.execute("CALL get_all_levels()");
    res.status(200).json(levels[0]);
  } catch (err) {
    console.log(err);
    next(err);
  }
};

// gets specific level from db
exports.getLevelById = async (req, res, next) => {
  const id = req.params.id;
  try {
    const [level, _] = await pool.execute("CALL get_level_by_id(?)", [id]);
    res.status(200).json(level[0]);
  } catch (err) {
    console.log(err);
    next(err);
  }
};

// POST - post new level user to the db
exports.createNewLevelUser = async (req, res, next) => {
  const { level_name } = req.body;
  try {
    const level = await pool.execute("CALL add_new_level(?)", [level_name]);
    res.status(201).json({ level });
  } catch (err) {
    console.log(err);
    next(err);
  }
};

// PUT - put new update to a level user from the db
exports.updateLevelById = async (req, res, next) => {
  const id = req.params.id;
  const { level_name } = req.body;
  try {
    const level = await pool.execute("CALL update_level(?,?)", [
      id,
      level_name,
    ]);
    res.status(201).json(level[0]);
  } catch (err) {
    console.log(err);
    next(err);
  }
};

// DELETE - delete level from db by id
exports.deleteLevelById = async (req, res, next) => {
  const id = req.params.id;
  try {
    const deleted = await pool.execute("CALL delete_level(?)", [id]);
    res.status(200).json(deleted[0]);
  } catch (err) {
    console.log(err);
    next(err);
  }
};
