import { http, HttpResponse } from 'msw'
import type { Dictionary } from 'src/models'

const datas: Dictionary[] = []
const subDatas: Dictionary[] = []

for (let i = 1; i < 28; i++) {
  const data: Dictionary = {
    id: i,
    name: 'dictionary_' + i,
    order: i,
    count: i - 1,
    description: 'this is description for this row',
    enabled: i % 3 > 0
  }
  for (let j = 1; j < i; j++) {
    const subData: Dictionary = {
      id: 100 + i,
      name: 'dictionary_' + i + '_' + j,
      order: j,
      count: 0,
      superiorId: i,
      enabled: j % 2 > 0,
      description: 'this is description for this row'
    }
    subDatas.push(subData)
  }
  datas.push(data)
}

export const dictionariesHandlers = [
  http.get('/api/dictionaries/:id', ({ params }) => {
    const { id } = params
    if (id) {
      let array = datas.filter(item => item.id === Number(id))
      if (array.length === 0) {
        array = subDatas.filter(item => item.id === Number(id))
      }
      return HttpResponse.json(array[0])
    } else {
      return HttpResponse.json(null)
    }
  }),
  http.get('/api/dictionaries/:id/subset', ({ params }) => {
    const superiorId = params.id
    return HttpResponse.json(subDatas.filter(item => item.superiorId === Number(superiorId)))
  }),
  http.get('/api/dictionaries', ({ request }) => {
    const url = new URL(request.url)

    const page = url.searchParams.get('page')
    const size = url.searchParams.get('size')

    // Construct a JSON response with the list of all Row
    // as the response body.
    const data = {
      content: Array.from(datas.slice(Number(page) * Number(size), (Number(page) + 1) * Number(size))),
      page: {
        totalElements: datas.length
      }
    }

    return HttpResponse.json(data)
  }),
  http.post('/api/dictionaries', async ({ request }) => {
    // Read the intercepted request body as JSON.
    const newData = await request.json() as Dictionary

    // Push the new Row to the map of all Row.
    datas.push(newData)

    // Don't forget to declare a semantic "201 Created"
    // response and send back the newly created Row!
    return HttpResponse.json(newData, { status: 201 })
  }),
  http.delete('/api/dictionaries/:id', ({ params }) => {
    // All request path params are provided in the "params"
    // argument of the response resolver.
    const { id } = params

    // Let's attempt to grab the Row by its ID.
    const deletedData = datas.filter(item => item.id === Number(id))

    // Respond with a "404 Not Found" response if the given
    // Row ID does not exist.
    if (!deletedData) {
      return new HttpResponse(null, { status: 404 })
    }

    // Delete the Dictionary from the "allDictionarys" map.
    datas.pop()

    // Respond with a "200 OK" response and the deleted Dictionary.
    return HttpResponse.json(deletedData)
  })
]
