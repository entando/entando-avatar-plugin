import React from 'react';
import PropTypes from 'prop-types';
import i18next from 'i18next';
import { withStyles } from '@material-ui/styles';
import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableCell from '@material-ui/core/TableCell';
import TableHead from '@material-ui/core/TableHead';
import TableRow from '@material-ui/core/TableRow';
import avatarType from 'components/__types__/avatar';

const styles = {
    root: {
        cursor: 'pointer',
    },
};

const AvatarTable = ({ classes, avatars, onSelect }) => {
    const tableRows = avatars.map(avatar => (
        <TableRow
            hover
            className={classes.root}
            key={avatar.id}
            onClick={() => onSelect(avatar)}
        >
            <TableCell><span>{avatar.username}</span></TableCell>
            <TableCell><span>{avatar.image}</span></TableCell>
        </TableRow>
    ));

    return (avatars.length ? (
        <Table>
            <TableHead>
                <TableRow>
                    <TableCell>
                        <span>{ i18next.t('entities.avatar.username') }</span>
                    </TableCell>
                    <TableCell>
                        <span>{ i18next.t('entities.avatar.image') }</span>
                    </TableCell>
                </TableRow>
            </TableHead>
            <TableBody>
                { tableRows }
            </TableBody>
        </Table>
    ) : (
        i18next.t("entities.avatar.noItems")
    ));
};

AvatarTable.propTypes = {
    classes: PropTypes.shape({
        root: PropTypes.string,
    }),
    avatars: avatarType,
    onSelect: PropTypes.func,
};

AvatarTable.defaultProps = {
    classes: {
        root: '',
    },
    onSelect: () => {},
};

export default withStyles(styles)(AvatarTable);
