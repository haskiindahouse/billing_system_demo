import type { RouteObject } from "react-router-dom"
import { Layout } from "src/pages/Layout"
import { Clients } from "./pages/clients/Clients"

export const routes: RouteObject[] = [
  {
    path: "/",
    element: <Layout />,
    children: [
      {
        path: "clients",
        element: <Clients />,
        children: [],
      },
    ],
  },
]
