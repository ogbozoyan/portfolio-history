<template>
  <q-btn round
         size="sm"
         icon="fas fa-hand-holding-usd"
         title="Вывести"
         @click="showDialog = true"
  />
  <q-dialog v-model="showDialog">
    <q-card>
      <q-card-section>
        <div class="text-h6">Вывести средства</div>
      </q-card-section>

      <q-card-section class="q-pt-none">
        <div class="q-gutter-md" style="width: 300px">
          <q-input
            filled
            v-model="amount"
            label="Сумма для вывода"
            mask="#.##"
            fill-mask="0"
            reverse-fill-mask
            hint="Mask: #.##"
            input-class="text-right"
          />
        </div>
      </q-card-section>

      <q-card-actions align="right">
        <q-btn flat label="Вывести" color="primary" @click="withdraw" type="a"/>
      </q-card-actions>
    </q-card>
  </q-dialog>
</template>

<script setup>
import {inject, ref} from "vue";
import {operationsStore} from "stores/operations.js";
import {AccountEvents} from "src/events/accountEvents.js";

const store = operationsStore()

const props = defineProps({
  accountId: String
})
const amount = ref(0)
const showDialog = ref(false)
const bus = inject('bus')

function withdraw() {
  store.withdrawFromAccount(props.accountId, amount.value)
}
bus.on(AccountEvents.accountChangeEvent, () => showDialog.value=false)
</script>


<style scoped>

</style>
