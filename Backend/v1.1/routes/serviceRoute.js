const express = require("express");
const serviceController = require("../controllers/serviceController");
const router = express.Router();

// GET 
router.route("/").get(serviceController.getAllServices)
router.route("/:id").get(serviceController.getServiceById);

router.post("/addService", serviceController.createNewService);

module.exports = router;
