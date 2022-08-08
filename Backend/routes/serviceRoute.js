const express = require("express");
const serviceController = require("../controllers/serviceController");
const router = express.Router();

// GET 
router.route("/").get(serviceController.getAllServices)

// POST
router.post("/add", serviceController.createNewService);

router.route("/:id")
      .get(serviceController.getServiceById) // GET - by id
      .put(serviceController.updateServiceById) // PUT 
      .delete(serviceController.deleteServiceById); // DELETE


module.exports = router;
