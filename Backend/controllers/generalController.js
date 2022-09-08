const pool = require("../config/db");
const bcrypt = require("bcrypt");

// gets user from db by email
exports.getUserByEmail = async (req, res, next) => {
    const email = req.body.email;
    try {
      const [user, _] = await pool.execute(
        "CALL get_user_by_email(?)",
        [email]
      );
      res.status(200).json(user[0]);
    } catch (err) {
      console.log(err);
      next(err);
    }
  };

exports.authenticateUser = async (req, res, next) => {
  const email = req.body.email;
  const password = req.body.u_password;
  try {
    const [result, _] = await pool.execute("CALL get_user_by_email(?)", [email]);
    const user = result[0][0];
    console.log(user);
    console.log(user.u_password);
    if(user != undefined){
        const isvalid = bcrypt.compare(password, user.u_password);
        if(isvalid){
            res.status(200).json(user);
        }
        else res.status(204).json("Email and password does not match");
    }
    else res.status(206).json("Email does not exist");
  } catch (err) {
    console.log(err);
    next(err);
  }
};