import {defineStore} from 'pinia'
import {accountsConnector} from "src/api/accounts-connector.js";

export const accountsStore = defineStore('accounts', {
  state: () => ({
    accounts: [],
    currentAccount: {}
  }),
  actions: {
    loadAccountsList(accountId) {
      accountsConnector.getAccountsList(
        resp => {
          this.accounts = resp.data.response
          if (accountId) {
            this.chooseAccount(accountId)
          }
        }
      )
    },
    chooseAccount(accountId) {
      this.currentAccount = {}
      let filtered = this.accounts.filter(item => item.id === accountId);
      if (filtered.length === 1) {
        this.currentAccount = filtered[0]
      }
      // let self = this
      accountsConnector.getAccountInfo(
        {id: accountId},
        (resp) => {
          this.currentAccount = resp.data.response
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
    updateAccount(accountId, name, broker, number) {
      this.currentAccount = {}
      accountsConnector.updateAccount(
        {
          id: accountId,
          name: name,
          broker: broker,
          number: number
        },
        (resp) => {
          this.loadAccountsList(accountId)
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
