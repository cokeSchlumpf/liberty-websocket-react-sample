import React from 'react';
import BS from 'react-bootstrap';

export default React.createClass({
  propTypes: {
    id: React.PropTypes.number,
    description: React.PropTypes.string,
    done: React.PropTypes.bool,
    onDelete: React.PropTypes.func,
    onToggleDone: React.PropTypes.func
  },

  handleDelete() {
    if (this.props.onDelete) {
      this.props.onDelete(this.props.id);
    }
  },

  handleToggleDone() {
    if (this.props.onToggleDone) {
      this.props.onToggleDone(this.props.id, !this.props.done);
    }
  },

  render() {
    return (
      <tr>
        <td>{ this.props.id }</td>
        <td>{ this.props.description }</td>
        <td className="text-right">
          <BS.Button bsSize="xsmall" bsStyle="danger" onClick={ this.handleDelete }>Delete</BS.Button>&nbsp;
          { !this.props.done ?
            <BS.Button bsSize="xsmall" bsStyle="success" onClick={ this.handleToggleDone }>Mark Complete</BS.Button>
            :
            <BS.Button bsSize="xsmall" onClick={ this.handleToggleDone }>Mark in Progress</BS.Button>
          }
        </td>
      </tr>);
  }
});
