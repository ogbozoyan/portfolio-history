import axios from 'axios'

export const instance = axios.create({
  baseURL: '/api/v1/',
  timeout: 1000,
  headers: { 'Content-Type': 'application/json' }
})

instance.interceptors.response.use(
  response => response,
  error => {
    return Promise.reject(error);
  }
)
