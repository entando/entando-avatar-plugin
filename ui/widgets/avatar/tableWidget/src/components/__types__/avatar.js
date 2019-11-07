import PropTypes from 'prop-types';

const avatarType = PropTypes.arrayOf(
  PropTypes.shape({
    id: PropTypes.number,

    username: PropTypes.string,
    image: PropTypes.string,
  }),
);

export default avatarType;
