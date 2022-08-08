const express = require("express");
const applicantController = require("../controllers/applicantController");
const router = express.Router();

// GET 
router.route("/").get(applicantController.getAllApplicants);

