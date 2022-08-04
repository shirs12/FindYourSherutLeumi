const express = require("express");
const serviceController = require("../controllers/serviceController");
const router = express.Router();

// GET 
router.route("/").get(serviceController.getAllServices)
router.route("/:id")
      .get(serviceController.getServiceById)
    //   .put(serviceController); // TODO

// POST
router.post("/add", serviceController.createNewService);

// PUT

// DELETE

module.exports = router;
