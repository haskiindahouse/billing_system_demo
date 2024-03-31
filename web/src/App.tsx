import { useGetClientsQuery } from "./api/clients"

const App = () => {
  const { data: clients } = useGetClientsQuery()
  return (
    <div className="App">
      <h1 className="text-3xl font-bold underline">Hello world!</h1>
      <pre>{JSON.stringify(clients, null, 2)}</pre>
    </div>
  )
}

export default App
