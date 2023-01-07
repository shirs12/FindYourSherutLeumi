const nodemailer = require('nodemailer');

// creates a transporter to allow easy email sendings
const transporter = nodemailer.createTransport({
    service: 'zoho',
    auth: {
      user: process.env.MAIL_ADDRESS,
      pass: process.env.MAIL_PASSWORD
    },
    tls: {
      rejectUnauthorized: false,
    },
});

module.exports = transporter;