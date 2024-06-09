import {instance} from "src/api/base-connector.js";

export const purchasedAssetsConnector = {
  getAssetsList(callBack) {
    instance.post('get-purchased-assets-list').then(response => callBack(response))
  },
}
