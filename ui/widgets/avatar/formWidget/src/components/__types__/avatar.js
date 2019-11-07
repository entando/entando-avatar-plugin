import PropTypes from 'prop-types';

export default PropTypes.shape({
  id: PropTypes.number,

  username: PropTypes.string.isRequired,
  image: PropTypes.string,
});

export const formValues = PropTypes.shape({
  username: PropTypes.string,
  image: PropTypes.string,
});

export const formTouched = PropTypes.shape({
  username: PropTypes.bool,
  image: PropTypes.bool,
});

export const formErrors = PropTypes.shape({
  username: PropTypes.oneOfType([PropTypes.string, PropTypes.shape()]),
  image: PropTypes.oneOfType([PropTypes.string, PropTypes.shape()]),
});
