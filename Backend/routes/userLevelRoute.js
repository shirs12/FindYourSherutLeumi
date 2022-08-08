const express = require("express");
const userLevelController = require("../controllers/userLevelController");
const router = express.Router();

// GET 
router.route("/").get(userLevelController.getAllLevels);
router.route("/:id")
      .get(userLevelController.getLevelById)
      .put(userLevelController.updateLevelById)
      .delete(userLevelController.deleteLevelById);

// POST
router.post("/add", userLevelController.createNewLevelUser);




module.exports = router;
