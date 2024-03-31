import React from "react"
import { createRoot } from "react-dom/client"
import { Provider } from "react-redux"
import { setupStore } from "./store"
import "./index.css"
import { RouterProvider, createBrowserRouter } from "react-router-dom"
import { routes } from "./routes"

if (import.meta.env.VITE_MOCKS_ENABLED) {
  const { worker } = await import("./mocks/browser")

  // `worker.start()` returns a Promise that resolves
  // once the Service Worker is up and ready to intercept requests.
  worker.start()
}

const store = setupStore()

const router = createBrowserRouter(routes)

const root = createRoot(document.getElementById("root")!)
root.render(
  <React.StrictMode>
    <Provider store={store}>
      <RouterProvider router={router} />
    </Provider>
  </React.StrictMode>,
)
