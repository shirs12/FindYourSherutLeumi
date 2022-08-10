require("dotenv").config();

const express = require("express");
const app = express();
const PORT = process.env.PORT || 3000;

app.use(express.json());

app.use("/services", require("./routes/serviceRoute"));
app.use("/userLevels", require("./routes/userLevelRoute"));
app.use("/applicants", require("./routes/applicantRoute"));
app.use("/coordinator", require("./routes/coordinatorRoute"));

app.use((err, req, res, next) => {
  console.log(err.stack);
  console.log(err.name);
  console.log(err.code);

  res.status(500).json({
    massage: "Somthing went wrong...",
  });
});

app.listen(PORT, () => console.log(`Listening on port ${PORT}...`));
