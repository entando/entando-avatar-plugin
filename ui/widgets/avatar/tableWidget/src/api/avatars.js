import { DOMAIN, JWT_TOKEN } from 'api/constants';

export default async () => {
    const url = `${DOMAIN}/avatars`;
    const init = {
        headers: {
            Authorization: `Bearer ${JWT_TOKEN}`,
        },
    };
    const response = await fetch(url, init);

    return response.json();
};
