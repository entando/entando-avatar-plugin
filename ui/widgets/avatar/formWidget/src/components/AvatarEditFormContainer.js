import React, { PureComponent } from 'react';
import PropTypes from 'prop-types';
import i18next from 'i18next';
import { ThemeProvider } from '@material-ui/styles';
import { createMuiTheme } from '@material-ui/core';
import AvatarForm from 'components/AvatarForm';
import Notification from 'components/common/Notification';
import { apiAvatarGet, apiAvatarPut } from 'api/avatars';

class AvatarEditFormContainer extends PureComponent {
  theme = createMuiTheme();

  state = {
    avatar: null,
    notificationMessage: null,
  };

  constructor(props) {
    super(props);
    this.closeNotification = this.closeNotification.bind(this);
    this.handleSubmit = this.handleSubmit.bind(this);
  }

  async componentDidMount() {
    const { id } = this.props;
    if (!id) return;
    try {
      const avatar = await apiAvatarGet(id);
      this.setState({
        avatar,
      });
    } catch (err) {
      this.handleError(err);
    }
  }

  closeNotification() {
    this.setState({ notificationMessage: null, notificationStatus: null });
  }

  async handleSubmit(avatar) {
    try {
      const updatedAvatar = await apiAvatarPut(avatar);
      const { onUpdate } = this.props;
      onUpdate(updatedAvatar);

      this.setState({
        avatar: updatedAvatar,
        notificationMessage: i18next.t('common.dataSaved'),
        notificationStatus: 'success',
      });
    } catch (err) {
      this.handleError(err);
    }
  }

  handleError(err) {
    const { onError } = this.props;
    onError(err);
    this.setState({
      notificationMessage: i18next.t('errors.dataLoading'),
      notificationStatus: 'error',
    });
  }

  render() {
    const { notificationMessage, notificationStatus, avatar } = this.state;
    return (
      <ThemeProvider theme={this.theme}>
        <AvatarForm
          avatar={avatar}
          onSubmit={this.handleSubmit}
        />
        <Notification
          status={notificationStatus}
          message={notificationMessage}
          onClose={this.closeNotification}
        />
      </ThemeProvider>
    );
  }
}

AvatarEditFormContainer.propTypes = {
  id: PropTypes.string.isRequired,
  onError: PropTypes.func,
  onUpdate: PropTypes.func,
};

AvatarEditFormContainer.defaultProps = {
  onError: () => {},
  onUpdate: () => {},
};

export default AvatarEditFormContainer;
