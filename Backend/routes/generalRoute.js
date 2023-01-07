const express = require("express");
const generalController = require("../controllers/generalController");
const router = express.Router();

router.route("/:email").get(generalController.getUserByEmail); // GET
router.route("/").post(generalController.authenticateUser); // POST
router.route("/forgotpassword/:email").put(generalController.updateUserPassword); // PUT


module.exports = router;