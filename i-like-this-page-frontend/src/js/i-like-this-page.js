import '../css/i-like-this-page.css';
import likeIcon from '../images/like-icon-64x64.png';
import unlikeIcon from '../images/unlike-icon-64x64.png';
import warningIcon from '../images/warning-icon-64x64.png';

import { getLike, deleteLike, addLike } from './api.js';

const nonLikeBackgroundColor = 'rgb(255 221 221)';
const likeBackgroundColor = 'rgb(255, 202, 202)';
const errorBackgroundColor = 'rgb(214, 214, 214)';

const renderButton = (likeOrError) => {
  const iltpBox = document.createElement('div');
  iltpBox.setAttribute('id', 'iltp-box');

  const iltpBoxStyle = `
    background-color: ${
      likeOrError ? nonLikeBackgroundColor : errorBackgroundColor
    };
    cursor: ${likeOrError ? 'pointer' : 'not-allowed'};`;

  iltpBox.setAttribute('style', iltpBoxStyle);

  const iltpIcon = document.createElement('span');
  iltpIcon.setAttribute('id', 'iltp-content-icon');
  const heartImage = document.createElement('img');
  heartImage.setAttribute('id', 'iltp-content-icon-img');
  heartImage.width = 16;
  heartImage.height = 16;
  heartImage.src = likeOrError ? unlikeIcon : warningIcon;

  iltpIcon.appendChild(heartImage);
  iltpBox.appendChild(iltpIcon);

  if (likeOrError) {
    const iltpNumber = document.createElement('span');
    iltpNumber.setAttribute('id', 'iltp-content-number');
    iltpNumber.innerText = '0';
    iltpBox.appendChild(iltpNumber);
  }

  removeLikeButtonIfExisted();
  iltpContainer.appendChild(iltpBox);
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
  document.getElementById('iltp-box').style.backgroundColor = userLikeStatus
    ? likeBackgroundColor
    : nonLikeBackgroundColor;
  document.getElementById('iltp-content-icon-img').src = userLikeStatus
    ? likeIcon
    : unlikeIcon;
  document.getElementById('iltp-content-number').innerText =
    apiResult.response.likeCount;
};

const handleError = (err) => {
  console.error(err);
  renderButton(false);
};

renderButton(true);

window.addEventListener('load', () => {
  const currentLocation = window.location.host + window.location.pathname;
  getLikeAndRender(currentLocation);
});
