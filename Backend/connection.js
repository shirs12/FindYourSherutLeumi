require("dotenv").config();

const PORT = process.env.PORT || 3000;

const server = require("./server");

const connect = require("http").createServer(server);
module.exports = { connect };

connect.listen(PORT, () => console.log(`Listening on port ${PORT}...`))