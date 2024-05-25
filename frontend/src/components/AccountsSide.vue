<template>
  <q-list>
    <q-item-label header class="text-h5">
      Список Счетов
    </q-item-label>
    <q-item v-for="account in store.accounts" v-bind="account" :key="account.id">
      <router-link :to="'/account/' + account.id" >{{account.name}}</router-link>
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
import {onMounted, ref} from "vue";
import {accountsStore} from "stores/accounts.js";

const addAccountDialogOpe = ref(false)
const newAccountName = ref("")
const newAccountBrokerName = ref("")
const newAccountBrokerNumber = ref("")
const store = accountsStore()

function processAdd() {
  store.addNewAccount(newAccountName.value, newAccountBrokerName.value, newAccountBrokerNumber.value)
}
onMounted(() => {
  store.loadAccountsList()
})
</script>

<style scoped>

</style>
