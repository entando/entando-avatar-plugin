
import React from 'react';
import PropTypes from 'prop-types';
import { withTranslation } from 'react-i18next';
import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableCell from '@material-ui/core/TableCell';
import TableHead from '@material-ui/core/TableHead';
import TableRow from '@material-ui/core/TableRow';

import avatarType from 'components/__types__/avatar';

const AvatarFieldTable = ({ t, i18n: { language }, avatar }) => {
  const translationKeyPrefix = `entities.avatar.`;

  return (
    <Table>
      <TableHead>
        <TableRow>
          <TableCell>{t('common.name')}</TableCell>
          <TableCell>{t('common.value')}</TableCell>
        </TableRow>
      </TableHead>
      <TableBody>
        <TableRow>
          <TableCell>
            <span>{t(`${translationKeyPrefix}id`)}</span>
          </TableCell>
          <TableCell>
            <span>{avatar.id}</span>
          </TableCell>
        </TableRow>
        <TableRow>
          <TableCell>
            <span>{t(`${translationKeyPrefix}username`)}</span>
          </TableCell>
          <TableCell>
            <span>{avatar.username}</span>
          </TableCell>
        </TableRow>
        <TableRow>
          <TableCell>
            <span>{t(`${translationKeyPrefix}image`)}</span>
          </TableCell>
          <TableCell>
            <span><img src={ `data:${avatar.imageContentType};base64, ${avatar.image}`} alt="" /></span>
          </TableCell>
        </TableRow>
      </TableBody>
    </Table>
  );
};

AvatarFieldTable.propTypes = {
  avatar: avatarType,
  t: PropTypes.func.isRequired,
  i18n: PropTypes.shape({
    language: PropTypes.string,
  }).isRequired,
};

AvatarFieldTable.defaultProps = {
  avatar: [],
};

export default withTranslation()(AvatarFieldTable);
