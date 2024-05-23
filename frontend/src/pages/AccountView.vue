<template>
  <div class="q-pa-md q-gutter-y-sm">
    <div class="text-h6 row">
      <div class="col">
        {{ store.currentAccount.name }}
      </div>
      <div class="col-auto q-pr-xs">
        <q-btn round color="primary" icon="edit" @click="accountDialogShown = true"/>
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
  <q-dialog v-model="accountDialogShown">
    <q-card>
      <q-card-section>
        <div class="text-h6">Создать новый счёт</div>
      </q-card-section>

      <q-card-section class="q-pt-none">
        <div class="q-gutter-md" style="width: 300px">
          <q-input v-model="accountName" label="Название счёта" />
          <q-input v-model="accountBroker" label="Брокер" />
          <q-input v-model="accountNumber" label="Номер счёта" />
        </div>
      </q-card-section>

      <q-card-actions align="right">
        <q-btn flat label="Создать" color="primary" @click="save" type="a"/>
      </q-card-actions>
    </q-card>
  </q-dialog>
</template>

<script setup>
import {useRoute, useRouter} from 'vue-router'
import {accountsStore} from "stores/accounts.js";
import {onBeforeUpdate, onMounted, ref, watch} from "vue";
import {storeToRefs} from "pinia";

const route = useRoute()
const router = useRouter()
const store = accountsStore()
const accountDialogShown = ref(false)
const accountName = ref("")
const accountBroker = ref("")
const accountNumber = ref("")

function initFields(name, broker, number) {
  accountName.value = name
  accountBroker.value = broker
  accountNumber.value = number
}

store.$subscribe( (mutation, store) =>{
    initFields(store.currentAccount.name, store.currentAccount.broker, store.currentAccount.number )
  }
)
onMounted(() => {
  store.chooseAccount(route.params.accountId)
})
function deleteAccount() {
  store.deleteAccount(store.currentAccount.id)
  router.push('/')
}
function save() {
  store.updateAccount(store.currentAccount.id, accountName.value, accountBroker.value, accountNumber.value)
}
</script>

<style scoped>

</style>
