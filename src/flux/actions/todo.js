import ReactFlux from 'react-flux';
import Constants from '../constants/todo';

let counter = 0;

export default ReactFlux.createActions({
  create: [ Constants.CREATE, function(description) {
    const promise = new Promise(function(resolve, reject) {
      if (description.length) {
        counter = counter + 1;

        resolve({
          id: counter,
          description: description,
          egon: 'olsen'
        });
      } else {
        reject(new Error('Invalid description'));
      }
    });

    return promise;
  } ],

  'delete': [ Constants.DELETE, function(id) {
    return {
      id: id
    };
  } ],

  done: [ Constants.DONE, function(id, done) {
    return {
      id: id,
      done: done
    };
  } ],

  list: [ Constants.LIST ]
});
