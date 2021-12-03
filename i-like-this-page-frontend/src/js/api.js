const DOMAIN = 'https://iltp.link';
const RESTAPI_BASEADDRESS = `${DOMAIN}/api/v1`;

const AUTHORIZATION_PREFIX = 'Bearer ';
const TOKEN_KEY_LOCALSTORAGE = 'iltp_tk';

export const getLike = async (currentLocation) => {
  const token = await checkToken();
  const getLikeUrl = createGetRequestUrl(`${RESTAPI_BASEADDRESS}/like`, {
    url: currentLocation,
  });
  const options = {
    method: 'GET',
    headers: {
      Authorization: AUTHORIZATION_PREFIX + token,
    },
  };

  const apiResult = await fetchSync(getLikeUrl, options);
  return apiResult;
};

export const addLike = async (currentLocation) => {
  const token = await checkToken();
  const addLikeUrl = `${RESTAPI_BASEADDRESS}/like`;
  const options = {
    method: 'POST',
    headers: {
      Authorization: AUTHORIZATION_PREFIX + token,
      'Content-Type': 'application/json',
    },
    body: JSON.stringify({ url: currentLocation }),
  };

  const apiResult = await fetchSync(addLikeUrl, options);
  return apiResult;
};

export const deleteLike = async (currentLocation) => {
  const token = await checkToken();
  const deleteLikeUrl = `${RESTAPI_BASEADDRESS}/like`;
  const options = {
    method: 'DELETE',
    headers: {
      Authorization: AUTHORIZATION_PREFIX + token,
      'Content-Type': 'application/json',
    },
    body: JSON.stringify({ url: currentLocation }),
  };

  const apiResult = await fetchSync(deleteLikeUrl, options);
  return apiResult;
};

const getToken = async () => {
  const getTokenUrl = `${RESTAPI_BASEADDRESS}/token`;
  const apiResult = await fetchSync(getTokenUrl);
  return apiResult;
};

const checkToken = async () => {
  const token = localStorage.getItem(TOKEN_KEY_LOCALSTORAGE);
  if (token) {
    return token;
  } else {
    const apiResult = await getToken();
    if (apiResult.error) {
      handleError(apiResult.error);
    }

    const newToken = apiResult.response;
    localStorage.setItem(TOKEN_KEY_LOCALSTORAGE, newToken);
    return newToken;
  }
};

const fetchSync = async (url, options) => {
  const response = await fetch(url, options);
  const responseJson = await response.json();
  return responseJson;
};

const createGetRequestUrl = (url, params) => {
  const urlWithParams = new URL(url);
  urlWithParams.search = new URLSearchParams(params).toString();
  return urlWithParams.toString();
};

const handleError = (message) => {
  throw message;
};
