import ReactFlux from 'react-flux';
import restful from 'restful.js';

import Constants from '../constants/todo';

let api = restful('localhost')
  .prefixUrl('todoapp/ws')
  .protocol('http')
  .port(9080);

export default ReactFlux.createActions({
  create: [ Constants.CREATE, function(description) {
    return new Promise(function(resolve, reject) {
      api.all('todos').post({ description: description, done: false }).then(function(response) {
        resolve(response.body(false)); 
      }, function(response) {
        reject(new Error(response.body(false)));
      });
    });
  } ],

  'delete': [ Constants.DELETE, function(id) {
    return new Promise(function(resolve, reject) {
      api.one('todos', id).delete().then(function(response) {
        resolve(response);
      }, function(response) {
        reject(new Error(response.body(false)));
      });
    })
  } ],

  done: [ Constants.DONE, function(id, done) {
    return new Promise(function(resolve, reject) {
      api.one('todos', id).get().then(function(response) {
        let itemEntity = response.body();
        let item = itemEntity.data();
        item.done = done;
        itemEntity.save().then(function() {
          resolve();
        }, function(saveResponse) {
          reject(new Error(saveResponse.body(false)));
        });
      }, function(response) {
        reject(new Error(response.body(false)));
      });
    });
  } ],

  list: [ Constants.LIST, function() {
    return new Promise(function(resolve, reject) {
      api.all('todos').getAll().then(function(response) {
        resolve(response.body(false));
      }, function(response) {
        reject(new Error(response.body(false)));
      });
    });
  } ]
});
