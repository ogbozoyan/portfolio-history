import {instance} from "src/api/base-connector.js";

export const operationsConnector = {
  replenishAccount(replenishOperation, callBack) {
    instance.post('replenish-account',
      replenishOperation
    ).then(response => callBack(response))
  },
  withdrawFromAccount(withdrawOperation, callBack) {
    instance.post('withdraw-from-account',
      withdrawOperation
    ).then(response => callBack(response))
  }
}
