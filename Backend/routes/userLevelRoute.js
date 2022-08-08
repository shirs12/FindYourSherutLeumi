const express = require("express");
const userLevelController = require("../controllers/userLevelController");
const router = express.Router();

// GET 
router.route("/").get(userLevelController.getAllLevels);
router.route("/:id")
      .get(userLevelController.getLevelById) // GET by id
      .put(userLevelController.updateLevelById) // PUT by id
      .delete(userLevelController.deleteLevelById); // DELETE by id

// POST
router.post("/add", userLevelController.createNewLevelUser);




module.exports = router;
