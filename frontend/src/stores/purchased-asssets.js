import {defineStore} from "pinia";
import {purchasedAssetsConnector as purchasedAssetsConnector} from "src/api/purchased-assets-connector.js";

export const usePurchasedAssetsStore = defineStore('purchasedAssetsStore', {
  state: () => ({
    assets: [],
  }),
  actions: {
    loadAccountsList(accountId) {
      purchasedAssetsConnector.getAssetsList(
        resp => {
          this.assets = resp.data.response
        }
      )
    },

  }
})
