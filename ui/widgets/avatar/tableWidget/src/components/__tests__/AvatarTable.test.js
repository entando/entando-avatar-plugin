import React from 'react';
import '@testing-library/jest-dom/extend-expect';
import { fireEvent, render } from '@testing-library/react';
import 'components/__mocks__/i18nMock';
import avatarMocks from 'components/__mocks__/avatarMocks';
import AvatarTable from 'components/AvatarTable';

describe('AvatarTable', () => {
  it('shows avatars', () => {
    const { getByText } = render(<AvatarTable avatars={avatarMocks} />);
    expect(getByText('Ut similique totam voluptatem maiores maiores expedita ab at eos. Dolore est dolorem dolor harum. Quia odio eaque officiis ullam. Voluptatem fuga similique quisquam error. Eius nostrum iste dolore est laudantium voluptatem.')).toBeInTheDocument();
    expect(getByText('Odit maiores consequatur. Libero quia autem quis quia hic et repellat molestiae. Asperiores voluptatem voluptas esse.')).toBeInTheDocument();
  });

  it('shows no avatars message', () => {
    const { queryByText } = render(<AvatarTable avatars={[]} />);
    expect(queryByText('Ut similique totam voluptatem maiores maiores expedita ab at eos. Dolore est dolorem dolor harum. Quia odio eaque officiis ullam. Voluptatem fuga similique quisquam error. Eius nostrum iste dolore est laudantium voluptatem.')).not.toBeInTheDocument();
    expect(queryByText('Odit maiores consequatur. Libero quia autem quis quia hic et repellat molestiae. Asperiores voluptatem voluptas esse.')).not.toBeInTheDocument();

    expect(queryByText('entities.avatar.noItems')).toBeInTheDocument();
  });

  it('calls onSelect when the user clicks a table row', () => {
    const onSelectMock = jest.fn();
    const { getByText } = render(
      <AvatarTable avatars={avatarMocks} onSelect={onSelectMock} />
    );
    fireEvent.click(getByText('Ut similique totam voluptatem maiores maiores expedita ab at eos. Dolore est dolorem dolor harum. Quia odio eaque officiis ullam. Voluptatem fuga similique quisquam error. Eius nostrum iste dolore est laudantium voluptatem.'));
    expect(onSelectMock).toHaveBeenCalledTimes(1);
  });
});
