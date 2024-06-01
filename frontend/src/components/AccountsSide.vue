<template>
  <q-list>
    <q-item v-for="account in store.accounts" v-bind="account" :key="account.id" @click="showAccount(account.id)">
        <q-card class="my-card"  @click="showAccount(account.id)">
          <q-card-section>
            <div class="text-h6">{{account.name}}</div>
          </q-card-section>

          <q-card-section class="q-pt-none">
            {{ account.currentBalance }}
          </q-card-section>
        </q-card>
    </q-item>
    <br />

    <q-page-sticky position="bottom-right" :offset="[18, 18]">
      <q-btn
        round
        icon="add"
        @click="addAccountDialogOpe = true"
        title="Добавить новый счёт"
        size="md"
      />
    </q-page-sticky>
  </q-list>
  <q-dialog v-model="addAccountDialogOpe">
    <q-card>
      <q-card-section>
        <div class="text-h6">Создать новый счёт</div>
      </q-card-section>

      <q-card-section class="q-pt-none">
        <div class="q-gutter-md" style="width: 300px">
          <q-input v-model="newAccountName" label="Название счёта" />
          <q-input v-model="newAccountBrokerName" label="Брокер" />
          <q-input v-model="newAccountBrokerNumber" label="Номер счёта" />
        </div>
      </q-card-section>

      <q-card-actions align="right">
        <q-btn flat label="Создать" color="primary" @click="processAdd" type="a"/>
      </q-card-actions>
    </q-card>
  </q-dialog>

</template>

<script setup>
import {inject, onMounted, ref} from "vue";
import {accountsStore} from "stores/accounts.js";
import {useRouter} from "vue-router";

const store = accountsStore()
const router = useRouter()
const bus = inject('bus')
const addAccountDialogOpe = ref(false)
const newAccountName = ref("")
const newAccountBrokerName = ref("")
const newAccountBrokerNumber = ref("")

function processAdd() {
  store.addNewAccount(newAccountName.value, newAccountBrokerName.value, newAccountBrokerNumber.value)
}
onMounted(() => {
  store.loadAccountsList()
})
bus.on('refreshAccountList', ()=>{store.loadAccountsList()})
function showAccount(accountId) {
  router.push('/account/'+accountId)
}
</script>

<style scoped  lang="sass">
.my-card
  width: 100%
  cursor: pointer
</style>
