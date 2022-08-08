const express = require("express");
const applicantController = require("../controllers/applicantController");
const router = express.Router();

// GET 
router.route("/").get(applicantController.getAllApplicants);
router.route("/:id")
      .get(applicantController.getApplicantById) // GET by id
      .put(applicantController.updateApplicantById) // PUT by id
      .delete(applicantController.deleteApplicantById); // DELETE by id

// POST
router.post("/add", applicantController.createNewApplicant);



module.exports = router;