import { api, omitBlankEntries } from "./api"

export type Client = {
  clientId: string
  currentBalance: {
    amount: number
    limit: number
  }
  status: string
  name: string
  email: string
  address: string
  phone: string
}

export const clientsApi = api.injectEndpoints({
  endpoints: builder => ({
    getClients: builder.query<Client[], void>({
      query: () => ({
        url: "clients",
      }),
      providesTags: (result = []) => [
        ...result.map(({ clientId: id }) => ({ type: "Clients", id }) as const),
        { type: "Clients" as const, id: "LIST" },
      ],
    }),
    createClient: builder.mutation<void, Partial<Client>>({
      query: ({ name, email, phone }) => ({
        url: `clients`,
        method: "POST",
        body: omitBlankEntries({ name, email, phone }),
      }),
      invalidatesTags: () => [{ type: "Clients", id: "LIST" }],
    }),

    updateClient: builder.mutation<void, Partial<Client>>({
      query: ({ clientId, name, email, phone }) => ({
        url: `clients/${clientId}`,
        method: "PUT",
        body: omitBlankEntries({ name, email, phone }),
      }),
      invalidatesTags: (_, __, { clientId }) => [
        { type: "Clients", id: clientId },
      ],
    }),
    deleteClient: builder.mutation<void, string>({
      query: id => ({
        url: `clients/${id}`,
        method: "DELETE",
      }),
      invalidatesTags: (_, __, id) => [{ type: "Clients", id }],
    }),
  }),
})

export const {
  useGetClientsQuery,
  useCreateClientMutation,
  useUpdateClientMutation,
  useDeleteClientMutation,
} = clientsApi
