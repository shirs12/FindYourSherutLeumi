const serviceService = require("../service/serviceService");

// gets all services from db
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

// post new service to the db
exports.createNewService = async (req, res, next) => {
    const { service_name,organization_name,category,country,
        city,street_name,street_num,has_apartment,is_second_year_only,
        is_morning_service,is_evening_service,description_service,
        coordinator_name } = req.body;
    try{
        const service = await serviceService.createService(
            service_name,organization_name,category,country,
            city,street_name,street_num,has_apartment,is_second_year_only,
            is_morning_service,is_evening_service,description_service,
            coordinator_name
        );
        res.status(200).json({service});   
    } catch(err) {
        console.log(err);
        next(err);
        }
};

// put new update to a service from the db
exports.updateServiceById = async (req, res, next) => {
    // TODO
}

