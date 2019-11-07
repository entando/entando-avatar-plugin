import React, {PureComponent} from 'react';
import PropTypes from 'prop-types';
import i18next from 'i18next';
import AvatarTable from 'components/AvatarTable';
import avatarsGet from 'api/avatars';
import Notification from 'components/common/Notification';
import { ThemeProvider } from '@material-ui/styles';
import { createMuiTheme } from '@material-ui/core';

export default class AvatarTableContainer extends PureComponent {
  theme = createMuiTheme();

  state = {
      avatars: [],
      notificationStatus: null,
      notificationMessage: null,
  };

  async componentDidMount() {
    try {
      const json = await avatarsGet();

      const avatars = json.map(avatar => ({
        ...avatar,
      }));
      this.setState({
        avatars,
        notificationStatus: null,
        notificationMessage: null,
      });
    } catch (err) {
      this.handleError(err);
    }
  }

  closeNotification = () => {
    this.setState({error: null});
  };

  handleError(err) {
    const {onError} = this.props;
    onError(err);
    this.setState({
      notificationStatus: 'error',
      notificationMessage: i18next.t('common.couldNotFetchData'),
    });
  }

  render() {
    const {notificationStatus, notificationMessage, avatars} = this.state;
    const {onSelect} = this.props;
    return (
      <ThemeProvider theme={this.theme}>
        <AvatarTable avatars={avatars} onSelect={onSelect}/>
        <Notification
          status={notificationStatus}
          message={notificationMessage}
          onClose={this.closeNotification}
        />
      </ThemeProvider>
    );
  }
}

AvatarTableContainer.propTypes = {
  onError: PropTypes.func,
  onSelect: PropTypes.func,
};

AvatarTableContainer.defaultProps = {
  onError: () => {
  },
  onSelect: () => {
  },
};
