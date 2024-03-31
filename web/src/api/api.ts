import { createApi, fetchBaseQuery } from "@reduxjs/toolkit/query/react"

export const URL = import.meta.env.VITE_APP_BASE_URL || "http://localhost:8082"

const baseQuery = fetchBaseQuery({
  baseUrl: URL,
})

export const api = createApi({
  reducerPath: "splitApi",
  baseQuery: baseQuery,
  tagTypes: ["Clients"],
  endpoints: () => ({}),
})

/**
 * Removes all entries which values are empty strings
 * @param o {Object}
 * @returns Returns a new object without modifying the original
 */
export const omitBlankEntries = <T extends {}>(o: T) => {
  if (typeof o !== "object" && Array.isArray(o) && o == null) return {}
  return Object.fromEntries(Object.entries(o).filter(([_, v]) => v !== ""))
}
