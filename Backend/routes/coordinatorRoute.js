const express = require("express");
const coordinatorController = require("../controllers/coordinatorController");
const router = express.Router();

// GET 
router.route("/").get(coordinatorController.getAllCoordinators);

module.exports = router;

