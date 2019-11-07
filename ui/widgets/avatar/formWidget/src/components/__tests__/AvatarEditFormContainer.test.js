import React from 'react';
import { fireEvent, render, wait } from '@testing-library/react';
import '@testing-library/jest-dom/extend-expect';
import { apiAvatarGet, apiAvatarPut } from 'api/avatars';
import AvatarEditFormContainer from 'components/AvatarEditFormContainer';
import 'components/__mocks__/i18nMock';
import avatarMock from 'components/__mocks__/avatarMocks';

jest.mock('api/avatars');

describe('ConferenceEditFormContainer', () => {
  beforeEach(() => {
    jest.clearAllMocks();
  });

  const errorMessageKey = 'errors.dataLoading';
  const successMessageKey = 'common.dataSaved';

  const onErrorMock = jest.fn();
  const onUpdateMock = jest.fn();

  it('loads data', async () => {
    apiAvatarGet.mockImplementation(() => Promise.resolve(avatarMock));
    const { queryByText } = render(
      <AvatarEditFormContainer id="1" onError={onErrorMock} onUpdate={onUpdateMock} />
    );

    await wait(() => {
      expect(apiAvatarGet).toHaveBeenCalledTimes(1);
      expect(apiAvatarGet).toHaveBeenCalledWith('1');
      expect(queryByText(errorMessageKey)).not.toBeInTheDocument();
      expect(onErrorMock).toHaveBeenCalledTimes(0);
    });
  });

  it('saves data', async () => {
    apiAvatarGet.mockImplementation(() => Promise.resolve(avatarMock));
    apiAvatarPut.mockImplementation(() => Promise.resolve(avatarMock));

    const { findByTestId, queryByText } = render(
      <AvatarEditFormContainer id="1" onError={onErrorMock} onUpdate={onUpdateMock} />
    );

    const saveButton = await findByTestId('submit-btn');

    fireEvent.click(saveButton);

    await wait(() => {
      expect(apiAvatarPut).toHaveBeenCalledTimes(1);
      expect(apiAvatarPut).toHaveBeenCalledWith(avatarMock);
      expect(queryByText(successMessageKey)).toBeInTheDocument();
      expect(onErrorMock).toHaveBeenCalledTimes(0);
      expect(queryByText(errorMessageKey)).not.toBeInTheDocument();
    });
  });

  it('shows an error if data is not successfully loaded', async () => {
    apiAvatarGet.mockImplementation(() => Promise.reject());
    const { queryByText } = render(
      <AvatarEditFormContainer id="1" onError={onErrorMock} onUpdate={onUpdateMock} />
    );

    await wait(() => {
      expect(apiAvatarGet).toHaveBeenCalledTimes(1);
      expect(apiAvatarGet).toHaveBeenCalledWith('1');
      expect(onErrorMock).toHaveBeenCalledTimes(1);
      expect(queryByText(errorMessageKey)).toBeInTheDocument();
      expect(queryByText(successMessageKey)).not.toBeInTheDocument();
    });
  });

  it('shows an error if data is not successfully saved', async () => {
    apiAvatarGet.mockImplementation(() => Promise.resolve(avatarMock));
    apiAvatarPut.mockImplementation(() => Promise.reject());
    const { findByTestId, getByText } = render(
      <AvatarEditFormContainer id="1" onError={onErrorMock} />
    );

    const saveButton = await findByTestId('submit-btn');

    fireEvent.click(saveButton);

    await wait(() => {
      expect(apiAvatarGet).toHaveBeenCalledTimes(1);
      expect(apiAvatarGet).toHaveBeenCalledWith('1');

      expect(apiAvatarPut).toHaveBeenCalledTimes(1);
      expect(apiAvatarPut).toHaveBeenCalledWith(avatarMock);

      expect(onErrorMock).toHaveBeenCalledTimes(1);
      expect(getByText(errorMessageKey)).toBeInTheDocument();
    });
  });
});
