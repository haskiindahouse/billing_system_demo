import { useState } from "react"
import type { Client } from "src/api/clients"
import {
  useCreateClientMutation,
  useDeleteClientMutation,
  useGetClientsQuery,
  useUpdateClientMutation,
} from "src/api/clients"
import { ClientModal } from "src/components/Modal"

export const Clients = () => {
  const { data: clients } = useGetClientsQuery()
  const [client, setClient] = useState<Client>()

  const [showModal, setShowModal] = useState(false)

  const toggleModal = () => setShowModal(prev => !prev)

  const [deleteClient] = useDeleteClientMutation()

  const [updateClient] = useUpdateClientMutation()

  const [createClient] = useCreateClientMutation()

  return (
    <div className="p-5 relative overflow-x-auto">
      <button
        onClick={() => {
          setClient(undefined)
          toggleModal()
        }}
        className="block text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800"
        type="button"
      >
        Создать
      </button>
      <table className="w-full text-sm text-left rtl:text-right text-gray-500 dark:text-gray-400">
        <thead className="text-xs text-gray-700 uppercase bg-gray-50 dark:bg-gray-700 dark:text-gray-400">
          <tr>
            <th scope="col" className="px-6 py-3">
              ID
            </th>
            <th scope="col" className="px-6 py-3">
              Баланс
            </th>
            <th scope="col" className="px-6 py-3">
              Лимит
            </th>
            <th scope="col" className="px-6 py-3">
              Статус
            </th>
            <th scope="col" className="px-6 py-3">
              Тип
            </th>
            <th scope="col" className="px-6 py-3">
              ФИО
            </th>
            <th scope="col" className="px-6 py-3">
              Электронная почта
            </th>
            <th scope="col" className="px-6 py-3">
              Дата рождения
            </th>
            <th scope="col" className="px-6 py-3">
              Адрес подключения
            </th>
            <th scope="col" className="px-6 py-3">
              Номер телефона
            </th>
            <th scope="col" className="px-6 py-3">
              Действия
            </th>
          </tr>
        </thead>
        <tbody>
          {clients?.map(client => (
            <tr
              className="bg-white border-b dark:bg-gray-800 dark:border-gray-700"
              key={client.clientId}
            >
              <th
                scope="row"
                className="px-6 py-4 font-medium text-gray-900 whitespace-nowrap dark:text-white"
              >
                {client.clientId}
              </th>
              <td className="px-6 py-4">{client.currentBalance.amount}</td>
              <td className="px-6 py-4">{client.currentBalance.limit}</td>
              <td className="px-6 py-4">{client.status}</td>
              <td className="px-6 py-4">{client.name}</td>
              <td className="px-6 py-4">{client.email}</td>
              <td className="px-6 py-4">{client.address}</td>
              <td className="px-6 py-4">{client.phone}</td>
              <td className="px-6 py-4">
                <button
                  onClick={() => {
                    setClient(client)
                    toggleModal()
                  }}
                  className="block text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800"
                  type="button"
                >
                  Редактировать
                </button>
                <button
                  onClick={() => deleteClient(client.clientId)}
                  className="block text-white bg-red-700 hover:bg-red-800 focus:ring-4 focus:outline-none focus:ring-red-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center dark:bg-red-600 dark:hover:bg-red-700 dark:focus:ring-red-800"
                  type="button"
                >
                  Удалить
                </button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>

      {showModal && (
        // TODO: create ModalProvider and call it with parameters when needed
        // after add separate handlers for create and update
        <ClientModal
          onClose={() => setShowModal(false)}
          {...client}
          onSubmit={(newClient: Client) => {
            !client
              ? createClient(newClient)
              : updateClient({ ...newClient, clientId: client.clientId })
          }}
        />
      )}
    </div>
  )
}
