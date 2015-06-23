import React from 'react/addons';
import ReactFlux from 'react-flux';
import Constants from '../constants/todo';

let Store = ReactFlux.createStore({
    getInitialState: function() {
      return {
        data: [
          { id: 0, description: 'Hallo Freunde!', done: false }
        ],
        error: null
      };
    },

    storeDidMount: function() {

    }
  },
  [
    [ Constants.CREATE_SUCCESS, function handleCreateSuccess(payload) {
      this.setState(React.addons.update(this.state.getState(), {
        data: { $push: [ payload ] },
        error: { $set: null }
      }));
    } ],

    [ Constants.CREATE_FAIL, function handleDoneFail(error) {
      this.setState({ error: error.message });
    } ],

    [ Constants.DELETE_SUCCESS, function handleDeleteSuccess(payload) {
      this.setState({
        data: this.state.getState().data.filter(function(item) {
          return item.id !== payload.id;
        })
      });
    } ],

    [ Constants.DONE_SUCCESS, function handleDoneSuccess(payload) {
      this.setState({
        data: this.state.getState().data.map(function(item) {
          if (item.id === payload.id) {
            item.done = payload.done;
          }

          return item;
        })
      });
    } ]
  ]);

export default Store;
