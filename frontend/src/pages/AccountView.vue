<template>
  <div class="q-pa-md q-gutter-y-sm">
    <div class="text-h6 row">
      <div class="col">
        {{ store.currentAccount.name }}
      </div>
      <div class="col-auto q-pr-xs">
        <q-btn round color="primary" icon="edit" />
      </div>
      <div class="col-auto">
        <q-btn round color="primary" icon="delete" @click="deleteAccount()"/>
      </div>
    </div>
    <q-separator/>
    <div>
      {{ store.currentAccount.broker }}
    </div>
    <div>
      {{ store.currentAccount.number }}
    </div>
  </div>
</template>

<script setup>
import {useRoute, useRouter} from 'vue-router'
import {accountsStore} from "stores/accounts.js";
import {onBeforeUpdate} from "vue";

const route = useRoute()
const router = useRouter()
const store = accountsStore()

onBeforeUpdate(() => {
  store.chooseAccount(route.params.accountId)
})
function deleteAccount() {
  store.deleteAccount(store.currentAccount.id)
  router.push('/')
}
</script>

<style scoped>

</style>
