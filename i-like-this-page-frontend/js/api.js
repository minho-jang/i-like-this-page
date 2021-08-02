const DOMAIN = "http://localhost:8080";
const RESTAPI_BASEADDRESS = `${DOMAIN}/api/v1`;

export const getLike = (currentLocation) => {
  const getLikeUrl = new URL(`${RESTAPI_BASEADDRESS}/like`);
  const params = { url: currentLocation };
  getLikeUrl.search = new URLSearchParams(params).toString();

  return fetch(getLikeUrl).then((response) => response.json());
}

export const addLike = (currentLocation) => {
  const addLikeUrl = `${RESTAPI_BASEADDRESS}/like`;
  const options = {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify({ url: currentLocation }),
  };

  return fetch(addLikeUrl, options).then((response) => response.json());
};

export const deleteLike = (currentLocation) => {
  const deleteLikeUrl = `${RESTAPI_BASEADDRESS}/like`;
  const options = {
    method: "DELETE",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify({ url: currentLocation }),
  };

  return fetch(deleteLikeUrl, options).then((response) => response.json());
};

