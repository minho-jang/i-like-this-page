import '../css/i-like-this-page.css';
import likeIcon from '../images/like-cat-64x64.png';
import unlikeIcon from '../images/unlike-cat-64x64.png';

import { getLike, deleteLike, addLike } from './api.js';

const renderButton = (isError = false) => {
  const iltpBoxEl = document.createElement('div');
  iltpBoxEl.setAttribute('id', 'iltp-box');
  iltpBoxEl.classList.add('iltp-box');

  if (isError) renderErrorButton(iltpBoxEl);
  else renderLikeButton(iltpBoxEl);

  removeLikeButtonIfExisted();
  iltpContainer.appendChild(iltpBoxEl);
};

const renderErrorButton = (iltpBoxEl) => {
  iltpBoxEl.classList.add('iltp-box-error');

  const errorMessage = 'ERR :(';
  const errorMessageEl = document.createElement('span');
  errorMessageEl.setAttribute('id', 'iltp-error-message');
  errorMessageEl.innerText = errorMessage;
  iltpBoxEl.append(errorMessageEl);
};

const renderLikeButton = (iltpBoxEl) => {
  const iltpIconEl = document.createElement('span');
  iltpIconEl.setAttribute('id', 'iltp-content-icon');
  const heartImageEl = document.createElement('img');
  heartImageEl.setAttribute('id', 'iltp-content-icon-img');
  heartImageEl.width = 24;
  heartImageEl.height = 24;
  heartImageEl.src = unlikeIcon;

  iltpIconEl.appendChild(heartImageEl);
  iltpBoxEl.appendChild(iltpIconEl);

  const iltpNumberEl = document.createElement('span');
  iltpNumberEl.setAttribute('id', 'iltp-content-number');
  iltpNumberEl.classList.add('iltp-content-number');
  iltpNumberEl.innerText = '0'; // default value
  iltpBoxEl.appendChild(iltpNumberEl);
};

const removeLikeButtonIfExisted = () => {
  if (iltpContainer.childNodes.length > 0) {
    iltpContainer.removeChild(iltpContainer.childNodes[0]);
  }
};

let userLikeStatus = false;
const iltpContainer = document.getElementById('i-like-this-page');

iltpContainer.addEventListener('click', () => {
  const isNotError = document.getElementById('iltp-content-number');
  if (isNotError) {
    const urlWithoutProtocol = window.location.host + window.location.pathname;

    if (userLikeStatus) {
      cancelLikeAndRender(urlWithoutProtocol);
    } else {
      addLikeAndRender(urlWithoutProtocol);
    }
  }
});

const getLikeAndRender = async (currentLocation) => {
  try {
    const apiResult = await getLike(currentLocation);
    if (apiResult.error) {
      handleError(apiResult.error);
      return;
    }
    renderApiResult(apiResult);
  } catch (err) {
    handleError(err);
  }
};

const addLikeAndRender = async (currentLocation) => {
  try {
    const apiResult = await addLike(currentLocation);
    if (apiResult.error) {
      handleError(apiResult.error);
      return;
    }
    renderApiResult(apiResult);
  } catch (err) {
    handleError(err);
  }
};

const cancelLikeAndRender = async (currentLocation) => {
  try {
    const apiResult = await deleteLike(currentLocation);
    if (apiResult.error) {
      handleError(apiResult.error);
      return;
    }
    renderApiResult(apiResult);
  } catch (err) {
    handleError(err);
  }
};

const renderApiResult = (apiResult) => {
  userLikeStatus = apiResult.response.likeStatus;
  document.getElementById('iltp-content-icon-img').src = userLikeStatus
    ? likeIcon
    : unlikeIcon;
  document.getElementById('iltp-content-number').innerText = numberWithComma(
    apiResult.response.likeCount
  );
};

const numberWithComma = (num) => {
  return num.toLocaleString();
};

const handleError = (err) => {
  console.error(err);
  renderButton(true);
};

renderButton();

window.addEventListener('load', () => {
  const currentLocation = window.location.host + window.location.pathname;
  getLikeAndRender(currentLocation);
});
