import {defineStore} from 'pinia'
import {accountsConnector} from "src/api/accounts-connector.js";

export const accountsStore = defineStore('accounts', {
  state: () => ({
    accounts: [],
    currentAccount: {}
  }),
  actions: {
    loadAccountsList() {
      accountsConnector.getAccountsList(
        resp => {
          this.accounts = resp.data.response
        }
      )
    },
    chooseAccount(accountId) {
      this.currentAccount = {}
      let filtered = this.accounts.filter(item => item.id === accountId);
      if (filtered.length === 1) {
        this.currentAccount = filtered[0]
      }
      accountsConnector.getAccountInfo(
        {id: accountId},
        (resp) => {
          this.currencAccount = resp.data.response
        }
      )
    },
    deleteAccount(accountId) {
      this.currentAccount = {}
      accountsConnector.deleteAccount(
        {id: accountId},
        (resp) => {
          this.loadAccountsList()
        }
      )
    },
    addNewAccount(accountName, broker, brokerNumber) {
      accountsConnector.createAccount({
        name: accountName,
        broker: broker,
        number: brokerNumber
      }, (resp) => {
        this.loadAccountsList()
      })
    },
  }
})
