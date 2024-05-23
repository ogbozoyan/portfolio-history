import {instance} from "src/api/base-connector.js";

export const accountsConnector = {
  createAccount(account, callBack) {
    instance.post('create-account',
      account
    ).then(response => callBack(response))
  },
  getAccountsList(callBack) {
    instance.post('get-accounts-list').then(response => callBack(response))
  },
  getAccountInfo(id, callBack) {
    instance.post('get-accounts-info', id).then(response => callBack(response))
  },
  deleteAccount(id, callBack) {
    instance.post('delete-account', id).then(response => callBack(response))
  },
  updateAccount(accountInfo, callBack) {
    instance.post('update-account', accountInfo).then(response => callBack(response))
  }
}
