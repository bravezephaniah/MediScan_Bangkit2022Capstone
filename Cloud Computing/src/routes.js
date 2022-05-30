const {
	addUser,
	login,
	logout,
	updateProfile,
	changePassword,
	addHistory,
	getHistoryByUserId,
	deleteHistoryById,
} = require('./handler');

const routes = [
	{
		method: 'GET',
		path: '/test',
		handler: (request, h) => {
			return 'hello world';
		},
	},
	{
		method: 'POST',
		path: '/users',
		handler: addUser,
	},
	{
		method: 'POST',
		path: '/login',
		handler: login,
	},
	{
		method: 'DELETE',
		path: '/logout',
		handler: logout,
	},
	{
		method: 'PUT',
		path: '/users',
		handler: updateProfile,
	},
	{
		method: 'PUT',
		path: '/users/changePassword',
		handler: changePassword,
	},
	{
		method: 'POST',
		path: '/history',
		options: {
			payload: {
				maxBytes: 209715200,
				output: 'stream',
				parse: true,
				multipart: true,
			},
			handler: addHistory,
		},
	},
	{
		method: 'GET',
		path: '/history',
		handler: getHistoryByUserId,
	},
	{
		method: 'DELETE',
		path: '/history/{id}',
		handler: deleteHistoryById,
	},
];

module.exports = routes;
