import React from 'react';
import BS from 'react-bootstrap';

export default React.createClass({
  propTypes: {
    data: React.PropTypes.array
  },

  render() {
    const statistic = this.props.data.reduce(function(current, item) {
      if (item.done) {
        current.done = current.done + 1;
      } else {
        current.open = current.open + 1;
      }

      return current;
    }, { open: 0, done: 0 });

    return <BS.NavItem>Open: { statistic.open }, Done: { statistic.done }</BS.NavItem>;
  }
});
