var mysql = require('mysql');
require('dotenv').config();

let con = mysql.createConnection({
	host: process.env.DB_HOST,
	user: process.env.DB_USER,
	password: process.env.DB_PASSWORD,
	database: process.env.DB_DATABASE,
});

con.connect(function (err) {
	if (err) throw err;
	console.log('Connected to Database!');
});

const insertUser = (username, email, password) => {
	con.query(
		'INSERT INTO users (username, email, password) VALUES (?,?,?)',
		[username, email, password],
		function (err, result) {
			if (err) throw err;
			return result;
		}
	);
};

const selectUser = () => {
	return new Promise((resolve, reject) => {
		con.query('SELECT * FROM users', (err, result) => {
			if (err) reject(err);
			resolve(result);
		});
	});
}

const insertHistory = (user_id, result, img_url, createdAt) => {
	con.query(
		'INSERT INTO history (user_id, result, img_url, createdAt) VALUES (?,?,?,?)',
		[user_id, result, img_url, createdAt],
		function (err, result) {
			if (err) throw err;
			return result;
		}
	);
};

const selectHistory = () => {
	return new Promise((resolve, reject) => {
		con.query('SELECT * FROM history', (err, result) => {
			if (err) reject(err);
			resolve(result);
		});
	});
}

module.exports = {
	insertUser,
	selectUser,
	insertHistory,
	selectHistory,
};
