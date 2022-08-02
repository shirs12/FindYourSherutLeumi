const serviceService = require("../service/serviceService");

exports.getAllServices = async (req, res, next) => {
    try {
        const services = await serviceService.getServices();
        res.status(200).json({services});
    } catch(err) {
        console.log(err);
        next(err);
    }
};

exports.createNewService = async (req, res, next) => {
    res.send("create new post route");
};

exports.getServiceById = async (req, res, next) => {
    res.send("get post by id route");
};
