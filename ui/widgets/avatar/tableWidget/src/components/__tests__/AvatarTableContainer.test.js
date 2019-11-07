import React from 'react';
import { render, wait } from '@testing-library/react';
import '@testing-library/jest-dom/extend-expect';
import avatarMocks from 'components/__mocks__/avatarMocks';
import avatarsGet from 'api/avatars';
import 'components/__mocks__/i18nMock';
import AvatarTableContainer from 'components/AvatarTableContainer';

jest.mock('api/avatars');

describe('AvatarTableContainer', () => {
    const errorMessageKey = 'common.couldNotFetchData';

    it('calls API', async () => {
        avatarsGet.mockImplementation(() => Promise.resolve(avatarMocks));
        const { queryByText } = render(<AvatarTableContainer />);

        await wait(() => {
            expect(avatarsGet).toHaveBeenCalledTimes(1);
            expect(queryByText(errorMessageKey)).not.toBeInTheDocument();
        });
    });

    it('shows an error if the API call is not successful', async () => {
        const onErrorMock = jest.fn();
        avatarsGet.mockImplementation(() => Promise.reject());
        const { getByText } = render(<AvatarTableContainer onError={onErrorMock} />);

        await wait(() => {
            expect(onErrorMock).toHaveBeenCalledTimes(1);
            expect(getByText(errorMessageKey)).toBeInTheDocument();
        });
    });
});
