import React from 'react/addons';
import BS from 'react-bootstrap';

export default React.createClass({
  mixins: [ React.addons.LinkedStateMixin ],

  propTypes: {
    onNewItem: React.PropTypes.func,
    error: React.PropTypes.string
  },

  getInitialState() {
    return {
      description: ''
    };
  },

  handleNewItemClick(event) {
    if (this.props.onNewItem) {
      this.props.onNewItem(this.refs.input.getValue());
      this.setState({ description: '' });
    }

    event.preventDefault();
  },

  render() {
    return (
      <BS.Row>
        <form onSubmit={ this.handleNewItemClick }>
          <BS.Col xs={12} md={10}>
            <BS.Input
              type="text" bsStyle={ this.props.error ? 'error' : null }
              placeholder="Enter item description" help={ this.props.error }
              ref="input" valueLink={this.linkState('description')} />
          </BS.Col>
          <BS.Col xs={12} md={2} className="text-right">
            <BS.ButtonInput type="submit" bsStyle="primary" onClick={ this.handleNewItemClick } disabled={ this.state.description.length === 0 }>Create new Item</BS.ButtonInput>
          </BS.Col>
        </form>
      </BS.Row>);
  }
});
