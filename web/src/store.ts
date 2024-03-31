import type {
  Action,
  ConfigureStoreOptions,
  ThunkAction,
} from "@reduxjs/toolkit"
import { configureStore } from "@reduxjs/toolkit"
import { setupListeners } from "@reduxjs/toolkit/query"
import { api } from "./api/api"

// `combineSlices` automatically combines the reducers using
// their `reducerPath`s, therefore we no longer need to call `combineReducers`.

// The store setup is wrapped in `makeStore` to allow reuse
// when setting up tests that need the same store config

export const setupStore = (
  options?: ConfigureStoreOptions["preloadedState"] | undefined,
) => {
  const store = configureStore({
    reducer: {
      [api.reducerPath]: api.reducer,
    },
    middleware: getDefaultMiddleware =>
      getDefaultMiddleware().concat(api.middleware),
    ...options,
  })

  setupListeners(store.dispatch)

  return store
}

export const store = setupStore()

// Infer the type of `store`
export type AppStore = typeof store
// Infer the `AppDispatch` type from the store itself
export type AppDispatch = AppStore["dispatch"]

export type RootState = ReturnType<typeof store.getState>
export type AppThunk<ThunkReturnType = void> = ThunkAction<
  ThunkReturnType,
  RootState,
  unknown,
  Action
>
