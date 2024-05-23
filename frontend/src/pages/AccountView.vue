<template>
  <div class="q-pa-md q-gutter-y-sm">
    <div class="text-h6 row">
      <div class="col text-h4">
        {{ store.currentAccount.name }}
      </div>
      <div class="col-auto q-pr-xs">
        <q-btn round
               size="sm"
               icon="edit"
               @click="accountDialogShown = true"
               title="Изменить счёт"
        />
      </div>
      <div class="col-auto">
        <q-btn round
               size="sm"
               icon="delete"
               @click="deleteAccount()"
               title="Удалить счёт"
        />
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
        <div class="text-h6">Изменить счёт</div>
      </q-card-section>

      <q-card-section class="q-pt-none">
        <div class="q-gutter-md" style="width: 300px">
          <q-input v-model="accountName" label="Название счёта" />
          <q-input v-model="accountBroker" label="Брокер" />
          <q-input v-model="accountNumber" label="Номер счёта" />
        </div>
      </q-card-section>

      <q-card-actions align="right">
        <q-btn flat label="Сохранить" color="primary" @click="save" type="a"/>
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

const props = defineProps({
  accountId: String
})

function initFields(name, broker, number) {
  accountName.value = name
  accountBroker.value = broker
  accountNumber.value = number
}

store.$subscribe( (mutation, store) =>{
    initFields(store.currentAccount.name, store.currentAccount.broker, store.currentAccount.number )
  }
)

function initPage() {
  store.chooseAccount(props.accountId)
}

watch(props, (nv) =>{
  initPage()
})
onMounted(() => {
  initPage()
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
