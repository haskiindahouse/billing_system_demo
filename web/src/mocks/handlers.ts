import { HttpResponse, http } from "msw"

export const handlers = [
  http.get(`/clients`, () => {
    return HttpResponse.json([
      {
        id: "1",
        balance: 123,
        limit: 123,
        status: "John Maverick",
        type: "John Maverick",
        name: "John Maverick",
        email: "John Maverick",
        dob: "John Maverick",
        address: "John Maverick",
      },
      {
        id: "2",
        balance: 123,
        limit: 123,
        status: "John Maverick",
        type: "John Maverick",
        name: "John Maverick",
        email: "John Maverick",
        dob: "John Maverick",
        address: "John Maverick",
      },

      {
        id: "3",
        balance: 123,
        limit: 123,
        status: "John Maverick",
        type: "John Maverick",
        name: "John Maverick",
        email: "John Maverick",
        dob: "John Maverick",
        address: "John Maverick",
      },

      {
        id: "4",
        balance: 123,
        limit: 123,
        status: "John Maverick",
        type: "John Maverick",
        name: "John Maverick",
        email: "John Maverick",
        dob: "John Maverick",
        address: "John Maverick",
      },

      {
        id: "5",
        balance: 123,
        limit: 123,
        status: "John Maverick",
        type: "John Maverick",
        name: "John Maverick",
        email: "John Maverick",
        dob: "John Maverick",
        address: "John Maverick",
      },
    ])
  }),
]
