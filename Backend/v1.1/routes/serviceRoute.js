const express = require("express");
const serviceController = require('../controllers/serviceController');
const router = express.Router();

router.route("/")
    .get(serviceController.getAllServices)
    .post(serviceController.createNewService);

router.route("/:id").get(serviceController.getServiceById);

module.exports = router;
