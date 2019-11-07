import { DOMAIN, JWT_TOKEN } from 'api/constants';

const resource = 'avatars';

const defaultOptions = {
  headers: new Headers({
    Authorization: `Bearer ${JWT_TOKEN}`,
    'Content-Type': 'application/json',
  }),
};

const request = async (url, options) => {
  const response = await fetch(url, options);

  return response.status >= 200 && response.status < 300
    ? response.json()
    : Promise.reject(new Error(response.statusText || response.status));
};

export const apiAvatarGet = async id => {
  const url = `${DOMAIN}/${resource}/${id}`;
  const options = {
    ...defaultOptions,
    method: 'GET',
  };
  return request(url, options);
};

export const apiAvatarPost = async avatar => {
  const url = `${DOMAIN}/${resource}`;
  const options = {
    ...defaultOptions,
    method: 'POST',
    body: avatar ? JSON.stringify(avatar) : null,
  };
  return request(url, options);
};

export const apiAvatarPut = async avatar => {
  const url = `${DOMAIN}/${resource}`;
  const options = {
    ...defaultOptions,
    method: 'PUT',
    body: avatar ? JSON.stringify(avatar) : null,
  };
  return request(url, options);
};
