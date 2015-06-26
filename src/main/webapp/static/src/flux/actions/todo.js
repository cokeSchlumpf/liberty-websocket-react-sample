import ReactFlux from 'react-flux';
import restful from 'restful.js';

import Constants from '../constants/todo';

let rest = false;

let api = restful('localhost')
  .prefixUrl('todoapp/rest')
  .protocol('http')
  .port(9080);

const WS = window.WebSocket ? window.WebSocket : require('websocket').w3cwebsocket;
let ws = null;

const handleException = function(reject) {
  return function(response) {
    reject(response.data);
  };
};

let actions = ReactFlux.createActions({
  create: [ Constants.CREATE, function(description) {
    return new Promise(function(resolve, reject) {
      api
        .all('todos')
        .post({ description: description, done: false })
        .then(function(response) {
          resolve(response.body(false));
        })
        .catch(handleException(reject));
    });
  } ],

  'delete': [ Constants.DELETE, function(id) {
    return new Promise(function(resolve, reject) {
      api.one('todos', id).delete().then(function(response) {
        resolve(response);
      }, function(response) {
        reject(new Error(response.body(false)));
      });
    });
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

  list: [ Constants.LIST, function(list) {
    let result = null;

    if (rest) {
      result = new Promise(function(resolve, reject) {
        api.all('todos').getAll().then(function(response) {
          resolve(response.body(false));
        }, function(response) {
          reject(new Error(response.body(false)));
        });
      });
    } else {
      result = list;
    }

    return result;
  } ],

  init: [ Constants.INIT, function(_rest) {
    if (_rest) {
      actions.list();
    } else {
      actions.rest(_rest);
    }
  } ],

  rest: [ Constants.REST, function(_rest) {
    rest = _rest;

    if (!rest) {
      ws = new WS('ws://localhost:9080/todoapp/todo');
      ws.onopen = function() {
        ws.onmessage = function (event) {
          const message = JSON.parse(event.data);
          const item = JSON.parse(message.data);

          if (message.action === 'LIST') {
            actions.list(item.items);
          }
        };

        ws.send(JSON.stringify({ action: 'LIST' }));
      };
    } else {
      ws.close();
    }

    return _rest;
  } ]
});

export default actions;
