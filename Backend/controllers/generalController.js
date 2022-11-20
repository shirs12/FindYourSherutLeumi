const pool = require("../config/db");
const bcrypt = require("bcrypt");
const transporter = require("../config/nodemailer");
const generator = require('generate-password');

// GET - gets user from db by email
exports.getUserByEmail = async (req, res, next) => {
    const email = req.params.email;
    try {
      const [user, _] = await pool.execute(
        "CALL get_user_by_email(?)",
        [email]
      );
      if (user[0][0] != undefined) res.status(200).json(user[0][0]);
      else res.status(400).json("Email does not exist");
    } catch (err) {
      console.log(err);
      next(err);
    }
  };

// authenticate the user details - for login
exports.authenticateUser = async (req, res, next) => {
  const email = req.body.email;
  const password = req.body.u_password;
  try {
    const [result, _] = await pool.execute("CALL get_user_by_email(?)", [email]);
    const user = result[0][0];
    console.log(user);
    if(user != undefined){
        const isvalid = await bcrypt.compare(password, user.u_password).then((valid) => {
          if(valid){
              res.status(200).json(user);
          } else {
              res.status(401).json("Email and password does not match"); 
              console.log("code 401");
          }
        })
      .catch((err) => next(err)); 
    }
    else res.status(400).json("Email does not exist");
  } catch (err) {
    console.log(err);
    next(err);
  }
};

// updates user's password - in case registered user forgot his password
exports.updateUserPassword = async (req, res, next) => {
  const email = req.params.email;
  const password = await generator.generate({
    length: 8,
    numbers: true
  });
  try {
    const hash_password = await bcrypt.hash(password, 12);
    const [user, _] = await pool.execute(
      "CALL update_user_password_by_email(?,?)",
      [email,hash_password]
    );
    if(user != undefined){
      this.forgotPassword(email, password);
      res.status(201).json(user[0]);
    } else res.status(400).json("Email does not exist");
  } catch (err) {
    console.log(err);
    next(err);
  }
};

// this method is generate a new password, and sends it to user's email
exports.forgotPassword = (email, password) => {
  const mailOptions = {
    from: process.env.MAIL_ADDRESS,
    to: email,
    subject: 'FindYourSherutLeumi: Reset Password',
    text: `Your new password is: ${password}\nYou can change this password in settings,
    after login the app with this password.\n\nFindYourSherutLeumi`
  };
  
  transporter.sendMail(mailOptions, function(error, info){
    if (error) {
      console.log(error);
    } else {
      console.log('Email sent: ' + info.response);
      res.status(250).json({'message': 'Email sent successfully'});
    }
  });
}



