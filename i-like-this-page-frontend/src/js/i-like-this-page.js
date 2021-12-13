import '../css/i-like-this-page.css';
import likeIcon from '../images/like-cat-64x64.png';
import unlikeIcon from '../images/unlike-cat-64x64.png';

import { getLike, deleteLike, addLike } from './api.js';

let userLikeStatus = false;
const iltpContainer = document.getElementById('i-like-this-page');

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
  const iconImageEl = document.createElement('img');
  iconImageEl.setAttribute('id', 'iltp-content-icon-img');
  iconImageEl.width = 24;
  iconImageEl.height = 24;
  iconImageEl.src = unlikeIcon;

  iltpIconEl.appendChild(iconImageEl);
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
  userLikeStatus
    ? document.getElementById('iltp-box').classList.add('iltp-box-like')
    : document.getElementById('iltp-box').classList.remove('iltp-box-like');
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
