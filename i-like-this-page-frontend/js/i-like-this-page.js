const DOMAIN = 'http://localhost:8080';
const RESTAPI_BASEADDRESS = `${DOMAIN}/api/v1`;

const renderLikeButtonTo = (container) => {
  const iltpBox = document.createElement('span');
  iltpBox.setAttribute('id', 'iltp-box');

  const iltpBoxStyle = `
    border-radius: 15%;
    background-color: rgb(255, 202, 202);
    font-family: 'Fira Sans', sans-serif;
    padding: 0.5rem 1rem;`;
  iltpBox.setAttribute('style', iltpBoxStyle);

  const iltpIcon = document.createElement('span');
  iltpIcon.setAttribute('id', 'iltp-content-icon');
  iltpIcon.innerText = '💕';
  iltpBox.appendChild(iltpIcon);

  const iltpContent = document.createElement('span');
  iltpContent.setAttribute('id', 'iltp-content-number');
  iltpContent.setAttribute('style', 'margin-left: .4rem;');
  iltpContent.innerText = 0;
  iltpBox.appendChild(iltpContent);

  container.appendChild(iltpBox);

  const cssLinkElement = document.createElement("link");
  cssLinkElement.setAttribute('href', 'css/i-like-this-page.css');
  cssLinkElement.setAttribute('type', 'text/css');
  cssLinkElement.setAttribute('rel', 'stylesheet');
  document.head.appendChild(cssLinkElement);
};

const getLikeNumber = () => {
  // TODO: url에서 "http://" 빼고 파라미터 추가하기
  fetch(`${RESTAPI_BASEADDRESS}/like`)
    .then((response) => response.json())
    .then((data) => {
      console.log(data);
      document.getElementById('iltp-content-number').innerHTML = 154;
    })
    .catch((err) => {
      // TODO: error handling
      console.error(err);
    });
};

const iltpContainer = document.getElementById('i-like-this-page');
const iltpContainerStyle = `
  cursor: pointer;
`;
iltpContainer.setAttribute('style', iltpContainerStyle);

// 'like' click event
iltpContainer.addEventListener('click', function () {
  console.log('LIKE CLICK!!');
  fetch(`${RESTAPI_BASEADDRESS}/like`, { method: 'POST' })
    .then((response) => response.json())
    .then((data) => {
      console.log(data);
      const iltpNumber = document.getElementById('iltp-content-number');
      iltpNumber.innerText = Number.parseInt(iltpNumber.innerText) + 1;
    })
    .catch((err) => {
      // TODO: error handling
      console.error(err);
    });
});

renderLikeButtonTo(iltpContainer);

window.addEventListener('load', () => {
  getLikeNumber();
});