import api from './axios'

export const getTransactions = ({ startDate, endDate, page = 0, size = 10, sort = 'date,desc' }) =>
  api
    .get('/moneylog', { params: { startDate, endDate, page, size, sort } })
    .then((res) => res.data)

export const createTransaction = (payload) =>
  api.post('/moneylog', payload).then((res) => res.data)

export const updateTransaction = (id, payload) =>
  api.put(`/moneylog/${id}`, payload).then((res) => res.data)

export const deleteTransaction = (id) => api.delete(`/moneylog/${id}`)
