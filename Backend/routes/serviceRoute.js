const express = require("express");
const serviceController = require("../controllers/serviceController");
const router = express.Router();

// GET 
router.route("/").get(serviceController.getAllServices)
router.route("/:id")
      .get(serviceController.getServiceById)
      .put(serviceController.updateServiceById); // PUT 

// POST
router.post("/add", serviceController.createNewService);



// DELETE

module.exports = router;
