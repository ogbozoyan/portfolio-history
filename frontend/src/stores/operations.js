import {defineStore} from 'pinia'
import {operationsConnector} from "src/api/operation-connector.js";
import {AccountEvents} from "src/events/accountEvents.js";

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
          this._p.$bus.emit(AccountEvents.accountChangeEvent, accountId)
        }
      )
    },
    withdrawFromAccount(accountId, amount) {
       operationsConnector.withdrawFromAccount(
        {
          "accountId": accountId,
          "amount": amount
        },
        response => {
          this._p.$bus.emit(AccountEvents.accountChangeEvent, accountId)
        }
      )
    },
  }
})
