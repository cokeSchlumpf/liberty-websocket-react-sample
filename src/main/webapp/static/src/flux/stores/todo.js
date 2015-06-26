import ReactFlux from 'react-flux';
import Constants from '../constants/todo';

import TodoActions from '../actions/todo';

let Store = ReactFlux.createStore({
    getInitialState: function() {
      return {
        data: [],
        error: null,
        rest: false
      };
    },

    storeDidMount: function() {
      TodoActions.init(this.state.rest);
    }
  },
  [
    [ Constants.CREATE_SUCCESS, function handleCreateSuccess() {
      this.setState({ error: null });
      if (this.state.getState().rest) { TodoActions.list(); }
    } ],

    [ Constants.CREATE_FAIL, function handleDoneFail(error) {
      this.setState({ error: error.message });
    } ],

    [ Constants.DELETE_SUCCESS, function handleDeleteSuccess() {
      this.setState({ error: null });
      if (this.state.getState().rest) { TodoActions.list(); }
    } ],

    [ Constants.DONE_SUCCESS, function handleDoneSuccess() {
      this.setState({ error: null });
      if (this.state.getState().rest) { TodoActions.list(); }
    } ],
    
    [ Constants.LIST_SUCCESS, function handleListSuccess(payload) {      
      this.setState({
        data: payload
      })
    } ],

    [ Constants.REST_SUCCESS, function handleRest(rest) {
      this.setState({ rest: rest });      
    } ]
  ]);

export default Store;
