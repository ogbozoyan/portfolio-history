import { EventBus } from 'quasar'
import { boot } from 'quasar/wrappers'

export default boot(({ app, router, store }) => {
  const bus = new EventBus()

  // for Options API
  app.config.globalProperties.$bus = bus

  // for Composition API
  app.provide('bus', bus)
  store.$bus = bus
})
