const express = require('express');
const fs = require('fs');

const convoConfigPath = '/root/ConvoConfig.json';
var server = express();

server.get('/current', function (request, response) {
    let service = request.query.service;
    if(service == 'Convo'){
	let err, data;
        err, data = fs.readFileSync(convoConfigPath);
	if (err) throw err;
        response.send(data.toString());
          
    }
        
});

server.listen(80);
console.log('启动配置服务器于端口80');
