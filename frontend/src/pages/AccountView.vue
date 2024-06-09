<template>
  <div class="q-pa-md q-gutter-y-sm">
    <div class="text-h6 row">
      <div class="col text-h4">
        {{ accountStore.currentAccount.name }}
      </div>
      <div class="col-auto q-pr-xs">
        <replenish-account-component :account-id="accountId"/>
        <withdraw-account-component :account-id="accountId" />
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
      {{ accountStore.currentAccount.broker }}
    </div>
    <div>
      {{ accountStore.currentAccount.number }}
    </div>
    <div>
      {{ accountStore.currentAccount.currentBalance }}
    </div>
    <q-separator/>
    <q-tabs
      v-model="tab"
      align="justify"
      narrow-indicator
    >
      <q-tab name="assets" label="Активы" />
      <q-tab name="operations" label="Операции" disable/>
      <q-tab name="stat" label="Статистика" disable/>
    </q-tabs>
    <q-tab-panels v-model="tab" animated class="bg-orange-1 text-dark text-center">
      <q-tab-panel name="assets">
        <q-table
          flat bordered
          :rows="assetsStore.assets"
          :columns="assetsColumns"
          row-key="name"
        />      </q-tab-panel>

      <q-tab-panel name="operations" class="bg-grey-9 text-white">
        <div class="text-h6">Alarms</div>
        Lorem ipsum dolor sit amet consectetur adipisicing elit.
      </q-tab-panel>

      <q-tab-panel name="stat" class="bg-lime-1 text-dark">
        <div class="text-h6">Movies</div>
        Lorem ipsum dolor sit amet consectetur adipisicing elit.
      </q-tab-panel>
    </q-tab-panels>
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
import {useRouter} from 'vue-router'
import {accountsStore} from "stores/accounts.js";
import {onMounted, ref, watch} from "vue";
import ReplenishAccountComponent from "components/ReplenishAccountComponent.vue";
import WithdrawAccountComponent from "components/WithdrawAccountComponent.vue";
import {usePurchasedAssetsStore} from "stores/purchased-asssets.js";

const router = useRouter()
const accountStore = accountsStore()
const assetsStore = usePurchasedAssetsStore()
const accountDialogShown = ref(false)
const accountName = ref("")
const accountBroker = ref("")
const accountNumber = ref("")
const assetsColumns = ref([
  {name:"code", field:"code"},
  {name:"amount", field:"amount"},
  {name:"purchasePrice", field:"purchasePrice"},
  {name:"currentPrice", field:"currentPrice"}])
const tab = ref("assets")
const props = defineProps({
  accountId: String
})

function initFields(name, broker, number) {
  accountName.value = name
  accountBroker.value = broker
  accountNumber.value = number
}

accountStore.$subscribe( (mutation, store) =>{
    initFields(accountStore.currentAccount.name, accountStore.currentAccount.broker, accountStore.currentAccount.number )
  }
)

function initPage() {
  accountStore.chooseAccount(props.accountId)
  tab.value = "assets"
  assetsStore.loadAccountsList(props.accountId)
}

watch(props, (nv) =>{
  initPage()
})
onMounted(() => {
  initPage()
})
function deleteAccount() {
  accountStore.deleteAccount(accountStore.currentAccount.id)
  router.push('/')
}
function save() {
  accountStore.updateAccount(accountStore.currentAccount.id, accountName.value, accountBroker.value, accountNumber.value)
}
watch(tab, (nv) => {
  console.log(nv)
})
</script>

<style scoped>

</style>
