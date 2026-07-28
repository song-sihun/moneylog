import api from './axios'

export const signup = ({ email, password, nickname }) =>
  api.post('/auth/signup', { email, password, nickname })

export const login = ({ email, password }) =>
  api.post('/auth/login', { email, password }).then((res) => res.data)
