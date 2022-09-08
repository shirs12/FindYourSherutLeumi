const express = require("express");
const generalController = require("../controllers/generalController");
const router = express.Router();

router.route("/").get(generalController.getUserByEmail);
router.route("/").post(generalController.authenticateUser);


module.exports = router;