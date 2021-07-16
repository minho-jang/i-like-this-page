const API_BASEADDRESS = 'http://localhost:8080';
const RESTAPI_BASEADDRESS = `${API_BASEADDRESS}/api/v1`;
const iltp_div = document.getElementById('i-love-this-page');

// first loading
window.addEventListener('load', async function () {
  try {
    // TODO 이것도 그냥 REST로 받아서 현재 얘가 클릭된 상태인지 아닌지를 같이 받아야 할듯
    const response = await this.fetch(API_BASEADDRESS);
    const data = await response.text();
    iltp_div.innerHTML = data;
  } catch (err) {
    // TODO error handling
    console.error(err);
  }
});

// 'like' click event
iltp_div.addEventListener('click', async function () {
  console.log('LIKE CLICK!!');
  try {
    const response = await fetch(`${RESTAPI_BASEADDRESS}/like`);
    const data = await response.json();
    console.log(data);
    const iltp_number = document.getElementById('iltp-content-number');
    iltp_number.innerText = Number.parseInt(iltp_number.innerText) + 1;
  } catch (err) {
    // TODO error handling
    console.error(err);
  }
});
