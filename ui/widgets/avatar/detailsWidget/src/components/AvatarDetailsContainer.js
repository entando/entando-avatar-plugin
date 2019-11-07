import React from 'react';
import PropTypes from 'prop-types';
import i18next from 'i18next';
import { ThemeProvider } from '@material-ui/styles';
import { createMuiTheme } from '@material-ui/core';

import AvatarDetails from 'components/AvatarDetails';
import Notification from 'components/common/Notification';
import getAvatar from 'api/avatar';

class AvatarDetailsContainer extends React.Component {
  constructor(props) {
    super(props);

    this.state = {
      loading: true,
      avatar: {},
      notificationStatus: null,
      notificationMessage: null,
    };

    this.theme = createMuiTheme();
    this.closeNotification = this.closeNotification.bind(this);
  }

  componentDidMount() {
    const { id, onError } = this.props;

    if (id) {
      getAvatar({ id })
        .then(response => this.setState({
          notificationStatus: null,
          notificationMessage: null,
          avatar: response
        }))
        .catch(e => {
          onError(e);
          this.setState({
            notificationStatus: 'error',
            notificationMessage: i18next.t('common.couldNotFetchData'),
          });
        })
        .finally(() => this.setState({ loading: false }));
    } else {
      this.setState({
        loading: false,
        notificationStatus: 'error',
        notificationMessage: i18next.t('common.missingId'),
      });
    }
  }

  closeNotification() {
    this.setState({ error: null });
  }

  render() {
    const { avatar, notificationStatus, notificationMessage, loading } = this.state;

    return (
      <ThemeProvider theme={this.theme}>
        {loading && i18next.t('common.loading')}
        {!loading && <AvatarDetails avatar={avatar} />}
        <Notification
          status={notificationStatus}
          message={notificationMessage}
          onClose={this.closeNotification}
        />
      </ThemeProvider>
    );
  }
}

AvatarDetailsContainer.propTypes = {
  id: PropTypes.string.isRequired,
  onError: PropTypes.func,
};

AvatarDetailsContainer.defaultProps = {
  onError: () => {},
};

export default AvatarDetailsContainer;
