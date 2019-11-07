import React from 'react';
import '@testing-library/jest-dom/extend-expect';
import { render } from '@testing-library/react';

import 'components/__mocks__/i18n';
import AvatarDetails from 'components/AvatarDetails';
import avatarMock from 'components/__mocks__/avatarMocks';
describe('AvatarDetails component', () => {
  test('renders data in details widget', () => {
    const { getByText } = render(<AvatarDetails avatar={avatarMock} />);
    
    expect(getByText('0')).toBeInTheDocument();
  });
});
