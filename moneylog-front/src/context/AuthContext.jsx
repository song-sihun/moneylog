import { createContext, useContext, useMemo, useState } from 'react'

const AuthContext = createContext(null)

export function AuthProvider({ children }) {
  const [user, setUser] = useState(() => {
    const email = localStorage.getItem('email')
    const nickname = localStorage.getItem('nickname')
    const accessToken = localStorage.getItem('accessToken')
    return accessToken ? { email, nickname } : null
  })

  const loginSuccess = ({ accessToken, email, nickname }) => {
    localStorage.setItem('accessToken', accessToken)
    localStorage.setItem('email', email)
    localStorage.setItem('nickname', nickname)
    setUser({ email, nickname })
  }

  const logout = () => {
    localStorage.removeItem('accessToken')
    localStorage.removeItem('email')
    localStorage.removeItem('nickname')
    setUser(null)
  }

  const value = useMemo(
    () => ({ user, isAuthenticated: !!user, loginSuccess, logout }),
    [user],
  )

  return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>
}

export function useAuth() {
  const ctx = useContext(AuthContext)
  if (!ctx) throw new Error('useAuth must be used within AuthProvider')
  return ctx
}
