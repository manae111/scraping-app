const express = require('express');
const bodyParser = require('body-parser');
const axios = require('axios');
const cheerio = require('cheerio');
const cors = require('cors');

const app = express();
app.use(cors());
app.use(bodyParser.urlencoded({ extended: false }));

app.post('/scrape', (req, res) => {
  const url = req.body.url;

  axios.get(url)
    .then(response => {
      const html = response.data;
      const $ = cheerio.load(html);
      const name = $('#productTitle').text(); // Assume the name is in an element with class 'name'
      const price = $('#corePriceDisplay_desktop_feature_div .a-price-whole:first').text(); // Assume the price is in an element with ID 'productPrice'
      res.send({name: name, price: price, url: url}); // Send the scraped name and price as the response
    })
    .catch(error => {
      console.error('Error:', error);
      res.status(500).send(error);
    });
});

app.get('', (req, res) => {
  res.send('helloJava');
});

app.listen(3000, () => console.log('Server is running on port 3000'));

var msg = 'Hello World';
console.log(msg);

// // モジュール準備
// const http = require("http");

// // サーバー作成
// const server = http.createServer((_, res) => {
//   // HTMLファイルを返却する準備
//   res.writeHead(200, {
//     "Content-Type": "text/html; charset=utf-8",
//   });
//   // HTMLファイルの内容
//   res.end("<h1>サーバーはctrl + cで停止できる</h1>");
// });

// // サーバー起動
// const port = 3000;
// server.listen(port, function () {
//   console.log("Node.js Server Started:" + port);
// });