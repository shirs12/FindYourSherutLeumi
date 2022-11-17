const nodemailer = require('nodemailer');

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