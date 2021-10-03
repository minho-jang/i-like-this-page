import { getLike, deleteLike, addLike } from "./api.js";
import "../css/i-like-this-page.css";

const likeBackgroundColor = "rgb(255, 202, 202)";
const errorBackgroundColor = "rgb(214, 214, 214)";

const renderButton = (likeOrError) => {
  const iltpBox = document.createElement("span");
  iltpBox.setAttribute("id", "iltp-box");

  const iltpBoxStyle = `
    border-radius: 15%;
    background-color: ${
      likeOrError ? likeBackgroundColor : errorBackgroundColor
    };
    font-family: 'Fira Sans', sans-serif;
    padding: 0.5rem 1rem;`;
  iltpBox.setAttribute("style", iltpBoxStyle);

  const iltpIcon = document.createElement("span");
  iltpIcon.setAttribute("id", "iltp-content-icon");
  iltpIcon.innerText = likeOrError ? "💕" : "❗️";
  iltpBox.appendChild(iltpIcon);

  const iltpContent = document.createElement("span");
  iltpContent.setAttribute("id", "iltp-content-number");
  if (likeOrError) {
    iltpContent.setAttribute("style", "margin-left: .4rem;");
    iltpContent.innerText = "0";
  }
  iltpBox.appendChild(iltpContent);

  removeLikeButtonIfExisted();
  iltpContainer.appendChild(iltpBox);
};

const removeLikeButtonIfExisted = () => {
  if (iltpContainer.childNodes.length > 0) {
    iltpContainer.removeChild(iltpContainer.childNodes[0]);
  }
};

let userLikeStatus = false;
const iltpContainer = document.getElementById("i-like-this-page");
const iltpContainerStyle = `
  display: inline-block;
  cursor: pointer;
  color: black;
  font-size: 0.75em;
`;
iltpContainer.setAttribute("style", iltpContainerStyle);

iltpContainer.addEventListener("click", () => {
  const urlWithoutProtocol = window.location.host + window.location.pathname;

  if (userLikeStatus) {
    cancelLikeAndRender(urlWithoutProtocol);
  } else {
    addLikeAndRender(urlWithoutProtocol);
  }
});

const getLikeAndRender = async (currentLocation) => {
  try {
    const apiResult = await getLike(currentLocation);
    if (apiResult.error) {
      handleError(apiResult.error);
      return;
    }

    userLikeStatus = apiResult.response.likeStatus;
    document.getElementById("iltp-content-number").innerHTML =
      apiResult.response.likeCount;
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

    userLikeStatus = apiResult.response.likeStatus;
    document.getElementById("iltp-content-number").innerText =
      apiResult.response.likeCount;
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

    userLikeStatus = apiResult.response.likeStatus;
    document.getElementById("iltp-content-number").innerText =
      apiResult.response.likeCount;
  } catch (err) {
    handleError(err);
  }
};

const handleError = (err) => {
  console.error(err);
  renderButton(false);
};

renderButton(true);

window.addEventListener("load", () => {
  const currentLocation = window.location.host + window.location.pathname;
  getLikeAndRender(currentLocation);
});
