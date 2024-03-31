import { createPortal } from "react-dom"

export const ClientModal = ({ onClose, onSubmit, ...client }: any) => {
  return createPortal(
    <div
      id="default-modal"
      tabIndex={-1}
      className="overflow-y-auto overflow-x-hidden fixed top-0 right-0 left-0 z-50 justify-center items-center w-full md:inset-0 h-[calc(100%-1rem)] max-h-full flex bg-gray-700/50"
    >
      <div className="relative p-4 w-full max-w-2xl max-h-full">
        <div className="relative bg-white rounded-lg shadow dark:bg-gray-700">
          <form
            className="max-w-sm mx-auto p-4"
            onSubmit={event => {
              event.preventDefault()
              onSubmit({
                ...Object.values(event.target).reduce(
                  (accumulator, { name, value }) => {
                    accumulator[name] = value
                    return accumulator
                  },
                  {},
                ),
                id: client?.id,
              })
            }}
          >
            <div className="mb-5">
              <label
                htmlFor="email"
                className="block mb-2 text-sm font-medium text-gray-900 dark:text-white"
              >
                Электронная почта
              </label>
              <input
                type="email"
                id="email"
                name="email"
                className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
                placeholder="name@yandex.ru"
                defaultValue={client.email}
                required
              />
            </div>
            <div className="mb-5">
              <label
                htmlFor="balance"
                className="block mb-2 text-sm font-medium text-gray-900 dark:text-white"
              >
                Баланс
              </label>
              <input
                type="text"
                id="balance"
                name="balance"
                className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
                defaultValue={client.balance}
              />
            </div>

            <div className="mb-5">
              <label
                htmlFor="status"
                className="block mb-2 text-sm font-medium text-gray-900 dark:text-white"
              >
                Статус
              </label>
              <input
                type="text"
                id="status"
                name="status"
                className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
                defaultValue={client.status}
              />
            </div>

            <div className="mb-5">
              <label
                htmlFor="name"
                className="block mb-2 text-sm font-medium text-gray-900 dark:text-white"
              >
                ФИО
              </label>
              <input
                type="text"
                name="name"
                id="name"
                className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
                defaultValue={client.name}
              />
            </div>

            <div className="mb-5">
              <label
                htmlFor="phone"
                className="block mb-2 text-sm font-medium text-gray-900 dark:text-white"
              >
                Номер телефона
              </label>
              <input
                type="tel"
                name="phone"
                id="phone"
                className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
                defaultValue={client.phone}
              />
            </div>

            <div className="mb-5">
              <label
                htmlFor="address"
                className="block mb-2 text-sm font-medium text-gray-900 dark:text-white"
              >
                Адрес
              </label>
              <input
                type="text"
                id="address"
                name="address"
                className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
                defaultValue={client.address}
              />
            </div>
            <button
              type="submit"
              className="text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm w-full sm:w-auto px-5 py-2.5 text-center dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800"
            >
              {client?.id ? "Обновить" : "Создать"}
            </button>

            <button
              type="button"
              onClick={onClose}
              className="ml-4 text-white bg-gray-700 hover:bg-gray-800 focus:ring-4 focus:outline-none focus:ring-gray-300 font-medium rounded-lg text-sm w-full sm:w-auto px-5 py-2.5 text-center dark:bg-gray-600 dark:hover:bg-gray-700 dark:focus:ring-gray-800"
            >
              Отмена
            </button>
          </form>
        </div>
      </div>
    </div>,
    document.getElementById("root")!,
  )
}
