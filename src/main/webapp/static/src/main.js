import React from 'react/addons';
import BS from 'react-bootstrap';
import View from 'react-flexbox-ui';
import $ from 'jquery';

import TodoStore from './flux/stores/todo';
import TodoActions from './flux/actions/todo';

import CreateForm from './components/CreateForm';
import Item from './components/Item';
import Statistic from './components/Statistic';

$(function() {
  const App = React.createClass({
    mixins: [ TodoStore.mixinFor() ],

    getStateFromStores() {
      return {
        todo: TodoStore.getState()
      };
    },

    handleRESTSwitch() {
      TodoActions.rest(!this.state.todo.rest);
    },

    render() {
      const wsLabel = this.state.todo.rest ? 'Turn websocket on' : 'Turn websocket off';

      return (
        <View column expand>
          <BS.Navbar brand="What to do?" inverse>
            <BS.Nav right eventKey={0} style={ { marginRight: 0 } }>
              <Statistic { ...this.state.todo } />
              <li className="navbar-btn">
                <BS.Button bsSize="small" onClick={ this.handleRESTSwitch }>{ wsLabel }</BS.Button>
              </li>
            </BS.Nav>
          </BS.Navbar>

          <View item size="1" componentClass={ BS.Grid }>
            <CreateForm onNewItem={ TodoActions.create } error={ this.state.todo.error } />
            <BS.Row>
              <BS.Col xs={12} style={{ height: '20px' }} />
            </BS.Row>
            <BS.Row>
              <BS.Col xs={12} md={12}>
                <BS.Table striped bordered condensed hover>
                  <thead>
                    <tr>
                      <td>#</td>
                      <td>Description</td>
                      <td></td>
                    </tr>
                  </thead>
                  <tbody>
                    {
                      this.state.todo.data.map(function(item) {
                        return <Item key={ 'item_' + item.id } { ...item } onDelete={ TodoActions.delete} onToggleDone={ TodoActions.done } />;
                      })
                    }
                  </tbody>
                </BS.Table>
              </BS.Col>
            </BS.Row>
          </View>
        </View>);
    }
  });

  React.render(<App />, document.body);
});
