import axios from 'axios'

const APPKEY = '71b78930c129ba5b'

axios.defaults.timeout = 5000
axios.defaults.baseURL = 'http://api.jisuapi.com'

axios.interceptors.request.use(
  (config) => {
    return config
  }, (error) => {
    console.log(JSON.stringify(error))
    return Promise.reject(error)
  }
)

axios.interceptors.response.use(
  (res) => {
    console.log(JSON.stringify(res.data))
    if (res.data.status === '0') {
      return res
    }
    return Promise.reject(res)
  }, (error) => {
    console.log(JSON.stringify(error))
    return Promise.reject(error)
  }
)

export function get (url, params) {
  params.appkey = APPKEY
  return new Promise((resolve, reject) => {
    axios.get(url, {
      params: params
    })
      .then(response => {
        resolve(response.data)
      }, err => {
        reject(err)
      })
      .catch((error) => {
        reject(error)
      })
  })
}

export function post (url, params) {
  return new Promise((resolve, reject) => {
    axios.post(url, params
    ).then(response => {
      resolve(response.data)
    }, err => {
      reject(err)
    })
      .catch((error) => {
        reject(error)
      })
  })
}

export const api = {
  search (keyword) {
    return get('/news/search', {keyword: keyword})
  },
  list (channel, start, num) {
    return get('/news/get', {
      channel: channel,
      start: start,
      num: num
    })
  },
  channels () {
    return get('/news/channel', {})
  }
}
