import React from 'react';
import ReactDOM from 'react-dom';
import AvatarEditFormContainer from 'components/AvatarEditFormContainer';
import AvatarAddFormContainer from 'components/AvatarAddFormContainer';
import setLocale from 'i18n/setLocale';

class AvatarFormElement extends HTMLElement {
  connectedCallback() {
    const mountPoint = document.createElement('div');
    this.appendChild(mountPoint);

    const id = this.getAttribute('id');
    const locale = this.getAttribute('locale');

    setLocale(locale);

    const prefix = 'avatar.form.';

    const eventDispatcher = (eventId, detailPropName) => payload => {
      const customEvent = new CustomEvent(eventId, {
        detail: {
          [detailPropName]: payload,
        },
      });
      this.dispatchEvent(customEvent);
    };

    const onCreateError = eventDispatcher(`${prefix}createError`, 'error');
    const onUpdateError = eventDispatcher(`${prefix}updateError`, 'error');
    const onCreate = eventDispatcher(`${prefix}create`, 'item');
    const onUpdate = eventDispatcher(`${prefix}update`, 'item');

    const reactRoot = id
      ? React.createElement(
          AvatarEditFormContainer,
          { id, onError: onUpdateError, onUpdate },
          null
        )
      : React.createElement(
          AvatarAddFormContainer,
          { onError: onCreateError, onCreate },
          null
        );
    ReactDOM.render(reactRoot, mountPoint);
  }
}

customElements.define('avatar-form', AvatarFormElement);
