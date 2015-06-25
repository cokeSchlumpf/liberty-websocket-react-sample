import ReactFlux from 'react-flux';
import Constants from '../constants/todo';

import TodoActions from '../actions/todo';

let Store = ReactFlux.createStore({
    getInitialState: function() {
      return {
        data: [],
        error: null
      };
    },

    storeDidMount: function() {
      TodoActions.init();
    }
  },
  [
    [ Constants.CREATE_SUCCESS, function handleCreateSuccess() {
      this.setState({ error: null });
      // TodoActions.list();
    } ],

    [ Constants.CREATE_FAIL, function handleDoneFail(error) {
      this.setState({ error: error.message });
    } ],

    [ Constants.DELETE_SUCCESS, function handleDeleteSuccess() {
      this.setState({ error: null });
      // TodoActions.list();  
    } ],

    [ Constants.DONE_SUCCESS, function handleDoneSuccess() {
      this.setState({ error: null });
      // TodoActions.list();  
    } ],
    
    [ Constants.LIST_SUCCESS, function handleListSuccess(payload) {      
      this.setState({
        data: payload
      })
    } ]
  ]);

export default Store;
