import {defineStore} from 'pinia'
import {operationsConnector} from "src/api/operation-connector.js";

export const operationsStore = defineStore('operations', {
  state: () => ({}),
  actions: {
    replenishAccount(accountId, amount) {
       operationsConnector.replenishAccount(
        {
          "accountId": accountId,
          "amount": amount,
          "unitPrice": 1
        },
        response => {
          this._p.$bus.emit('accountReplenished', accountId)
        }
      )
    },
  }
})
