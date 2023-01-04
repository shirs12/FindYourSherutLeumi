require("dotenv").config();

const express = require("express");
const app = express();
app.use(express.urlencoded({ extended: true }));

app.use(express.json());

app.use("/services", require("./routes/serviceRoute"));
app.use("/userLevels", require("./routes/userLevelRoute"));
app.use("/applicants", require("./routes/applicantRoute"));
app.use("/coordinators", require("./routes/coordinatorRoute"));

app.use("/login", require("./routes/generalRoute"));

app.use((err, req, res, next) => {
  console.log(err.stack);
  console.log(err.name);
  console.log(err.code);

  res.status(500).json({
    massage: "Somthing went wrong...",
  });
});

app.all("/*", (req, res) => {
  res.status(404)
  .json({massage : `${req.method} on ${req.originalUrl} not found`});
})


module.exports = app;