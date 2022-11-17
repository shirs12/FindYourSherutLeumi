const express = require("express");
const generalController = require("../controllers/generalController");
const router = express.Router();

router.route("/:email").get(generalController.getUserByEmail);
router.route("/").post(generalController.authenticateUser);
router.route("/forgotpassword").put(generalController.updateUserPassword);


module.exports = router;