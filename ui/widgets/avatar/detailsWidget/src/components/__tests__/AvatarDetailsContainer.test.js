import React from 'react';
import { render, wait } from '@testing-library/react';
import '@testing-library/jest-dom/extend-expect';

import 'components/__mocks__/i18n';
import getAvatar from 'api/avatar';
import avatarApiGetResponseMock from 'components/__mocks__/avatarMocks';
import AvatarDetailsContainer from 'components/AvatarDetailsContainer';

jest.mock('api/avatar');

beforeEach(() => {
  getAvatar.mockClear();
});

describe('AvatarDetailsContainer component', () => {
  test('requests data when component is mounted', async () => {
    getAvatar.mockImplementation(() => Promise.resolve(avatarApiGetResponseMock));

    render(<AvatarDetailsContainer id="1" />);

    await wait(() => {
      expect(getAvatar).toHaveBeenCalledTimes(1);
    });
  });

  test('data is shown after mount API call', async () => {
    getAvatar.mockImplementation(() => Promise.resolve(avatarApiGetResponseMock));

    const { getByText } = render(<AvatarDetailsContainer id="1" />);

    await wait(() => {
      expect(getAvatar).toHaveBeenCalledTimes(1);
      expect(getByText('entities.avatar.username')).toBeInTheDocument();
      expect(getByText('entities.avatar.image')).toBeInTheDocument();
    });
  });

  test('error is shown after failed API call', async () => {
    const onErrorMock = jest.fn();
    getAvatar.mockImplementation(() => Promise.reject());

    const { getByText } = render(<AvatarDetailsContainer id="1" onError={onErrorMock} />);

    await wait(() => {
      expect(getAvatar).toHaveBeenCalledTimes(1);
      expect(onErrorMock).toHaveBeenCalledTimes(1);
      expect(getByText('common.couldNotFetchData')).toBeInTheDocument();
    });
  });
});
