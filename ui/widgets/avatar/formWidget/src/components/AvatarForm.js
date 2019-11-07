import React from 'react';
import PropTypes from 'prop-types';
import i18next from 'i18next';
import { withStyles } from '@material-ui/styles';
import Grid from '@material-ui/core/Grid';
import Button from '@material-ui/core/Button';
import TextField from '@material-ui/core/TextField';
import { withFormik } from 'formik';
import * as yup from 'yup';

import { compose } from 'recompose'; // TODO: REMOVE compose/recompose

import { formValues, formTouched, formErrors } from 'components/__types__/avatar';

const styles = theme => ({
  root: {
    margin: theme.spacing(3),
  },
  textField: {
    width: '100%',
  },
});

const AvatarForm = props => {
  const {
    classes,
    values,
    touched,
    errors,
    handleChange,
    handleBlur,
    handleSubmit,
    setFieldValue,
  } = props;

  const getHelperText = field => (errors[field] && touched[field] ? errors[field] : '');

  return (
      <form onSubmit={handleSubmit} className={classes.root}>
        <Grid container spacing={2}>
          <Grid item xs={12} sm={6}>
            <TextField
              id="avatar-username"
              error={errors.name && touched.name}
              helperText={getHelperText('username')}
              className={classes.textField}
              onBlur={handleBlur}
              value={values.username}
              name="username"
              onChange={handleChange}
              label={i18next.t('entities.avatar.username')}
            />
          </Grid>
          <Grid item xs={12} sm={6}>
            <TextField
              id="avatar-image"
              error={errors.name && touched.name}
              helperText={getHelperText('image')}
              className={classes.textField}
              onBlur={handleBlur}
              value={values.image}
              name="image"
              onChange={handleChange}
              label={i18next.t('entities.avatar.image')}
            />
          </Grid>
          <Button type="submit" color="primary" data-testid="submit-btn">
            {i18next.t('common.save')}
          </Button>
        </Grid>
      </form>
  );
};

AvatarForm.propTypes = {
  classes: PropTypes.shape({
    root: PropTypes.string,
    textField: PropTypes.string,
    submitButton: PropTypes.string,
  }),
  values: formValues,
  touched: formTouched,
  errors: formErrors,
  handleChange: PropTypes.func.isRequired,
  handleBlur: PropTypes.func.isRequired,
  handleSubmit: PropTypes.func.isRequired,
  setFieldValue: PropTypes.func.isRequired,
};

AvatarForm.defaultProps = {
  classes: {},
  values: {},
  touched: {},
  errors: {},
};

const emptyAvatar = {
  username: '',
  image: '',
};

const validationSchema = yup.object().shape({
  username: yup.string().required(),
  image: yup.string(),
});

const formikBag = {
  mapPropsToValues: ({ avatar }) => avatar || emptyAvatar,

  enableReinitialize: true,

  validationSchema,

  handleSubmit: (values, { props: { onSubmit } }) => {
    onSubmit(values);
  },

  displayName: 'AvatarForm',
};

export default compose(
  withStyles(styles, { withTheme: true }),
  withFormik(formikBag)
)(AvatarForm);
