const express = require("express");
const coordinatorController = require("../controllers/coordinatorController");
const router = express.Router();

// GET 
router.route("/").get(coordinatorController.getAllCoordinators);
router.route("/:id")
      .get(coordinatorController.getCoordinatorById) // GET by id
      .put(coordinatorController.updateCoordinatorById) // PUT by id


// POST
router.post("/add", coordinatorController.createNewCoordinator);

module.exports = router;

