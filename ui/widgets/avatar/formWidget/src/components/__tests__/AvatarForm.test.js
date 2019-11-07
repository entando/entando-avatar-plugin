import React from 'react';
import '@testing-library/jest-dom/extend-expect';
import { render } from '@testing-library/react';
import 'components/__mocks__/i18nMock';
import avatarMock from 'components/__mocks__/avatarMocks';
// import { mockConferenceWithDateStrings } from 'components/__mocks__/conferenceMocks';
import AvatarForm from 'components/AvatarForm';
import { createMuiTheme } from '@material-ui/core';
import { ThemeProvider } from '@material-ui/styles';

const theme = createMuiTheme();

describe('Conference Form', () => {
  it('shows form', () => {
    const { getByLabelText } = render(
      <ThemeProvider theme={theme}>
        <AvatarForm avatar={avatarMock} />
      </ThemeProvider>
    );
    expect(getByLabelText('entities.avatar.username').value).toBe(
      'Ut similique totam voluptatem maiores maiores expedita ab at eos. Dolore est dolorem dolor harum. Quia odio eaque officiis ullam. Voluptatem fuga similique quisquam error. Eius nostrum iste dolore est laudantium voluptatem.'
    );
  });
});
