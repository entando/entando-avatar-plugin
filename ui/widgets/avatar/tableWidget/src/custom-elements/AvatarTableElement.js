import React from 'react';
import ReactDOM from 'react-dom';
import i18next from 'i18next';
import AvatarTableContainer from 'components/AvatarTableContainer';

class AvatarTableElement extends HTMLElement
{
  connectedCallback()
  {
    const mountPoint = document.createElement('div');
    this.appendChild(mountPoint);

    const locale = this.getAttribute('locale') || 'en';
    i18next.changeLanguage(locale);

    const customEventPrefix = 'Avatar.table.';

    const onError = error => {
      const customEvent = new CustomEvent(`${customEventPrefix}error`, {
        detail: {
          error,
        },
      });
      this.dispatchEvent(customEvent);
    };

    const onSelect = item => {
      const customEvent = new CustomEvent(`${customEventPrefix}select`, {
        detail: {
          item,
        },
      });
      this.dispatchEvent(customEvent);
    };

    const reactRoot = React.createElement(AvatarTableContainer, {onError, onSelect}, null);
    ReactDOM.render(reactRoot, mountPoint);
  }
}

customElements.define('avatar-table', AvatarTableElement);
