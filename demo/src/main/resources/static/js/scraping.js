'use strict';

document.getElementById('scrapeForm').addEventListener('submit', function(event) {
    event.preventDefault();
  
    var url = this.querySelector('input[name="url"]').value;
  
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
        document.getElementById('url').value = data.url;
        document.getElementById('submitForm').submit();
      })
      .catch(error => console.error('Error:', error));
  });