import {defineStore} from 'pinia'
import {accountsConnector} from "src/api/accounts-connector.js";

export const accountsStore = defineStore('accounts', {
  state: () => ({
    accounts: []
  }),
  actions: {
    loadAccountsList() {
      accountsConnector.getAccountsList(
        resp => {
          this.accounts = resp.data.responce
        }
      )
    },
    addNewAccount(accountName, broker, brokerNumber) {
      accountsConnector.createAccount({
        name: accountName,
        broker: broker,
        number: brokerNumber
      }, (resp) => {
        console.log(resp)
        this.loadAccountsList()
      })
    },
  }
})
