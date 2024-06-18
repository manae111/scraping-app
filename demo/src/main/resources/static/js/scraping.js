'use strict';

document.getElementById('scrapeForm').addEventListener('submit', function(event) {
    event.preventDefault();//ページ遷移キャンセル
  
    var url = this.querySelector('input[name="inputUrl"]').value;
  
    fetch(this.action, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/x-www-form-urlencoded',
      },
      body: 'url=' + encodeURIComponent(url),
    })
      .then(response => response.json())
      .then(data => {
        document.getElementById('scrapedItemName').value = data.name;
        document.getElementById('scrapedItemPrice').value = data.price;
        document.getElementById('scrapedUrl').value = data.url;
        document.getElementById('submitForm').submit();
      })
      .catch(error => console.error('商品登録時にJavaScript上でエラーが発生しました:', error));
  });