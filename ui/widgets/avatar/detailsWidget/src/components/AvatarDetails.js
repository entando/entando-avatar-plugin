import React from 'react';
import i18next from 'i18next';
import Box from '@material-ui/core/Box';

import avatarType from 'components/__types__/avatar';
import AvatarFieldTable from 'components/avatar-field-table/AvatarFieldTable';

const AvatarDetails = ({ avatar }) => {
  return (
    <Box>
      <h3>
        {i18next.t('common.widgetName', {
          widgetNamePlaceholder: 'Avatar',
        })}
      </h3>
      <AvatarFieldTable avatar={avatar} />
    </Box>
  );
};

AvatarDetails.propTypes = {
  avatar: avatarType,
};

AvatarDetails.defaultProps = {
  avatar: {},
};

export default AvatarDetails;
