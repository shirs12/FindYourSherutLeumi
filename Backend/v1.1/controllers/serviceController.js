const serviceService = require("../service/serviceService");

// gets all services fron db
exports.getAllServices = async (req, res, next) => {
    try {
        const services = await serviceService.getServices();
        res.status(200).json({services});
    } catch(err) {
        console.log(err);
        next(err);
    }
};

// gets specific service from db
exports.getServiceById = async (req, res, next) => {
    const id = req.params.id;
    try{
        const service = await serviceService.getServiceId(id);
        res.status(200).json({service});   
    } catch(err) {
        console.log(err);
        next(err);
        }
};

// TODO
exports.createNewService = async (req, res, next) => {
    res.send("create new service route");
};

